package com.framasaas.be.domain;

import static com.framasaas.be.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.be.domain.AddressTestSamples.*;
import static com.framasaas.be.domain.ArticleTestSamples.*;
import static com.framasaas.be.domain.CustomerTestSamples.*;
import static com.framasaas.be.domain.ServiceOrderAssignmentTestSamples.*;
import static com.framasaas.be.domain.ServiceOrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ServiceOrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOrder.class);
        ServiceOrder serviceOrder1 = getServiceOrderSample1();
        ServiceOrder serviceOrder2 = new ServiceOrder();
        assertThat(serviceOrder1).isNotEqualTo(serviceOrder2);

        serviceOrder2.setId(serviceOrder1.getId());
        assertThat(serviceOrder1).isEqualTo(serviceOrder2);

        serviceOrder2 = getServiceOrderSample2();
        assertThat(serviceOrder1).isNotEqualTo(serviceOrder2);
    }

    @Test
    void serviceOrderAssignmentTest() {
        ServiceOrder serviceOrder = getServiceOrderRandomSampleGenerator();
        ServiceOrderAssignment serviceOrderAssignmentBack = getServiceOrderAssignmentRandomSampleGenerator();

        serviceOrder.addServiceOrderAssignment(serviceOrderAssignmentBack);
        assertThat(serviceOrder.getServiceOrderAssignments()).containsOnly(serviceOrderAssignmentBack);
        assertThat(serviceOrderAssignmentBack.getServiceOrder()).isEqualTo(serviceOrder);

        serviceOrder.removeServiceOrderAssignment(serviceOrderAssignmentBack);
        assertThat(serviceOrder.getServiceOrderAssignments()).doesNotContain(serviceOrderAssignmentBack);
        assertThat(serviceOrderAssignmentBack.getServiceOrder()).isNull();

        serviceOrder.serviceOrderAssignments(new HashSet<>(Set.of(serviceOrderAssignmentBack)));
        assertThat(serviceOrder.getServiceOrderAssignments()).containsOnly(serviceOrderAssignmentBack);
        assertThat(serviceOrderAssignmentBack.getServiceOrder()).isEqualTo(serviceOrder);

        serviceOrder.setServiceOrderAssignments(new HashSet<>());
        assertThat(serviceOrder.getServiceOrderAssignments()).doesNotContain(serviceOrderAssignmentBack);
        assertThat(serviceOrderAssignmentBack.getServiceOrder()).isNull();
    }

    @Test
    void additionalAttributeTest() {
        ServiceOrder serviceOrder = getServiceOrderRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        serviceOrder.addAdditionalAttribute(additionalAttributeBack);
        assertThat(serviceOrder.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrder()).isEqualTo(serviceOrder);

        serviceOrder.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(serviceOrder.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrder()).isNull();

        serviceOrder.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(serviceOrder.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrder()).isEqualTo(serviceOrder);

        serviceOrder.setAdditionalAttributes(new HashSet<>());
        assertThat(serviceOrder.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrder()).isNull();
    }

    @Test
    void customerTest() {
        ServiceOrder serviceOrder = getServiceOrderRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        serviceOrder.setCustomer(customerBack);
        assertThat(serviceOrder.getCustomer()).isEqualTo(customerBack);

        serviceOrder.customer(null);
        assertThat(serviceOrder.getCustomer()).isNull();
    }

    @Test
    void articleTest() {
        ServiceOrder serviceOrder = getServiceOrderRandomSampleGenerator();
        Article articleBack = getArticleRandomSampleGenerator();

        serviceOrder.setArticle(articleBack);
        assertThat(serviceOrder.getArticle()).isEqualTo(articleBack);

        serviceOrder.article(null);
        assertThat(serviceOrder.getArticle()).isNull();
    }

    @Test
    void addressTest() {
        ServiceOrder serviceOrder = getServiceOrderRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        serviceOrder.setAddress(addressBack);
        assertThat(serviceOrder.getAddress()).isEqualTo(addressBack);

        serviceOrder.address(null);
        assertThat(serviceOrder.getAddress()).isNull();
    }
}
