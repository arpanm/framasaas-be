package com.framasaas.be.domain;

import static com.framasaas.be.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.be.domain.ServiceOrderFieldAgentAssignmentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ServiceOrderFieldAgentAssignmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOrderFieldAgentAssignment.class);
        ServiceOrderFieldAgentAssignment serviceOrderFieldAgentAssignment1 = getServiceOrderFieldAgentAssignmentSample1();
        ServiceOrderFieldAgentAssignment serviceOrderFieldAgentAssignment2 = new ServiceOrderFieldAgentAssignment();
        assertThat(serviceOrderFieldAgentAssignment1).isNotEqualTo(serviceOrderFieldAgentAssignment2);

        serviceOrderFieldAgentAssignment2.setId(serviceOrderFieldAgentAssignment1.getId());
        assertThat(serviceOrderFieldAgentAssignment1).isEqualTo(serviceOrderFieldAgentAssignment2);

        serviceOrderFieldAgentAssignment2 = getServiceOrderFieldAgentAssignmentSample2();
        assertThat(serviceOrderFieldAgentAssignment1).isNotEqualTo(serviceOrderFieldAgentAssignment2);
    }

    @Test
    void additionalAttributeTest() {
        ServiceOrderFieldAgentAssignment serviceOrderFieldAgentAssignment = getServiceOrderFieldAgentAssignmentRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        serviceOrderFieldAgentAssignment.addAdditionalAttribute(additionalAttributeBack);
        assertThat(serviceOrderFieldAgentAssignment.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrderFieldAgentAssignment()).isEqualTo(serviceOrderFieldAgentAssignment);

        serviceOrderFieldAgentAssignment.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(serviceOrderFieldAgentAssignment.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrderFieldAgentAssignment()).isNull();

        serviceOrderFieldAgentAssignment.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(serviceOrderFieldAgentAssignment.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrderFieldAgentAssignment()).isEqualTo(serviceOrderFieldAgentAssignment);

        serviceOrderFieldAgentAssignment.setAdditionalAttributes(new HashSet<>());
        assertThat(serviceOrderFieldAgentAssignment.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrderFieldAgentAssignment()).isNull();
    }
}
