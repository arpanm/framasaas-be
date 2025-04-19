package com.framasaas.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LanguageMappingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static LanguageMapping getLanguageMappingSample1() {
        return new LanguageMapping().id(1L).createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static LanguageMapping getLanguageMappingSample2() {
        return new LanguageMapping().id(2L).createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static LanguageMapping getLanguageMappingRandomSampleGenerator() {
        return new LanguageMapping()
            .id(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
