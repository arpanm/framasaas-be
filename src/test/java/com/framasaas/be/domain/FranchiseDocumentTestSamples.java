package com.framasaas.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FranchiseDocumentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FranchiseDocument getFranchiseDocumentSample1() {
        return new FranchiseDocument()
            .id(1L)
            .documentName("documentName1")
            .documentSize(1L)
            .documentPath("documentPath1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static FranchiseDocument getFranchiseDocumentSample2() {
        return new FranchiseDocument()
            .id(2L)
            .documentName("documentName2")
            .documentSize(2L)
            .documentPath("documentPath2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static FranchiseDocument getFranchiseDocumentRandomSampleGenerator() {
        return new FranchiseDocument()
            .id(longCount.incrementAndGet())
            .documentName(UUID.randomUUID().toString())
            .documentSize(longCount.incrementAndGet())
            .documentPath(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
