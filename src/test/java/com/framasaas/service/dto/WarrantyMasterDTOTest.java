package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WarrantyMasterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WarrantyMasterDTO.class);
        WarrantyMasterDTO warrantyMasterDTO1 = new WarrantyMasterDTO();
        warrantyMasterDTO1.setId(1L);
        WarrantyMasterDTO warrantyMasterDTO2 = new WarrantyMasterDTO();
        assertThat(warrantyMasterDTO1).isNotEqualTo(warrantyMasterDTO2);
        warrantyMasterDTO2.setId(warrantyMasterDTO1.getId());
        assertThat(warrantyMasterDTO1).isEqualTo(warrantyMasterDTO2);
        warrantyMasterDTO2.setId(2L);
        assertThat(warrantyMasterDTO1).isNotEqualTo(warrantyMasterDTO2);
        warrantyMasterDTO1.setId(null);
        assertThat(warrantyMasterDTO1).isNotEqualTo(warrantyMasterDTO2);
    }
}
