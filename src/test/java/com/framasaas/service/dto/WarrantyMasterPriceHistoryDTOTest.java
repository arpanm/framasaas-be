package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WarrantyMasterPriceHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WarrantyMasterPriceHistoryDTO.class);
        WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO1 = new WarrantyMasterPriceHistoryDTO();
        warrantyMasterPriceHistoryDTO1.setId(1L);
        WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO2 = new WarrantyMasterPriceHistoryDTO();
        assertThat(warrantyMasterPriceHistoryDTO1).isNotEqualTo(warrantyMasterPriceHistoryDTO2);
        warrantyMasterPriceHistoryDTO2.setId(warrantyMasterPriceHistoryDTO1.getId());
        assertThat(warrantyMasterPriceHistoryDTO1).isEqualTo(warrantyMasterPriceHistoryDTO2);
        warrantyMasterPriceHistoryDTO2.setId(2L);
        assertThat(warrantyMasterPriceHistoryDTO1).isNotEqualTo(warrantyMasterPriceHistoryDTO2);
        warrantyMasterPriceHistoryDTO1.setId(null);
        assertThat(warrantyMasterPriceHistoryDTO1).isNotEqualTo(warrantyMasterPriceHistoryDTO2);
    }
}
