package com.framasaas.be.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class FranchiseCategoryMappingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FranchiseCategoryMapping getFranchiseCategoryMappingSample1() {
        return new FranchiseCategoryMapping().id(1L);
    }

    public static FranchiseCategoryMapping getFranchiseCategoryMappingSample2() {
        return new FranchiseCategoryMapping().id(2L);
    }

    public static FranchiseCategoryMapping getFranchiseCategoryMappingRandomSampleGenerator() {
        return new FranchiseCategoryMapping().id(longCount.incrementAndGet());
    }
}
