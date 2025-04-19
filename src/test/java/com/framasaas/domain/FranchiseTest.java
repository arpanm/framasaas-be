package com.framasaas.domain;

import static com.framasaas.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.domain.AddressTestSamples.*;
import static com.framasaas.domain.FranchiseAllocationRuleSetTestSamples.*;
import static com.framasaas.domain.FranchiseBrandMappingTestSamples.*;
import static com.framasaas.domain.FranchiseCategoryMappingTestSamples.*;
import static com.framasaas.domain.FranchisePerformanceHistoryTestSamples.*;
import static com.framasaas.domain.FranchiseStatusHistoryTestSamples.*;
import static com.framasaas.domain.FranchiseTestSamples.*;
import static com.framasaas.domain.FranchiseUserTestSamples.*;
import static com.framasaas.domain.ServiceOrderFranchiseAssignmentTestSamples.*;
import static com.framasaas.domain.SupportingDocumentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
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
    void supportingDocumentTest() {
        Franchise franchise = getFranchiseRandomSampleGenerator();
        SupportingDocument supportingDocumentBack = getSupportingDocumentRandomSampleGenerator();

        franchise.addSupportingDocument(supportingDocumentBack);
        assertThat(franchise.getSupportingDocuments()).containsOnly(supportingDocumentBack);
        assertThat(supportingDocumentBack.getFranchise()).isEqualTo(franchise);

        franchise.removeSupportingDocument(supportingDocumentBack);
        assertThat(franchise.getSupportingDocuments()).doesNotContain(supportingDocumentBack);
        assertThat(supportingDocumentBack.getFranchise()).isNull();

        franchise.supportingDocuments(new HashSet<>(Set.of(supportingDocumentBack)));
        assertThat(franchise.getSupportingDocuments()).containsOnly(supportingDocumentBack);
        assertThat(supportingDocumentBack.getFranchise()).isEqualTo(franchise);

        franchise.setSupportingDocuments(new HashSet<>());
        assertThat(franchise.getSupportingDocuments()).doesNotContain(supportingDocumentBack);
        assertThat(supportingDocumentBack.getFranchise()).isNull();
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
    void serviceOrderFranchiseAssignmentTest() {
        Franchise franchise = getFranchiseRandomSampleGenerator();
        ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignmentBack = getServiceOrderFranchiseAssignmentRandomSampleGenerator();

        franchise.addServiceOrderFranchiseAssignment(serviceOrderFranchiseAssignmentBack);
        assertThat(franchise.getServiceOrderFranchiseAssignments()).containsOnly(serviceOrderFranchiseAssignmentBack);
        assertThat(serviceOrderFranchiseAssignmentBack.getFranchise()).isEqualTo(franchise);

        franchise.removeServiceOrderFranchiseAssignment(serviceOrderFranchiseAssignmentBack);
        assertThat(franchise.getServiceOrderFranchiseAssignments()).doesNotContain(serviceOrderFranchiseAssignmentBack);
        assertThat(serviceOrderFranchiseAssignmentBack.getFranchise()).isNull();

        franchise.serviceOrderFranchiseAssignments(new HashSet<>(Set.of(serviceOrderFranchiseAssignmentBack)));
        assertThat(franchise.getServiceOrderFranchiseAssignments()).containsOnly(serviceOrderFranchiseAssignmentBack);
        assertThat(serviceOrderFranchiseAssignmentBack.getFranchise()).isEqualTo(franchise);

        franchise.setServiceOrderFranchiseAssignments(new HashSet<>());
        assertThat(franchise.getServiceOrderFranchiseAssignments()).doesNotContain(serviceOrderFranchiseAssignmentBack);
        assertThat(serviceOrderFranchiseAssignmentBack.getFranchise()).isNull();
    }

    @Test
    void additionalAttributeTest() {
        Franchise franchise = getFranchiseRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        franchise.addAdditionalAttribute(additionalAttributeBack);
        assertThat(franchise.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFranchise()).isEqualTo(franchise);

        franchise.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(franchise.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFranchise()).isNull();

        franchise.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(franchise.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFranchise()).isEqualTo(franchise);

        franchise.setAdditionalAttributes(new HashSet<>());
        assertThat(franchise.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFranchise()).isNull();
    }

    @Test
    void rulesetTest() {
        Franchise franchise = getFranchiseRandomSampleGenerator();
        FranchiseAllocationRuleSet franchiseAllocationRuleSetBack = getFranchiseAllocationRuleSetRandomSampleGenerator();

        franchise.setRuleset(franchiseAllocationRuleSetBack);
        assertThat(franchise.getRuleset()).isEqualTo(franchiseAllocationRuleSetBack);

        franchise.ruleset(null);
        assertThat(franchise.getRuleset()).isNull();
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
