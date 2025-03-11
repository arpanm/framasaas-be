package com.framasaas.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ServiceOrderFranchiseAssignmentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ServiceOrderFranchiseAssignment getServiceOrderFranchiseAssignmentSample1() {
        return new ServiceOrderFranchiseAssignment()
            .id(1L)
            .nps(1L)
            .franchiseInvoicePath("franchiseInvoicePath1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static ServiceOrderFranchiseAssignment getServiceOrderFranchiseAssignmentSample2() {
        return new ServiceOrderFranchiseAssignment()
            .id(2L)
            .nps(2L)
            .franchiseInvoicePath("franchiseInvoicePath2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static ServiceOrderFranchiseAssignment getServiceOrderFranchiseAssignmentRandomSampleGenerator() {
        return new ServiceOrderFranchiseAssignment()
            .id(longCount.incrementAndGet())
            .nps(longCount.incrementAndGet())
            .franchiseInvoicePath(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
