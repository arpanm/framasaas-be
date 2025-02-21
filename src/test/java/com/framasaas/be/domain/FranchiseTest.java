package com.framasaas.be.domain;

import static com.framasaas.be.domain.AddressTestSamples.*;
import static com.framasaas.be.domain.FranchiseBrandMappingTestSamples.*;
import static com.framasaas.be.domain.FranchiseCategoryMappingTestSamples.*;
import static com.framasaas.be.domain.FranchiseDocumentTestSamples.*;
import static com.framasaas.be.domain.FranchiseStatusHistoryTestSamples.*;
import static com.framasaas.be.domain.FranchiseTestSamples.*;
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

    @Test
    void documentsTest() {
        Franchise franchise = getFranchiseRandomSampleGenerator();
        FranchiseDocument franchiseDocumentBack = getFranchiseDocumentRandomSampleGenerator();

        franchise.addDocuments(franchiseDocumentBack);
        assertThat(franchise.getDocuments()).containsOnly(franchiseDocumentBack);
        assertThat(franchiseDocumentBack.getFranchise()).isEqualTo(franchise);

        franchise.removeDocuments(franchiseDocumentBack);
        assertThat(franchise.getDocuments()).doesNotContain(franchiseDocumentBack);
        assertThat(franchiseDocumentBack.getFranchise()).isNull();

        franchise.documents(new HashSet<>(Set.of(franchiseDocumentBack)));
        assertThat(franchise.getDocuments()).containsOnly(franchiseDocumentBack);
        assertThat(franchiseDocumentBack.getFranchise()).isEqualTo(franchise);

        franchise.setDocuments(new HashSet<>());
        assertThat(franchise.getDocuments()).doesNotContain(franchiseDocumentBack);
        assertThat(franchiseDocumentBack.getFranchise()).isNull();
    }
}
