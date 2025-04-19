package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LanguageMappingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LanguageMappingDTO.class);
        LanguageMappingDTO languageMappingDTO1 = new LanguageMappingDTO();
        languageMappingDTO1.setId(1L);
        LanguageMappingDTO languageMappingDTO2 = new LanguageMappingDTO();
        assertThat(languageMappingDTO1).isNotEqualTo(languageMappingDTO2);
        languageMappingDTO2.setId(languageMappingDTO1.getId());
        assertThat(languageMappingDTO1).isEqualTo(languageMappingDTO2);
        languageMappingDTO2.setId(2L);
        assertThat(languageMappingDTO1).isNotEqualTo(languageMappingDTO2);
        languageMappingDTO1.setId(null);
        assertThat(languageMappingDTO1).isNotEqualTo(languageMappingDTO2);
    }
}
