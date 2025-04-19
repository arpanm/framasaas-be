package com.framasaas.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SupportingDocumentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SupportingDocument getSupportingDocumentSample1() {
        return new SupportingDocument()
            .id(1L)
            .documentName("documentName1")
            .documentSize(1L)
            .documentPath("documentPath1")
            .validatedBy("validatedBy1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static SupportingDocument getSupportingDocumentSample2() {
        return new SupportingDocument()
            .id(2L)
            .documentName("documentName2")
            .documentSize(2L)
            .documentPath("documentPath2")
            .validatedBy("validatedBy2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static SupportingDocument getSupportingDocumentRandomSampleGenerator() {
        return new SupportingDocument()
            .id(longCount.incrementAndGet())
            .documentName(UUID.randomUUID().toString())
            .documentSize(longCount.incrementAndGet())
            .documentPath(UUID.randomUUID().toString())
            .validatedBy(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
