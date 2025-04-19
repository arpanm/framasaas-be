package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceOrderFranchiseAssignmentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOrderFranchiseAssignmentDTO.class);
        ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO1 = new ServiceOrderFranchiseAssignmentDTO();
        serviceOrderFranchiseAssignmentDTO1.setId(1L);
        ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO2 = new ServiceOrderFranchiseAssignmentDTO();
        assertThat(serviceOrderFranchiseAssignmentDTO1).isNotEqualTo(serviceOrderFranchiseAssignmentDTO2);
        serviceOrderFranchiseAssignmentDTO2.setId(serviceOrderFranchiseAssignmentDTO1.getId());
        assertThat(serviceOrderFranchiseAssignmentDTO1).isEqualTo(serviceOrderFranchiseAssignmentDTO2);
        serviceOrderFranchiseAssignmentDTO2.setId(2L);
        assertThat(serviceOrderFranchiseAssignmentDTO1).isNotEqualTo(serviceOrderFranchiseAssignmentDTO2);
        serviceOrderFranchiseAssignmentDTO1.setId(null);
        assertThat(serviceOrderFranchiseAssignmentDTO1).isNotEqualTo(serviceOrderFranchiseAssignmentDTO2);
    }
}
