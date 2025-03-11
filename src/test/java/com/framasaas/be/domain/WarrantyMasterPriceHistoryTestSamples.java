package com.framasaas.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WarrantyMasterPriceHistoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static WarrantyMasterPriceHistory getWarrantyMasterPriceHistorySample1() {
        return new WarrantyMasterPriceHistory().id(1L).updatedBy("updatedBy1");
    }

    public static WarrantyMasterPriceHistory getWarrantyMasterPriceHistorySample2() {
        return new WarrantyMasterPriceHistory().id(2L).updatedBy("updatedBy2");
    }

    public static WarrantyMasterPriceHistory getWarrantyMasterPriceHistoryRandomSampleGenerator() {
        return new WarrantyMasterPriceHistory().id(longCount.incrementAndGet()).updatedBy(UUID.randomUUID().toString());
    }
}
