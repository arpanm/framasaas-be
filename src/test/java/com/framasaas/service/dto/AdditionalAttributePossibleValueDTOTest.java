package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdditionalAttributePossibleValueDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdditionalAttributePossibleValueDTO.class);
        AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO1 = new AdditionalAttributePossibleValueDTO();
        additionalAttributePossibleValueDTO1.setId(1L);
        AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO2 = new AdditionalAttributePossibleValueDTO();
        assertThat(additionalAttributePossibleValueDTO1).isNotEqualTo(additionalAttributePossibleValueDTO2);
        additionalAttributePossibleValueDTO2.setId(additionalAttributePossibleValueDTO1.getId());
        assertThat(additionalAttributePossibleValueDTO1).isEqualTo(additionalAttributePossibleValueDTO2);
        additionalAttributePossibleValueDTO2.setId(2L);
        assertThat(additionalAttributePossibleValueDTO1).isNotEqualTo(additionalAttributePossibleValueDTO2);
        additionalAttributePossibleValueDTO1.setId(null);
        assertThat(additionalAttributePossibleValueDTO1).isNotEqualTo(additionalAttributePossibleValueDTO2);
    }
}
