package com.framasaas.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CustomerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Customer getCustomerSample1() {
        return new Customer().id(1L).email("email1").contact(1L).alternameContact(1L).createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static Customer getCustomerSample2() {
        return new Customer().id(2L).email("email2").contact(2L).alternameContact(2L).createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static Customer getCustomerRandomSampleGenerator() {
        return new Customer()
            .id(longCount.incrementAndGet())
            .email(UUID.randomUUID().toString())
            .contact(longCount.incrementAndGet())
            .alternameContact(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
