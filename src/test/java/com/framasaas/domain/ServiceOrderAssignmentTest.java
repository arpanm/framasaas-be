package com.framasaas.domain;

import static com.framasaas.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.domain.FranchiseTestSamples.*;
import static com.framasaas.domain.ServiceOrderAssignmentTestSamples.*;
import static com.framasaas.domain.ServiceOrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ServiceOrderAssignmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOrderAssignment.class);
        ServiceOrderAssignment serviceOrderAssignment1 = getServiceOrderAssignmentSample1();
        ServiceOrderAssignment serviceOrderAssignment2 = new ServiceOrderAssignment();
        assertThat(serviceOrderAssignment1).isNotEqualTo(serviceOrderAssignment2);

        serviceOrderAssignment2.setId(serviceOrderAssignment1.getId());
        assertThat(serviceOrderAssignment1).isEqualTo(serviceOrderAssignment2);

        serviceOrderAssignment2 = getServiceOrderAssignmentSample2();
        assertThat(serviceOrderAssignment1).isNotEqualTo(serviceOrderAssignment2);
    }

    @Test
    void additionalAttributeTest() {
        ServiceOrderAssignment serviceOrderAssignment = getServiceOrderAssignmentRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        serviceOrderAssignment.addAdditionalAttribute(additionalAttributeBack);
        assertThat(serviceOrderAssignment.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrderAssignment()).isEqualTo(serviceOrderAssignment);

        serviceOrderAssignment.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(serviceOrderAssignment.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrderAssignment()).isNull();

        serviceOrderAssignment.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(serviceOrderAssignment.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrderAssignment()).isEqualTo(serviceOrderAssignment);

        serviceOrderAssignment.setAdditionalAttributes(new HashSet<>());
        assertThat(serviceOrderAssignment.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrderAssignment()).isNull();
    }

    @Test
    void serviceOrderTest() {
        ServiceOrderAssignment serviceOrderAssignment = getServiceOrderAssignmentRandomSampleGenerator();
        ServiceOrder serviceOrderBack = getServiceOrderRandomSampleGenerator();

        serviceOrderAssignment.setServiceOrder(serviceOrderBack);
        assertThat(serviceOrderAssignment.getServiceOrder()).isEqualTo(serviceOrderBack);

        serviceOrderAssignment.serviceOrder(null);
        assertThat(serviceOrderAssignment.getServiceOrder()).isNull();
    }

    @Test
    void franchiseTest() {
        ServiceOrderAssignment serviceOrderAssignment = getServiceOrderAssignmentRandomSampleGenerator();
        Franchise franchiseBack = getFranchiseRandomSampleGenerator();

        serviceOrderAssignment.setFranchise(franchiseBack);
        assertThat(serviceOrderAssignment.getFranchise()).isEqualTo(franchiseBack);

        serviceOrderAssignment.franchise(null);
        assertThat(serviceOrderAssignment.getFranchise()).isNull();
    }
}
