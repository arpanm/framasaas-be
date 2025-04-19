package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceOrderFieldAgentAssignmentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOrderFieldAgentAssignmentDTO.class);
        ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO1 = new ServiceOrderFieldAgentAssignmentDTO();
        serviceOrderFieldAgentAssignmentDTO1.setId(1L);
        ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO2 = new ServiceOrderFieldAgentAssignmentDTO();
        assertThat(serviceOrderFieldAgentAssignmentDTO1).isNotEqualTo(serviceOrderFieldAgentAssignmentDTO2);
        serviceOrderFieldAgentAssignmentDTO2.setId(serviceOrderFieldAgentAssignmentDTO1.getId());
        assertThat(serviceOrderFieldAgentAssignmentDTO1).isEqualTo(serviceOrderFieldAgentAssignmentDTO2);
        serviceOrderFieldAgentAssignmentDTO2.setId(2L);
        assertThat(serviceOrderFieldAgentAssignmentDTO1).isNotEqualTo(serviceOrderFieldAgentAssignmentDTO2);
        serviceOrderFieldAgentAssignmentDTO1.setId(null);
        assertThat(serviceOrderFieldAgentAssignmentDTO1).isNotEqualTo(serviceOrderFieldAgentAssignmentDTO2);
    }
}
