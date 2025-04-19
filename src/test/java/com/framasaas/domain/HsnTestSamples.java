package com.framasaas.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class HsnTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Hsn getHsnSample1() {
        return new Hsn().id(1L).hsnCD("hsnCD1").description("description1").createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static Hsn getHsnSample2() {
        return new Hsn().id(2L).hsnCD("hsnCD2").description("description2").createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static Hsn getHsnRandomSampleGenerator() {
        return new Hsn()
            .id(longCount.incrementAndGet())
            .hsnCD(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
