package com.framasaas.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FranchiseTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Franchise getFranchiseSample1() {
        return new Franchise()
            .id(1L)
            .franchiseName("franchiseName1")
            .owner("owner1")
            .email("email1")
            .contact(1L)
            .gstNumber("gstNumber1")
            .registrationNumber("registrationNumber1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static Franchise getFranchiseSample2() {
        return new Franchise()
            .id(2L)
            .franchiseName("franchiseName2")
            .owner("owner2")
            .email("email2")
            .contact(2L)
            .gstNumber("gstNumber2")
            .registrationNumber("registrationNumber2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static Franchise getFranchiseRandomSampleGenerator() {
        return new Franchise()
            .id(longCount.incrementAndGet())
            .franchiseName(UUID.randomUUID().toString())
            .owner(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .contact(longCount.incrementAndGet())
            .gstNumber(UUID.randomUUID().toString())
            .registrationNumber(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
