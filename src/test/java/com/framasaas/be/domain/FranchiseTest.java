package com.framasaas.be.domain;

import static com.framasaas.be.domain.AddressTestSamples.*;
import static com.framasaas.be.domain.FranchiseBrandMappingTestSamples.*;
import static com.framasaas.be.domain.FranchiseCategoryMappingTestSamples.*;
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

class FranchiseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Franchise.class);
        Franchise franchise1 = getFranchiseSample1();
        Franchise franchise2 = new Franchise();
        assertThat(franchise1).isNotEqualTo(franchise2);

        franchise2.setId(franchise1.getId());
        assertThat(franchise1).isEqualTo(franchise2);

        franchise2 = getFranchiseSample2();
        assertThat(franchise1).isNotEqualTo(franchise2);
    }

    @Test
    void addressTest() {
        Franchise franchise = getFranchiseRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        franchise.setAddress(addressBack);
        assertThat(franchise.getAddress()).isEqualTo(addressBack);

        franchise.address(null);
        assertThat(franchise.getAddress()).isNull();
    }

    @Test
    void franchiseStatusHistoryTest() {
        Franchise franchise = getFranchiseRandomSampleGenerator();
        FranchiseStatusHistory franchiseStatusHistoryBack = getFranchiseStatusHistoryRandomSampleGenerator();

        franchise.addFranchiseStatusHistory(franchiseStatusHistoryBack);
        assertThat(franchise.getFranchiseStatusHistories()).containsOnly(franchiseStatusHistoryBack);
        assertThat(franchiseStatusHistoryBack.getFranchise()).isEqualTo(franchise);

        franchise.removeFranchiseStatusHistory(franchiseStatusHistoryBack);
        assertThat(franchise.getFranchiseStatusHistories()).doesNotContain(franchiseStatusHistoryBack);
        assertThat(franchiseStatusHistoryBack.getFranchise()).isNull();

        franchise.franchiseStatusHistories(new HashSet<>(Set.of(franchiseStatusHistoryBack)));
        assertThat(franchise.getFranchiseStatusHistories()).containsOnly(franchiseStatusHistoryBack);
        assertThat(franchiseStatusHistoryBack.getFranchise()).isEqualTo(franchise);

        franchise.setFranchiseStatusHistories(new HashSet<>());
        assertThat(franchise.getFranchiseStatusHistories()).doesNotContain(franchiseStatusHistoryBack);
        assertThat(franchiseStatusHistoryBack.getFranchise()).isNull();
    }

    @Test
    void franchisePerformanceHistoryTest() {
        Franchise franchise = getFranchiseRandomSampleGenerator();
        FranchisePerformanceHistory franchisePerformanceHistoryBack = getFranchisePerformanceHistoryRandomSampleGenerator();

        franchise.addFranchisePerformanceHistory(franchisePerformanceHistoryBack);
        assertThat(franchise.getFranchisePerformanceHistories()).containsOnly(franchisePerformanceHistoryBack);
        assertThat(franchisePerformanceHistoryBack.getFranchise()).isEqualTo(franchise);

        franchise.removeFranchisePerformanceHistory(franchisePerformanceHistoryBack);
        assertThat(franchise.getFranchisePerformanceHistories()).doesNotContain(franchisePerformanceHistoryBack);
        assertThat(franchisePerformanceHistoryBack.getFranchise()).isNull();

        franchise.franchisePerformanceHistories(new HashSet<>(Set.of(franchisePerformanceHistoryBack)));
        assertThat(franchise.getFranchisePerformanceHistories()).containsOnly(franchisePerformanceHistoryBack);
        assertThat(franchisePerformanceHistoryBack.getFranchise()).isEqualTo(franchise);

        franchise.setFranchisePerformanceHistories(new HashSet<>());
        assertThat(franchise.getFranchisePerformanceHistories()).doesNotContain(franchisePerformanceHistoryBack);
        assertThat(franchisePerformanceHistoryBack.getFranchise()).isNull();
    }

    @Test
    void locationMappingTest() {
        Franchise franchise = getFranchiseRandomSampleGenerator();
        LocationMapping locationMappingBack = getLocationMappingRandomSampleGenerator();

        franchise.addLocationMapping(locationMappingBack);
        assertThat(franchise.getLocationMappings()).containsOnly(locationMappingBack);
        assertThat(locationMappingBack.getFranchise()).isEqualTo(franchise);

        franchise.removeLocationMapping(locationMappingBack);
        assertThat(franchise.getLocationMappings()).doesNotContain(locationMappingBack);
        assertThat(locationMappingBack.getFranchise()).isNull();

        franchise.locationMappings(new HashSet<>(Set.of(locationMappingBack)));
        assertThat(franchise.getLocationMappings()).containsOnly(locationMappingBack);
        assertThat(locationMappingBack.getFranchise()).isEqualTo(franchise);

        franchise.setLocationMappings(new HashSet<>());
        assertThat(franchise.getLocationMappings()).doesNotContain(locationMappingBack);
        assertThat(locationMappingBack.getFranchise()).isNull();
    }

    @Test
    void franchiseDocumentTest() {
        Franchise franchise = getFranchiseRandomSampleGenerator();
        FranchiseDocument franchiseDocumentBack = getFranchiseDocumentRandomSampleGenerator();

        franchise.addFranchiseDocument(franchiseDocumentBack);
        assertThat(franchise.getFranchiseDocuments()).containsOnly(franchiseDocumentBack);
        assertThat(franchiseDocumentBack.getFranchise()).isEqualTo(franchise);

        franchise.removeFranchiseDocument(franchiseDocumentBack);
        assertThat(franchise.getFranchiseDocuments()).doesNotContain(franchiseDocumentBack);
        assertThat(franchiseDocumentBack.getFranchise()).isNull();

        franchise.franchiseDocuments(new HashSet<>(Set.of(franchiseDocumentBack)));
        assertThat(franchise.getFranchiseDocuments()).containsOnly(franchiseDocumentBack);
        assertThat(franchiseDocumentBack.getFranchise()).isEqualTo(franchise);

        franchise.setFranchiseDocuments(new HashSet<>());
        assertThat(franchise.getFranchiseDocuments()).doesNotContain(franchiseDocumentBack);
        assertThat(franchiseDocumentBack.getFranchise()).isNull();
    }

    @Test
    void franchiseUserTest() {
        Franchise franchise = getFranchiseRandomSampleGenerator();
        FranchiseUser franchiseUserBack = getFranchiseUserRandomSampleGenerator();

        franchise.addFranchiseUser(franchiseUserBack);
        assertThat(franchise.getFranchiseUsers()).containsOnly(franchiseUserBack);
        assertThat(franchiseUserBack.getFranchise()).isEqualTo(franchise);

        franchise.removeFranchiseUser(franchiseUserBack);
        assertThat(franchise.getFranchiseUsers()).doesNotContain(franchiseUserBack);
        assertThat(franchiseUserBack.getFranchise()).isNull();

        franchise.franchiseUsers(new HashSet<>(Set.of(franchiseUserBack)));
        assertThat(franchise.getFranchiseUsers()).containsOnly(franchiseUserBack);
        assertThat(franchiseUserBack.getFranchise()).isEqualTo(franchise);

        franchise.setFranchiseUsers(new HashSet<>());
        assertThat(franchise.getFranchiseUsers()).doesNotContain(franchiseUserBack);
        assertThat(franchiseUserBack.getFranchise()).isNull();
    }

    @Test
    void brandsTest() {
        Franchise franchise = getFranchiseRandomSampleGenerator();
        FranchiseBrandMapping franchiseBrandMappingBack = getFranchiseBrandMappingRandomSampleGenerator();

        franchise.addBrands(franchiseBrandMappingBack);
        assertThat(franchise.getBrands()).containsOnly(franchiseBrandMappingBack);
        assertThat(franchiseBrandMappingBack.getFranchise()).isEqualTo(franchise);

        franchise.removeBrands(franchiseBrandMappingBack);
        assertThat(franchise.getBrands()).doesNotContain(franchiseBrandMappingBack);
        assertThat(franchiseBrandMappingBack.getFranchise()).isNull();

        franchise.brands(new HashSet<>(Set.of(franchiseBrandMappingBack)));
        assertThat(franchise.getBrands()).containsOnly(franchiseBrandMappingBack);
        assertThat(franchiseBrandMappingBack.getFranchise()).isEqualTo(franchise);

        franchise.setBrands(new HashSet<>());
        assertThat(franchise.getBrands()).doesNotContain(franchiseBrandMappingBack);
        assertThat(franchiseBrandMappingBack.getFranchise()).isNull();
    }

    @Test
    void categoriesTest() {
        Franchise franchise = getFranchiseRandomSampleGenerator();
        FranchiseCategoryMapping franchiseCategoryMappingBack = getFranchiseCategoryMappingRandomSampleGenerator();

        franchise.addCategories(franchiseCategoryMappingBack);
        assertThat(franchise.getCategories()).containsOnly(franchiseCategoryMappingBack);
        assertThat(franchiseCategoryMappingBack.getFranchise()).isEqualTo(franchise);

        franchise.removeCategories(franchiseCategoryMappingBack);
        assertThat(franchise.getCategories()).doesNotContain(franchiseCategoryMappingBack);
        assertThat(franchiseCategoryMappingBack.getFranchise()).isNull();

        franchise.categories(new HashSet<>(Set.of(franchiseCategoryMappingBack)));
        assertThat(franchise.getCategories()).containsOnly(franchiseCategoryMappingBack);
        assertThat(franchiseCategoryMappingBack.getFranchise()).isEqualTo(franchise);

        franchise.setCategories(new HashSet<>());
        assertThat(franchise.getCategories()).doesNotContain(franchiseCategoryMappingBack);
        assertThat(franchiseCategoryMappingBack.getFranchise()).isNull();
    }
}
