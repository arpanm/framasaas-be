package com.framasaas.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AddressTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Address getAddressSample1() {
        return new Address()
            .id(1L)
            .address1("address11")
            .address2("address21")
            .city("city1")
            .area("area1")
            .district("district1")
            .pincode(1L)
            .state("state1")
            .country("country1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static Address getAddressSample2() {
        return new Address()
            .id(2L)
            .address1("address12")
            .address2("address22")
            .city("city2")
            .area("area2")
            .district("district2")
            .pincode(2L)
            .state("state2")
            .country("country2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static Address getAddressRandomSampleGenerator() {
        return new Address()
            .id(longCount.incrementAndGet())
            .address1(UUID.randomUUID().toString())
            .address2(UUID.randomUUID().toString())
            .city(UUID.randomUUID().toString())
            .area(UUID.randomUUID().toString())
            .district(UUID.randomUUID().toString())
            .pincode(longCount.incrementAndGet())
            .state(UUID.randomUUID().toString())
            .country(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
