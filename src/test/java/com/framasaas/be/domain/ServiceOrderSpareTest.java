package com.framasaas.be.domain;

import static com.framasaas.be.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.be.domain.ProductTestSamples.*;
import static com.framasaas.be.domain.ServiceOrderSpareTestSamples.*;
import static com.framasaas.be.domain.ServiceOrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ServiceOrderSpareTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOrderSpare.class);
        ServiceOrderSpare serviceOrderSpare1 = getServiceOrderSpareSample1();
        ServiceOrderSpare serviceOrderSpare2 = new ServiceOrderSpare();
        assertThat(serviceOrderSpare1).isNotEqualTo(serviceOrderSpare2);

        serviceOrderSpare2.setId(serviceOrderSpare1.getId());
        assertThat(serviceOrderSpare1).isEqualTo(serviceOrderSpare2);

        serviceOrderSpare2 = getServiceOrderSpareSample2();
        assertThat(serviceOrderSpare1).isNotEqualTo(serviceOrderSpare2);
    }

    @Test
    void additionalAttributeTest() {
        ServiceOrderSpare serviceOrderSpare = getServiceOrderSpareRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        serviceOrderSpare.addAdditionalAttribute(additionalAttributeBack);
        assertThat(serviceOrderSpare.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrderSpare()).isEqualTo(serviceOrderSpare);

        serviceOrderSpare.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(serviceOrderSpare.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrderSpare()).isNull();

        serviceOrderSpare.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(serviceOrderSpare.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrderSpare()).isEqualTo(serviceOrderSpare);

        serviceOrderSpare.setAdditionalAttributes(new HashSet<>());
        assertThat(serviceOrderSpare.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrderSpare()).isNull();
    }

    @Test
    void serviceOrderTest() {
        ServiceOrderSpare serviceOrderSpare = getServiceOrderSpareRandomSampleGenerator();
        ServiceOrder serviceOrderBack = getServiceOrderRandomSampleGenerator();

        serviceOrderSpare.setServiceOrder(serviceOrderBack);
        assertThat(serviceOrderSpare.getServiceOrder()).isEqualTo(serviceOrderBack);

        serviceOrderSpare.serviceOrder(null);
        assertThat(serviceOrderSpare.getServiceOrder()).isNull();
    }

    @Test
    void productTest() {
        ServiceOrderSpare serviceOrderSpare = getServiceOrderSpareRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        serviceOrderSpare.setProduct(productBack);
        assertThat(serviceOrderSpare.getProduct()).isEqualTo(productBack);

        serviceOrderSpare.product(null);
        assertThat(serviceOrderSpare.getProduct()).isNull();
    }
}
