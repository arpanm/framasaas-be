package com.framasaas.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArticleWarrantyDetailsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ArticleWarrantyDetails getArticleWarrantyDetailsSample1() {
        return new ArticleWarrantyDetails()
            .id(1L)
            .vendorArticleWarrantyId("vendorArticleWarrantyId1")
            .vendorWarrantyMasterId("vendorWarrantyMasterId1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static ArticleWarrantyDetails getArticleWarrantyDetailsSample2() {
        return new ArticleWarrantyDetails()
            .id(2L)
            .vendorArticleWarrantyId("vendorArticleWarrantyId2")
            .vendorWarrantyMasterId("vendorWarrantyMasterId2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static ArticleWarrantyDetails getArticleWarrantyDetailsRandomSampleGenerator() {
        return new ArticleWarrantyDetails()
            .id(longCount.incrementAndGet())
            .vendorArticleWarrantyId(UUID.randomUUID().toString())
            .vendorWarrantyMasterId(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
