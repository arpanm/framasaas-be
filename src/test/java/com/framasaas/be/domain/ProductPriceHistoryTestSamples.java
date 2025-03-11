package com.framasaas.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProductPriceHistoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ProductPriceHistory getProductPriceHistorySample1() {
        return new ProductPriceHistory().id(1L).updateDescription("updateDescription1").updatedBy("updatedBy1");
    }

    public static ProductPriceHistory getProductPriceHistorySample2() {
        return new ProductPriceHistory().id(2L).updateDescription("updateDescription2").updatedBy("updatedBy2");
    }

    public static ProductPriceHistory getProductPriceHistoryRandomSampleGenerator() {
        return new ProductPriceHistory()
            .id(longCount.incrementAndGet())
            .updateDescription(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
