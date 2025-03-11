package com.framasaas.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ServiceOrderSpareTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ServiceOrderSpare getServiceOrderSpareSample1() {
        return new ServiceOrderSpare().id(1L).createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static ServiceOrderSpare getServiceOrderSpareSample2() {
        return new ServiceOrderSpare().id(2L).createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static ServiceOrderSpare getServiceOrderSpareRandomSampleGenerator() {
        return new ServiceOrderSpare()
            .id(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
