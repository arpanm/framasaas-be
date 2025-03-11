package com.framasaas.be.domain;

import static com.framasaas.be.domain.AdditionalAttributePossibleValueTestSamples.*;
import static com.framasaas.be.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.be.domain.AddressTestSamples.*;
import static com.framasaas.be.domain.CustomerTestSamples.*;
import static com.framasaas.be.domain.FranchiseDocumentTestSamples.*;
import static com.framasaas.be.domain.FranchisePerformanceHistoryTestSamples.*;
import static com.framasaas.be.domain.FranchiseStatusHistoryTestSamples.*;
import static com.framasaas.be.domain.FranchiseTestSamples.*;
import static com.framasaas.be.domain.FranchiseUserTestSamples.*;
import static com.framasaas.be.domain.LocationMappingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AdditionalAttributeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdditionalAttribute.class);
        AdditionalAttribute additionalAttribute1 = getAdditionalAttributeSample1();
        AdditionalAttribute additionalAttribute2 = new AdditionalAttribute();
        assertThat(additionalAttribute1).isNotEqualTo(additionalAttribute2);

        additionalAttribute2.setId(additionalAttribute1.getId());
        assertThat(additionalAttribute1).isEqualTo(additionalAttribute2);

        additionalAttribute2 = getAdditionalAttributeSample2();
        assertThat(additionalAttribute1).isNotEqualTo(additionalAttribute2);
    }

    @Test
    void additionalAttributePossibleValueTest() {
        AdditionalAttribute additionalAttribute = getAdditionalAttributeRandomSampleGenerator();
        AdditionalAttributePossibleValue additionalAttributePossibleValueBack = getAdditionalAttributePossibleValueRandomSampleGenerator();

        additionalAttribute.addAdditionalAttributePossibleValue(additionalAttributePossibleValueBack);
        assertThat(additionalAttribute.getAdditionalAttributePossibleValues()).containsOnly(additionalAttributePossibleValueBack);
        assertThat(additionalAttributePossibleValueBack.getAttribute()).isEqualTo(additionalAttribute);

        additionalAttribute.removeAdditionalAttributePossibleValue(additionalAttributePossibleValueBack);
        assertThat(additionalAttribute.getAdditionalAttributePossibleValues()).doesNotContain(additionalAttributePossibleValueBack);
        assertThat(additionalAttributePossibleValueBack.getAttribute()).isNull();

        additionalAttribute.additionalAttributePossibleValues(new HashSet<>(Set.of(additionalAttributePossibleValueBack)));
        assertThat(additionalAttribute.getAdditionalAttributePossibleValues()).containsOnly(additionalAttributePossibleValueBack);
        assertThat(additionalAttributePossibleValueBack.getAttribute()).isEqualTo(additionalAttribute);

        additionalAttribute.setAdditionalAttributePossibleValues(new HashSet<>());
        assertThat(additionalAttribute.getAdditionalAttributePossibleValues()).doesNotContain(additionalAttributePossibleValueBack);
        assertThat(additionalAttributePossibleValueBack.getAttribute()).isNull();
    }

    @Test
    void franchiseTest() {
        AdditionalAttribute additionalAttribute = getAdditionalAttributeRandomSampleGenerator();
        Franchise franchiseBack = getFranchiseRandomSampleGenerator();

        additionalAttribute.setFranchise(franchiseBack);
        assertThat(additionalAttribute.getFranchise()).isEqualTo(franchiseBack);

        additionalAttribute.franchise(null);
        assertThat(additionalAttribute.getFranchise()).isNull();
    }

    @Test
    void franchiseStatusTest() {
        AdditionalAttribute additionalAttribute = getAdditionalAttributeRandomSampleGenerator();
        FranchiseStatusHistory franchiseStatusHistoryBack = getFranchiseStatusHistoryRandomSampleGenerator();

        additionalAttribute.setFranchiseStatus(franchiseStatusHistoryBack);
        assertThat(additionalAttribute.getFranchiseStatus()).isEqualTo(franchiseStatusHistoryBack);

        additionalAttribute.franchiseStatus(null);
        assertThat(additionalAttribute.getFranchiseStatus()).isNull();
    }

    @Test
    void franchisePerformanceTest() {
        AdditionalAttribute additionalAttribute = getAdditionalAttributeRandomSampleGenerator();
        FranchisePerformanceHistory franchisePerformanceHistoryBack = getFranchisePerformanceHistoryRandomSampleGenerator();

        additionalAttribute.setFranchisePerformance(franchisePerformanceHistoryBack);
        assertThat(additionalAttribute.getFranchisePerformance()).isEqualTo(franchisePerformanceHistoryBack);

        additionalAttribute.franchisePerformance(null);
        assertThat(additionalAttribute.getFranchisePerformance()).isNull();
    }

    @Test
    void addressTest() {
        AdditionalAttribute additionalAttribute = getAdditionalAttributeRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        additionalAttribute.setAddress(addressBack);
        assertThat(additionalAttribute.getAddress()).isEqualTo(addressBack);

        additionalAttribute.address(null);
        assertThat(additionalAttribute.getAddress()).isNull();
    }

    @Test
    void locationTest() {
        AdditionalAttribute additionalAttribute = getAdditionalAttributeRandomSampleGenerator();
        LocationMapping locationMappingBack = getLocationMappingRandomSampleGenerator();

        additionalAttribute.setLocation(locationMappingBack);
        assertThat(additionalAttribute.getLocation()).isEqualTo(locationMappingBack);

        additionalAttribute.location(null);
        assertThat(additionalAttribute.getLocation()).isNull();
    }

    @Test
    void franchiseUserTest() {
        AdditionalAttribute additionalAttribute = getAdditionalAttributeRandomSampleGenerator();
        FranchiseUser franchiseUserBack = getFranchiseUserRandomSampleGenerator();

        additionalAttribute.setFranchiseUser(franchiseUserBack);
        assertThat(additionalAttribute.getFranchiseUser()).isEqualTo(franchiseUserBack);

        additionalAttribute.franchiseUser(null);
        assertThat(additionalAttribute.getFranchiseUser()).isNull();
    }

    @Test
    void customerTest() {
        AdditionalAttribute additionalAttribute = getAdditionalAttributeRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        additionalAttribute.setCustomer(customerBack);
        assertThat(additionalAttribute.getCustomer()).isEqualTo(customerBack);

        additionalAttribute.customer(null);
        assertThat(additionalAttribute.getCustomer()).isNull();
    }

    @Test
    void documentTest() {
        AdditionalAttribute additionalAttribute = getAdditionalAttributeRandomSampleGenerator();
        FranchiseDocument franchiseDocumentBack = getFranchiseDocumentRandomSampleGenerator();

        additionalAttribute.setDocument(franchiseDocumentBack);
        assertThat(additionalAttribute.getDocument()).isEqualTo(franchiseDocumentBack);

        additionalAttribute.document(null);
        assertThat(additionalAttribute.getDocument()).isNull();
    }
}
