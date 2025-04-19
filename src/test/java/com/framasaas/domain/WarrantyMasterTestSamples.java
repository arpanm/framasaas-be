package com.framasaas.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WarrantyMasterTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static WarrantyMaster getWarrantyMasterSample1() {
        return new WarrantyMaster()
            .id(1L)
            .name("name1")
            .vendorWarrantyMasterId("vendorWarrantyMasterId1")
            .description("description1")
            .periodInMonths(1L)
            .coverage("coverage1")
            .exclusion("exclusion1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static WarrantyMaster getWarrantyMasterSample2() {
        return new WarrantyMaster()
            .id(2L)
            .name("name2")
            .vendorWarrantyMasterId("vendorWarrantyMasterId2")
            .description("description2")
            .periodInMonths(2L)
            .coverage("coverage2")
            .exclusion("exclusion2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static WarrantyMaster getWarrantyMasterRandomSampleGenerator() {
        return new WarrantyMaster()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .vendorWarrantyMasterId(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .periodInMonths(longCount.incrementAndGet())
            .coverage(UUID.randomUUID().toString())
            .exclusion(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
