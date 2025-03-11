package com.framasaas.be.domain;

import static com.framasaas.be.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.be.domain.FranchiseTestSamples.*;
import static com.framasaas.be.domain.ServiceOrderFranchiseAssignmentTestSamples.*;
import static com.framasaas.be.domain.ServiceOrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ServiceOrderFranchiseAssignmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOrderFranchiseAssignment.class);
        ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment1 = getServiceOrderFranchiseAssignmentSample1();
        ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment2 = new ServiceOrderFranchiseAssignment();
        assertThat(serviceOrderFranchiseAssignment1).isNotEqualTo(serviceOrderFranchiseAssignment2);

        serviceOrderFranchiseAssignment2.setId(serviceOrderFranchiseAssignment1.getId());
        assertThat(serviceOrderFranchiseAssignment1).isEqualTo(serviceOrderFranchiseAssignment2);

        serviceOrderFranchiseAssignment2 = getServiceOrderFranchiseAssignmentSample2();
        assertThat(serviceOrderFranchiseAssignment1).isNotEqualTo(serviceOrderFranchiseAssignment2);
    }

    @Test
    void additionalAttributeTest() {
        ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment = getServiceOrderFranchiseAssignmentRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        serviceOrderFranchiseAssignment.addAdditionalAttribute(additionalAttributeBack);
        assertThat(serviceOrderFranchiseAssignment.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrderFranchiseAssignment()).isEqualTo(serviceOrderFranchiseAssignment);

        serviceOrderFranchiseAssignment.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(serviceOrderFranchiseAssignment.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrderFranchiseAssignment()).isNull();

        serviceOrderFranchiseAssignment.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(serviceOrderFranchiseAssignment.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrderFranchiseAssignment()).isEqualTo(serviceOrderFranchiseAssignment);

        serviceOrderFranchiseAssignment.setAdditionalAttributes(new HashSet<>());
        assertThat(serviceOrderFranchiseAssignment.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrderFranchiseAssignment()).isNull();
    }

    @Test
    void serviceOrderTest() {
        ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment = getServiceOrderFranchiseAssignmentRandomSampleGenerator();
        ServiceOrder serviceOrderBack = getServiceOrderRandomSampleGenerator();

        serviceOrderFranchiseAssignment.setServiceOrder(serviceOrderBack);
        assertThat(serviceOrderFranchiseAssignment.getServiceOrder()).isEqualTo(serviceOrderBack);

        serviceOrderFranchiseAssignment.serviceOrder(null);
        assertThat(serviceOrderFranchiseAssignment.getServiceOrder()).isNull();
    }

    @Test
    void franchiseTest() {
        ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment = getServiceOrderFranchiseAssignmentRandomSampleGenerator();
        Franchise franchiseBack = getFranchiseRandomSampleGenerator();

        serviceOrderFranchiseAssignment.setFranchise(franchiseBack);
        assertThat(serviceOrderFranchiseAssignment.getFranchise()).isEqualTo(franchiseBack);

        serviceOrderFranchiseAssignment.franchise(null);
        assertThat(serviceOrderFranchiseAssignment.getFranchise()).isNull();
    }
}
