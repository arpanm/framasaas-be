package com.framasaas.domain;

import static com.framasaas.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.domain.AddressTestSamples.*;
import static com.framasaas.domain.CustomerTestSamples.*;
import static com.framasaas.domain.FranchiseTestSamples.*;
import static com.framasaas.domain.LocationMappingTestSamples.*;
import static com.framasaas.domain.ServiceOrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Address.class);
        Address address1 = getAddressSample1();
        Address address2 = new Address();
        assertThat(address1).isNotEqualTo(address2);

        address2.setId(address1.getId());
        assertThat(address1).isEqualTo(address2);

        address2 = getAddressSample2();
        assertThat(address1).isNotEqualTo(address2);
    }

    @Test
    void serviceOrderTest() {
        Address address = getAddressRandomSampleGenerator();
        ServiceOrder serviceOrderBack = getServiceOrderRandomSampleGenerator();

        address.addServiceOrder(serviceOrderBack);
        assertThat(address.getServiceOrders()).containsOnly(serviceOrderBack);
        assertThat(serviceOrderBack.getAddress()).isEqualTo(address);

        address.removeServiceOrder(serviceOrderBack);
        assertThat(address.getServiceOrders()).doesNotContain(serviceOrderBack);
        assertThat(serviceOrderBack.getAddress()).isNull();

        address.serviceOrders(new HashSet<>(Set.of(serviceOrderBack)));
        assertThat(address.getServiceOrders()).containsOnly(serviceOrderBack);
        assertThat(serviceOrderBack.getAddress()).isEqualTo(address);

        address.setServiceOrders(new HashSet<>());
        assertThat(address.getServiceOrders()).doesNotContain(serviceOrderBack);
        assertThat(serviceOrderBack.getAddress()).isNull();
    }

    @Test
    void additionalAttributeTest() {
        Address address = getAddressRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        address.addAdditionalAttribute(additionalAttributeBack);
        assertThat(address.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getAddress()).isEqualTo(address);

        address.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(address.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getAddress()).isNull();

        address.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(address.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getAddress()).isEqualTo(address);

        address.setAdditionalAttributes(new HashSet<>());
        assertThat(address.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getAddress()).isNull();
    }

    @Test
    void locationTest() {
        Address address = getAddressRandomSampleGenerator();
        LocationMapping locationMappingBack = getLocationMappingRandomSampleGenerator();

        address.setLocation(locationMappingBack);
        assertThat(address.getLocation()).isEqualTo(locationMappingBack);

        address.location(null);
        assertThat(address.getLocation()).isNull();
    }

    @Test
    void franchiseTest() {
        Address address = getAddressRandomSampleGenerator();
        Franchise franchiseBack = getFranchiseRandomSampleGenerator();

        address.setFranchise(franchiseBack);
        assertThat(address.getFranchise()).isEqualTo(franchiseBack);
        assertThat(franchiseBack.getAddress()).isEqualTo(address);

        address.franchise(null);
        assertThat(address.getFranchise()).isNull();
        assertThat(franchiseBack.getAddress()).isNull();
    }

    @Test
    void customerTest() {
        Address address = getAddressRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        address.setCustomer(customerBack);
        assertThat(address.getCustomer()).isEqualTo(customerBack);

        address.customer(null);
        assertThat(address.getCustomer()).isNull();
    }
}
