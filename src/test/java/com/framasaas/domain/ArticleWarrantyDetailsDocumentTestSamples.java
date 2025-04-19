package com.framasaas.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArticleWarrantyDetailsDocumentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ArticleWarrantyDetailsDocument getArticleWarrantyDetailsDocumentSample1() {
        return new ArticleWarrantyDetailsDocument()
            .id(1L)
            .documentPath("documentPath1")
            .validatedBy("validatedBy1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static ArticleWarrantyDetailsDocument getArticleWarrantyDetailsDocumentSample2() {
        return new ArticleWarrantyDetailsDocument()
            .id(2L)
            .documentPath("documentPath2")
            .validatedBy("validatedBy2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static ArticleWarrantyDetailsDocument getArticleWarrantyDetailsDocumentRandomSampleGenerator() {
        return new ArticleWarrantyDetailsDocument()
            .id(longCount.incrementAndGet())
            .documentPath(UUID.randomUUID().toString())
            .validatedBy(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
