package com.framasaas.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FranchisePerformanceHistoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FranchisePerformanceHistory getFranchisePerformanceHistorySample1() {
        return new FranchisePerformanceHistory().id(1L).updatedBy("updatedBy1").createddBy("createddBy1");
    }

    public static FranchisePerformanceHistory getFranchisePerformanceHistorySample2() {
        return new FranchisePerformanceHistory().id(2L).updatedBy("updatedBy2").createddBy("createddBy2");
    }

    public static FranchisePerformanceHistory getFranchisePerformanceHistoryRandomSampleGenerator() {
        return new FranchisePerformanceHistory()
            .id(longCount.incrementAndGet())
            .updatedBy(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString());
    }
}
