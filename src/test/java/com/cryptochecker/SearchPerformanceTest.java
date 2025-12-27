package com.cryptochecker;

import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

public class SearchPerformanceTest {

    private ArrayList<WebData.Coin> testCoins;

    @BeforeEach
    void setUp() {
        testCoins = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            WebData.Coin coin = new WebData().getCoin();
            coin.name = "Bitcoin " + i;
            coin.symbol = "BTC" + i;
            testCoins.add(coin);
        }
    }

    @Test
    @DisplayName("Load Test: Search Throughput and Error Rate")
    void testSearchLoad() throws InterruptedException {
        int numberOfSearches = 1000;
        int threadPoolSize = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
        
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger errorCount = new AtomicInteger(0);
        AtomicLong totalResponseTime = new AtomicLong(0);
        
        CountDownLatch latch = new CountDownLatch(numberOfSearches);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numberOfSearches; i++) {
            executor.submit(() -> {
                long reqStart = System.currentTimeMillis();
                try {
                    String searchTerm = "Bitcoin " + (int)(Math.random() * 1000);
                    boolean found = testCoins.stream()
                            .anyMatch(c -> c.name.equalsIgnoreCase(searchTerm));
                    if (!found) throw new Exception("Search failed to find existing record");
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    errorCount.incrementAndGet();
                } finally {
                    totalResponseTime.addAndGet(System.currentTimeMillis() - reqStart);
                    latch.countDown();
                }
            });
        }

        latch.await(30, TimeUnit.SECONDS);
        long endTime = System.currentTimeMillis();
        executor.shutdown();

        long duration = endTime - startTime;
        double throughput = (double) successCount.get() / (duration / 1000.0);
        double avgResponseTime = (double) totalResponseTime.get() / numberOfSearches;
        double errorRate = (double) errorCount.get() / numberOfSearches * 100;

        System.out.println("--- Search Operation Load Test Metrics ---");
        System.out.println("Total Searches: " + numberOfSearches);
        System.out.println("Throughput: " + String.format("%.2f", throughput) + " searches/sec");
        System.out.println("Avg Latency: " + String.format("%.2f", avgResponseTime) + " ms");
        System.out.println("Error Rate: " + String.format("%.2f", errorRate) + "%");

        assertTrue(avgResponseTime < 10, "Search latency should be very low");
    }

    @Test
    @DisplayName("Performance Test: Search Complexity")
    void testSearchPerformance() {
        String searchTerm = "Bitcoin 999";
        long startTime = System.nanoTime();
        
        boolean found = testCoins.stream()
                .anyMatch(c -> c.name.equalsIgnoreCase(searchTerm));
        
        long endTime = System.nanoTime();
        long durationMs = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

        System.out.println("Search Operation took: " + durationMs + " ms for 1000 records");
        assertTrue(durationMs < 50, "Search should complete in under 50ms");
        assertTrue(found);
    }
}
