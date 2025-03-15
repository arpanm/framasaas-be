package com.framasaas.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArticleTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Article getArticleSample1() {
        return new Article()
            .id(1L)
            .serialNo("serialNo1")
            .vendorArticleId("vendorArticleId1")
            .purchaseStore("purchaseStore1")
            .invoicePath("invoicePath1")
            .validatedBy("validatedBy1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static Article getArticleSample2() {
        return new Article()
            .id(2L)
            .serialNo("serialNo2")
            .vendorArticleId("vendorArticleId2")
            .purchaseStore("purchaseStore2")
            .invoicePath("invoicePath2")
            .validatedBy("validatedBy2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static Article getArticleRandomSampleGenerator() {
        return new Article()
            .id(longCount.incrementAndGet())
            .serialNo(UUID.randomUUID().toString())
            .vendorArticleId(UUID.randomUUID().toString())
            .purchaseStore(UUID.randomUUID().toString())
            .invoicePath(UUID.randomUUID().toString())
            .validatedBy(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
