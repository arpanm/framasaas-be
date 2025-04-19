package com.framasaas.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ServiceOrderPaymentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ServiceOrderPayment getServiceOrderPaymentSample1() {
        return new ServiceOrderPayment()
            .id(1L)
            .paymentLink("paymentLink1")
            .pgTxnId("pgTxnId1")
            .pgTxnResponse("pgTxnResponse1")
            .paymentInitiatedBy("paymentInitiatedBy1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static ServiceOrderPayment getServiceOrderPaymentSample2() {
        return new ServiceOrderPayment()
            .id(2L)
            .paymentLink("paymentLink2")
            .pgTxnId("pgTxnId2")
            .pgTxnResponse("pgTxnResponse2")
            .paymentInitiatedBy("paymentInitiatedBy2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static ServiceOrderPayment getServiceOrderPaymentRandomSampleGenerator() {
        return new ServiceOrderPayment()
            .id(longCount.incrementAndGet())
            .paymentLink(UUID.randomUUID().toString())
            .pgTxnId(UUID.randomUUID().toString())
            .pgTxnResponse(UUID.randomUUID().toString())
            .paymentInitiatedBy(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
