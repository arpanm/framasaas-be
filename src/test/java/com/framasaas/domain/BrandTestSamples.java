package com.framasaas.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BrandTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Brand getBrandSample1() {
        return new Brand()
            .id(1L)
            .brandName("brandName1")
            .logoPath("logoPath1")
            .vendorBrandId("vendorBrandId1")
            .description("description1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static Brand getBrandSample2() {
        return new Brand()
            .id(2L)
            .brandName("brandName2")
            .logoPath("logoPath2")
            .vendorBrandId("vendorBrandId2")
            .description("description2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static Brand getBrandRandomSampleGenerator() {
        return new Brand()
            .id(longCount.incrementAndGet())
            .brandName(UUID.randomUUID().toString())
            .logoPath(UUID.randomUUID().toString())
            .vendorBrandId(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
