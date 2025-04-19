package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdditionalAttributeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdditionalAttributeDTO.class);
        AdditionalAttributeDTO additionalAttributeDTO1 = new AdditionalAttributeDTO();
        additionalAttributeDTO1.setId(1L);
        AdditionalAttributeDTO additionalAttributeDTO2 = new AdditionalAttributeDTO();
        assertThat(additionalAttributeDTO1).isNotEqualTo(additionalAttributeDTO2);
        additionalAttributeDTO2.setId(additionalAttributeDTO1.getId());
        assertThat(additionalAttributeDTO1).isEqualTo(additionalAttributeDTO2);
        additionalAttributeDTO2.setId(2L);
        assertThat(additionalAttributeDTO1).isNotEqualTo(additionalAttributeDTO2);
        additionalAttributeDTO1.setId(null);
        assertThat(additionalAttributeDTO1).isNotEqualTo(additionalAttributeDTO2);
    }
}
