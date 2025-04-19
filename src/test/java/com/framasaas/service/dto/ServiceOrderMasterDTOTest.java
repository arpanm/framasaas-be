package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceOrderMasterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOrderMasterDTO.class);
        ServiceOrderMasterDTO serviceOrderMasterDTO1 = new ServiceOrderMasterDTO();
        serviceOrderMasterDTO1.setId(1L);
        ServiceOrderMasterDTO serviceOrderMasterDTO2 = new ServiceOrderMasterDTO();
        assertThat(serviceOrderMasterDTO1).isNotEqualTo(serviceOrderMasterDTO2);
        serviceOrderMasterDTO2.setId(serviceOrderMasterDTO1.getId());
        assertThat(serviceOrderMasterDTO1).isEqualTo(serviceOrderMasterDTO2);
        serviceOrderMasterDTO2.setId(2L);
        assertThat(serviceOrderMasterDTO1).isNotEqualTo(serviceOrderMasterDTO2);
        serviceOrderMasterDTO1.setId(null);
        assertThat(serviceOrderMasterDTO1).isNotEqualTo(serviceOrderMasterDTO2);
    }
}
