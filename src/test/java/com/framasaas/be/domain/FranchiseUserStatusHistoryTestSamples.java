package com.framasaas.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FranchiseUserStatusHistoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FranchiseUserStatusHistory getFranchiseUserStatusHistorySample1() {
        return new FranchiseUserStatusHistory().id(1L).updatedBy("updatedBy1");
    }

    public static FranchiseUserStatusHistory getFranchiseUserStatusHistorySample2() {
        return new FranchiseUserStatusHistory().id(2L).updatedBy("updatedBy2");
    }

    public static FranchiseUserStatusHistory getFranchiseUserStatusHistoryRandomSampleGenerator() {
        return new FranchiseUserStatusHistory().id(longCount.incrementAndGet()).updatedBy(UUID.randomUUID().toString());
    }
}
