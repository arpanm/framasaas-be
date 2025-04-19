package com.framasaas.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ServiceOrderMasterTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ServiceOrderMaster getServiceOrderMasterSample1() {
        return new ServiceOrderMaster().id(1L).slaInHours(1L).createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static ServiceOrderMaster getServiceOrderMasterSample2() {
        return new ServiceOrderMaster().id(2L).slaInHours(2L).createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static ServiceOrderMaster getServiceOrderMasterRandomSampleGenerator() {
        return new ServiceOrderMaster()
            .id(longCount.incrementAndGet())
            .slaInHours(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
