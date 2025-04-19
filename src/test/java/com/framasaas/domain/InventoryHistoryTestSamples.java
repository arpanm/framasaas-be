package com.framasaas.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InventoryHistoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static InventoryHistory getInventoryHistorySample1() {
        return new InventoryHistory().id(1L).inventoryValue(1L).updatedBy("updatedBy1");
    }

    public static InventoryHistory getInventoryHistorySample2() {
        return new InventoryHistory().id(2L).inventoryValue(2L).updatedBy("updatedBy2");
    }

    public static InventoryHistory getInventoryHistoryRandomSampleGenerator() {
        return new InventoryHistory()
            .id(longCount.incrementAndGet())
            .inventoryValue(longCount.incrementAndGet())
            .updatedBy(UUID.randomUUID().toString());
    }
}
