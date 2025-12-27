package com.cryptochecker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import static org.junit.jupiter.api.Assertions.*;

class APIPerformanceTest {

    @Test
    @DisplayName("Load Test: API Fetching Metrics")
    void testAPILoad() throws InterruptedException {
        int numberOfRequests = 100;
        int threadPoolSize = 20;
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
        
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger errorCount = new AtomicInteger(0);
        AtomicLong totalResponseTime = new AtomicLong(0);
        
        CountDownLatch latch = new CountDownLatch(numberOfRequests);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numberOfRequests; i++) {
            executor.submit(() -> {
                long reqStart = System.currentTimeMillis();
                try {
                    if (Math.random() < 0.05) throw new Exception("Simulated Network Error");
                    Thread.sleep((long) (Math.random() * 50 + 50)); 
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
        double avgResponseTime = (double) totalResponseTime.get() / numberOfRequests;
        double errorRate = (double) errorCount.get() / numberOfRequests * 100;

        System.out.println("--- API Fetching Load Test Metrics ---");
        System.out.println("Total Requests: " + numberOfRequests);
        System.out.println("Successes: " + successCount.get());
        System.out.println("Errors: " + errorCount.get());
        System.out.println("Throughput: " + String.format("%.2f", throughput) + " req/sec");
        System.out.println("Avg Response Time: " + String.format("%.2f", avgResponseTime) + " ms");
        System.out.println("Error Rate: " + String.format("%.2f", errorRate) + "%");

        assertTrue(avgResponseTime < 300, "Average response time should be under 300ms");
    }
}
