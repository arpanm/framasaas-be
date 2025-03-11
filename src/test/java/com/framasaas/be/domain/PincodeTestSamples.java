package com.framasaas.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PincodeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Pincode getPincodeSample1() {
        return new Pincode().id(1L).pincode("pincode1").createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static Pincode getPincodeSample2() {
        return new Pincode().id(2L).pincode("pincode2").createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static Pincode getPincodeRandomSampleGenerator() {
        return new Pincode()
            .id(longCount.incrementAndGet())
            .pincode(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
