package com.framasaas.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AdditionalAttributeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AdditionalAttribute getAdditionalAttributeSample1() {
        return new AdditionalAttribute()
            .id(1L)
            .attributeName("attributeName1")
            .attributeValue("attributeValue1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static AdditionalAttribute getAdditionalAttributeSample2() {
        return new AdditionalAttribute()
            .id(2L)
            .attributeName("attributeName2")
            .attributeValue("attributeValue2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static AdditionalAttribute getAdditionalAttributeRandomSampleGenerator() {
        return new AdditionalAttribute()
            .id(longCount.incrementAndGet())
            .attributeName(UUID.randomUUID().toString())
            .attributeValue(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
