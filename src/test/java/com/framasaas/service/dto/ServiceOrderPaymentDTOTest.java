package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceOrderPaymentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOrderPaymentDTO.class);
        ServiceOrderPaymentDTO serviceOrderPaymentDTO1 = new ServiceOrderPaymentDTO();
        serviceOrderPaymentDTO1.setId(1L);
        ServiceOrderPaymentDTO serviceOrderPaymentDTO2 = new ServiceOrderPaymentDTO();
        assertThat(serviceOrderPaymentDTO1).isNotEqualTo(serviceOrderPaymentDTO2);
        serviceOrderPaymentDTO2.setId(serviceOrderPaymentDTO1.getId());
        assertThat(serviceOrderPaymentDTO1).isEqualTo(serviceOrderPaymentDTO2);
        serviceOrderPaymentDTO2.setId(2L);
        assertThat(serviceOrderPaymentDTO1).isNotEqualTo(serviceOrderPaymentDTO2);
        serviceOrderPaymentDTO1.setId(null);
        assertThat(serviceOrderPaymentDTO1).isNotEqualTo(serviceOrderPaymentDTO2);
    }
}
