package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceOrderSpareDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOrderSpareDTO.class);
        ServiceOrderSpareDTO serviceOrderSpareDTO1 = new ServiceOrderSpareDTO();
        serviceOrderSpareDTO1.setId(1L);
        ServiceOrderSpareDTO serviceOrderSpareDTO2 = new ServiceOrderSpareDTO();
        assertThat(serviceOrderSpareDTO1).isNotEqualTo(serviceOrderSpareDTO2);
        serviceOrderSpareDTO2.setId(serviceOrderSpareDTO1.getId());
        assertThat(serviceOrderSpareDTO1).isEqualTo(serviceOrderSpareDTO2);
        serviceOrderSpareDTO2.setId(2L);
        assertThat(serviceOrderSpareDTO1).isNotEqualTo(serviceOrderSpareDTO2);
        serviceOrderSpareDTO1.setId(null);
        assertThat(serviceOrderSpareDTO1).isNotEqualTo(serviceOrderSpareDTO2);
    }
}
