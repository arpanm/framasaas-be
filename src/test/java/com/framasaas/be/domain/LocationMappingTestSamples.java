package com.framasaas.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LocationMappingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static LocationMapping getLocationMappingSample1() {
        return new LocationMapping().id(1L).locationName("locationName1");
    }

    public static LocationMapping getLocationMappingSample2() {
        return new LocationMapping().id(2L).locationName("locationName2");
    }

    public static LocationMapping getLocationMappingRandomSampleGenerator() {
        return new LocationMapping().id(longCount.incrementAndGet()).locationName(UUID.randomUUID().toString());
    }
}
