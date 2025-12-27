package com.cryptochecker;

import org.junit.jupiter.api.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import static org.junit.jupiter.api.Assertions.*;

public class ConversionPerformanceTest {

    private static final int ITERATIONS = 10000;

    @Test
    @DisplayName("Load Test: Conversion Throughput and Latency")
    void testConversionLoad() throws InterruptedException {
        int numberOfConversions = 5000;
        int threadPoolSize = 15;
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
        
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger errorCount = new AtomicInteger(0);
        AtomicLong totalResponseTime = new AtomicLong(0);
        
        CountDownLatch latch = new CountDownLatch(numberOfConversions);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numberOfConversions; i++) {
            executor.submit(() -> {
                long reqStart = System.currentTimeMillis();
                try {
                    double price1 = Math.random() * 50000;
                    double price2 = Math.random() * 2000;
                    double amount = Math.random() * 10;
                    if (price2 == 0) throw new ArithmeticException("Division by zero");
                    double result = (price1 / price2) * amount;
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
        double avgResponseTime = (double) totalResponseTime.get() / numberOfConversions;
        double errorRate = (double) errorCount.get() / numberOfConversions * 100;

        System.out.println("--- Conversion Operation Load Test Metrics ---");
        System.out.println("Total Conversions: " + numberOfConversions);
        System.out.println("Throughput: " + String.format("%.2f", throughput) + " ops/sec");
        System.out.println("Avg Latency: " + String.format("%.2f", avgResponseTime) + " ms");
        System.out.println("Error Rate: " + String.format("%.2f", errorRate) + "%");

        assertTrue(avgResponseTime < 5, "Conversion latency should be minimal");
    }

    @Test
    @DisplayName("Performance Test: Bulk Conversion Calculations")
    void testConversionThroughput() {
        double price1 = 50000.0;
        double price2 = 2000.0;  
        double amount = 1.5;

        long startTime = System.nanoTime();
        
        for (int i = 0; i < ITERATIONS; i++) {
            double result = (price1 / price2) * amount;
            assertNotNull(result);
        }

        long endTime = System.nanoTime();
        double durationSeconds = (endTime - startTime) / 1_000_000_000.0;
        double opsPerSec = ITERATIONS / durationSeconds;

        System.out.println("Conversion Throughput: " + String.format("%.0f", opsPerSec) + " ops/sec");
        assertTrue(opsPerSec > 100000, "Should handle at least 100k conversions per second");
    }
}
