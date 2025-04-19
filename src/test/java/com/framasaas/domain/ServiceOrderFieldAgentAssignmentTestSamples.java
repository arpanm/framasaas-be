package com.framasaas.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ServiceOrderFieldAgentAssignmentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ServiceOrderFieldAgentAssignment getServiceOrderFieldAgentAssignmentSample1() {
        return new ServiceOrderFieldAgentAssignment()
            .id(1L)
            .nps(1L)
            .completionOTP(1L)
            .cancellationOTP(1L)
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static ServiceOrderFieldAgentAssignment getServiceOrderFieldAgentAssignmentSample2() {
        return new ServiceOrderFieldAgentAssignment()
            .id(2L)
            .nps(2L)
            .completionOTP(2L)
            .cancellationOTP(2L)
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static ServiceOrderFieldAgentAssignment getServiceOrderFieldAgentAssignmentRandomSampleGenerator() {
        return new ServiceOrderFieldAgentAssignment()
            .id(longCount.incrementAndGet())
            .nps(longCount.incrementAndGet())
            .completionOTP(longCount.incrementAndGet())
            .cancellationOTP(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
