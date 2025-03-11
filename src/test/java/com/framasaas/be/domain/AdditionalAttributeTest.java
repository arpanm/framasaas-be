package com.framasaas.be.domain;

import static com.framasaas.be.domain.AdditionalAttributePossibleValueTestSamples.*;
import static com.framasaas.be.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.be.domain.AddressTestSamples.*;
import static com.framasaas.be.domain.BrandTestSamples.*;
import static com.framasaas.be.domain.CategoryTestSamples.*;
import static com.framasaas.be.domain.CustomerTestSamples.*;
import static com.framasaas.be.domain.FranchiseDocumentTestSamples.*;
import static com.framasaas.be.domain.FranchisePerformanceHistoryTestSamples.*;
import static com.framasaas.be.domain.FranchiseStatusHistoryTestSamples.*;
import static com.framasaas.be.domain.FranchiseTestSamples.*;
import static com.framasaas.be.domain.FranchiseUserTestSamples.*;
import static com.framasaas.be.domain.HsnTestSamples.*;
import static com.framasaas.be.domain.LocationMappingTestSamples.*;
import static com.framasaas.be.domain.ProductPriceHistoryTestSamples.*;
import static com.framasaas.be.domain.ProductTestSamples.*;
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
    void brandTest() {
        AdditionalAttribute additionalAttribute = getAdditionalAttributeRandomSampleGenerator();
        Brand brandBack = getBrandRandomSampleGenerator();

        additionalAttribute.setBrand(brandBack);
        assertThat(additionalAttribute.getBrand()).isEqualTo(brandBack);

        additionalAttribute.brand(null);
        assertThat(additionalAttribute.getBrand()).isNull();
    }

    @Test
    void categoryTest() {
        AdditionalAttribute additionalAttribute = getAdditionalAttributeRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        additionalAttribute.setCategory(categoryBack);
        assertThat(additionalAttribute.getCategory()).isEqualTo(categoryBack);

        additionalAttribute.category(null);
        assertThat(additionalAttribute.getCategory()).isNull();
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

    @Test
    void productTest() {
        AdditionalAttribute additionalAttribute = getAdditionalAttributeRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        additionalAttribute.setProduct(productBack);
        assertThat(additionalAttribute.getProduct()).isEqualTo(productBack);

        additionalAttribute.product(null);
        assertThat(additionalAttribute.getProduct()).isNull();
    }

    @Test
    void hsnTest() {
        AdditionalAttribute additionalAttribute = getAdditionalAttributeRandomSampleGenerator();
        Hsn hsnBack = getHsnRandomSampleGenerator();

        additionalAttribute.setHsn(hsnBack);
        assertThat(additionalAttribute.getHsn()).isEqualTo(hsnBack);

        additionalAttribute.hsn(null);
        assertThat(additionalAttribute.getHsn()).isNull();
    }

    @Test
    void priceHistoryTest() {
        AdditionalAttribute additionalAttribute = getAdditionalAttributeRandomSampleGenerator();
        ProductPriceHistory productPriceHistoryBack = getProductPriceHistoryRandomSampleGenerator();

        additionalAttribute.setPriceHistory(productPriceHistoryBack);
        assertThat(additionalAttribute.getPriceHistory()).isEqualTo(productPriceHistoryBack);

        additionalAttribute.priceHistory(null);
        assertThat(additionalAttribute.getPriceHistory()).isNull();
    }
}
