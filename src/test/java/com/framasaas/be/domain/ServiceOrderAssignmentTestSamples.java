package com.framasaas.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ServiceOrderAssignmentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ServiceOrderAssignment getServiceOrderAssignmentSample1() {
        return new ServiceOrderAssignment()
            .id(1L)
            .nps(1L)
            .completionOTP(1L)
            .cancellationOTP(1L)
            .franchiseInvoicePath("franchiseInvoicePath1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static ServiceOrderAssignment getServiceOrderAssignmentSample2() {
        return new ServiceOrderAssignment()
            .id(2L)
            .nps(2L)
            .completionOTP(2L)
            .cancellationOTP(2L)
            .franchiseInvoicePath("franchiseInvoicePath2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static ServiceOrderAssignment getServiceOrderAssignmentRandomSampleGenerator() {
        return new ServiceOrderAssignment()
            .id(longCount.incrementAndGet())
            .nps(longCount.incrementAndGet())
            .completionOTP(longCount.incrementAndGet())
            .cancellationOTP(longCount.incrementAndGet())
            .franchiseInvoicePath(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
