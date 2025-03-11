package com.framasaas.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProductTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Product getProductSample1() {
        return new Product()
            .id(1L)
            .productName("productName1")
            .vendorProductId("vendorProductId1")
            .description("description1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static Product getProductSample2() {
        return new Product()
            .id(2L)
            .productName("productName2")
            .vendorProductId("vendorProductId2")
            .description("description2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static Product getProductRandomSampleGenerator() {
        return new Product()
            .id(longCount.incrementAndGet())
            .productName(UUID.randomUUID().toString())
            .vendorProductId(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
