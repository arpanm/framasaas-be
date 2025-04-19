package com.framasaas.domain;

import static com.framasaas.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.domain.ServiceOrderPaymentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ServiceOrderPaymentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOrderPayment.class);
        ServiceOrderPayment serviceOrderPayment1 = getServiceOrderPaymentSample1();
        ServiceOrderPayment serviceOrderPayment2 = new ServiceOrderPayment();
        assertThat(serviceOrderPayment1).isNotEqualTo(serviceOrderPayment2);

        serviceOrderPayment2.setId(serviceOrderPayment1.getId());
        assertThat(serviceOrderPayment1).isEqualTo(serviceOrderPayment2);

        serviceOrderPayment2 = getServiceOrderPaymentSample2();
        assertThat(serviceOrderPayment1).isNotEqualTo(serviceOrderPayment2);
    }

    @Test
    void additionalAttributeTest() {
        ServiceOrderPayment serviceOrderPayment = getServiceOrderPaymentRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        serviceOrderPayment.addAdditionalAttribute(additionalAttributeBack);
        assertThat(serviceOrderPayment.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrderPayment()).isEqualTo(serviceOrderPayment);

        serviceOrderPayment.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(serviceOrderPayment.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrderPayment()).isNull();

        serviceOrderPayment.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(serviceOrderPayment.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrderPayment()).isEqualTo(serviceOrderPayment);

        serviceOrderPayment.setAdditionalAttributes(new HashSet<>());
        assertThat(serviceOrderPayment.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrderPayment()).isNull();
    }
}
