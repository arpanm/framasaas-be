package com.framasaas.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ServiceOrderTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ServiceOrder getServiceOrderSample1() {
        return new ServiceOrder()
            .id(1L)
            .description("description1")
            .customerInvoicePath("customerInvoicePath1")
            .priority(1L)
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static ServiceOrder getServiceOrderSample2() {
        return new ServiceOrder()
            .id(2L)
            .description("description2")
            .customerInvoicePath("customerInvoicePath2")
            .priority(2L)
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static ServiceOrder getServiceOrderRandomSampleGenerator() {
        return new ServiceOrder()
            .id(longCount.incrementAndGet())
            .description(UUID.randomUUID().toString())
            .customerInvoicePath(UUID.randomUUID().toString())
            .priority(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
