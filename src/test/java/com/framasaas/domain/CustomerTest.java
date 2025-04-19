package com.framasaas.domain;

import static com.framasaas.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.domain.AddressTestSamples.*;
import static com.framasaas.domain.ArticleTestSamples.*;
import static com.framasaas.domain.CustomerTestSamples.*;
import static com.framasaas.domain.ServiceOrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CustomerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customer.class);
        Customer customer1 = getCustomerSample1();
        Customer customer2 = new Customer();
        assertThat(customer1).isNotEqualTo(customer2);

        customer2.setId(customer1.getId());
        assertThat(customer1).isEqualTo(customer2);

        customer2 = getCustomerSample2();
        assertThat(customer1).isNotEqualTo(customer2);
    }

    @Test
    void addressTest() {
        Customer customer = getCustomerRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        customer.addAddress(addressBack);
        assertThat(customer.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getCustomer()).isEqualTo(customer);

        customer.removeAddress(addressBack);
        assertThat(customer.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getCustomer()).isNull();

        customer.addresses(new HashSet<>(Set.of(addressBack)));
        assertThat(customer.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getCustomer()).isEqualTo(customer);

        customer.setAddresses(new HashSet<>());
        assertThat(customer.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getCustomer()).isNull();
    }

    @Test
    void articleTest() {
        Customer customer = getCustomerRandomSampleGenerator();
        Article articleBack = getArticleRandomSampleGenerator();

        customer.addArticle(articleBack);
        assertThat(customer.getArticles()).containsOnly(articleBack);
        assertThat(articleBack.getCustomer()).isEqualTo(customer);

        customer.removeArticle(articleBack);
        assertThat(customer.getArticles()).doesNotContain(articleBack);
        assertThat(articleBack.getCustomer()).isNull();

        customer.articles(new HashSet<>(Set.of(articleBack)));
        assertThat(customer.getArticles()).containsOnly(articleBack);
        assertThat(articleBack.getCustomer()).isEqualTo(customer);

        customer.setArticles(new HashSet<>());
        assertThat(customer.getArticles()).doesNotContain(articleBack);
        assertThat(articleBack.getCustomer()).isNull();
    }

    @Test
    void serviceOrderTest() {
        Customer customer = getCustomerRandomSampleGenerator();
        ServiceOrder serviceOrderBack = getServiceOrderRandomSampleGenerator();

        customer.addServiceOrder(serviceOrderBack);
        assertThat(customer.getServiceOrders()).containsOnly(serviceOrderBack);
        assertThat(serviceOrderBack.getCustomer()).isEqualTo(customer);

        customer.removeServiceOrder(serviceOrderBack);
        assertThat(customer.getServiceOrders()).doesNotContain(serviceOrderBack);
        assertThat(serviceOrderBack.getCustomer()).isNull();

        customer.serviceOrders(new HashSet<>(Set.of(serviceOrderBack)));
        assertThat(customer.getServiceOrders()).containsOnly(serviceOrderBack);
        assertThat(serviceOrderBack.getCustomer()).isEqualTo(customer);

        customer.setServiceOrders(new HashSet<>());
        assertThat(customer.getServiceOrders()).doesNotContain(serviceOrderBack);
        assertThat(serviceOrderBack.getCustomer()).isNull();
    }

    @Test
    void additionalAttributeTest() {
        Customer customer = getCustomerRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        customer.addAdditionalAttribute(additionalAttributeBack);
        assertThat(customer.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getCustomer()).isEqualTo(customer);

        customer.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(customer.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getCustomer()).isNull();

        customer.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(customer.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getCustomer()).isEqualTo(customer);

        customer.setAdditionalAttributes(new HashSet<>());
        assertThat(customer.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getCustomer()).isNull();
    }
}
