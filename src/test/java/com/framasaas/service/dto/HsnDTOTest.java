package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HsnDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HsnDTO.class);
        HsnDTO hsnDTO1 = new HsnDTO();
        hsnDTO1.setId(1L);
        HsnDTO hsnDTO2 = new HsnDTO();
        assertThat(hsnDTO1).isNotEqualTo(hsnDTO2);
        hsnDTO2.setId(hsnDTO1.getId());
        assertThat(hsnDTO1).isEqualTo(hsnDTO2);
        hsnDTO2.setId(2L);
        assertThat(hsnDTO1).isNotEqualTo(hsnDTO2);
        hsnDTO1.setId(null);
        assertThat(hsnDTO1).isNotEqualTo(hsnDTO2);
    }
}
