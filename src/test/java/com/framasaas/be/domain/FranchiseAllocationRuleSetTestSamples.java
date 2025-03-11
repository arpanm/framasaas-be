package com.framasaas.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FranchiseAllocationRuleSetTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FranchiseAllocationRuleSet getFranchiseAllocationRuleSetSample1() {
        return new FranchiseAllocationRuleSet().id(1L).name("name1").priority(1L).createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static FranchiseAllocationRuleSet getFranchiseAllocationRuleSetSample2() {
        return new FranchiseAllocationRuleSet().id(2L).name("name2").priority(2L).createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static FranchiseAllocationRuleSet getFranchiseAllocationRuleSetRandomSampleGenerator() {
        return new FranchiseAllocationRuleSet()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .priority(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
