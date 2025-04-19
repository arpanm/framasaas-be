package com.framasaas.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FranchiseStatusHistoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FranchiseStatusHistory getFranchiseStatusHistorySample1() {
        return new FranchiseStatusHistory().id(1L).updatedBy("updatedBy1");
    }

    public static FranchiseStatusHistory getFranchiseStatusHistorySample2() {
        return new FranchiseStatusHistory().id(2L).updatedBy("updatedBy2");
    }

    public static FranchiseStatusHistory getFranchiseStatusHistoryRandomSampleGenerator() {
        return new FranchiseStatusHistory().id(longCount.incrementAndGet()).updatedBy(UUID.randomUUID().toString());
    }
}
