package com.framasaas.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FranchiseAllocationRuleTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FranchiseAllocationRule getFranchiseAllocationRuleSample1() {
        return new FranchiseAllocationRule().id(1L).createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static FranchiseAllocationRule getFranchiseAllocationRuleSample2() {
        return new FranchiseAllocationRule().id(2L).createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static FranchiseAllocationRule getFranchiseAllocationRuleRandomSampleGenerator() {
        return new FranchiseAllocationRule()
            .id(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
