package com.framasaas.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FranchiseBrandMappingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FranchiseBrandMapping getFranchiseBrandMappingSample1() {
        return new FranchiseBrandMapping().id(1L).createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static FranchiseBrandMapping getFranchiseBrandMappingSample2() {
        return new FranchiseBrandMapping().id(2L).createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static FranchiseBrandMapping getFranchiseBrandMappingRandomSampleGenerator() {
        return new FranchiseBrandMapping()
            .id(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
