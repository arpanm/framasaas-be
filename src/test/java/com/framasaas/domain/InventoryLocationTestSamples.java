package com.framasaas.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InventoryLocationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static InventoryLocation getInventoryLocationSample1() {
        return new InventoryLocation().id(1L).name("name1").createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static InventoryLocation getInventoryLocationSample2() {
        return new InventoryLocation().id(2L).name("name2").createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static InventoryLocation getInventoryLocationRandomSampleGenerator() {
        return new InventoryLocation()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
