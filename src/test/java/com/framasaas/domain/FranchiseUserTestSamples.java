package com.framasaas.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FranchiseUserTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FranchiseUser getFranchiseUserSample1() {
        return new FranchiseUser()
            .id(1L)
            .userName("userName1")
            .email("email1")
            .contact(1L)
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static FranchiseUser getFranchiseUserSample2() {
        return new FranchiseUser()
            .id(2L)
            .userName("userName2")
            .email("email2")
            .contact(2L)
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static FranchiseUser getFranchiseUserRandomSampleGenerator() {
        return new FranchiseUser()
            .id(longCount.incrementAndGet())
            .userName(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .contact(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
