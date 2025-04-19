package com.framasaas.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AdditionalAttributePossibleValueTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AdditionalAttributePossibleValue getAdditionalAttributePossibleValueSample1() {
        return new AdditionalAttributePossibleValue()
            .id(1L)
            .possibleValue("possibleValue1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static AdditionalAttributePossibleValue getAdditionalAttributePossibleValueSample2() {
        return new AdditionalAttributePossibleValue()
            .id(2L)
            .possibleValue("possibleValue2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static AdditionalAttributePossibleValue getAdditionalAttributePossibleValueRandomSampleGenerator() {
        return new AdditionalAttributePossibleValue()
            .id(longCount.incrementAndGet())
            .possibleValue(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
