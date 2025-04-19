package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InventoryHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryHistoryDTO.class);
        InventoryHistoryDTO inventoryHistoryDTO1 = new InventoryHistoryDTO();
        inventoryHistoryDTO1.setId(1L);
        InventoryHistoryDTO inventoryHistoryDTO2 = new InventoryHistoryDTO();
        assertThat(inventoryHistoryDTO1).isNotEqualTo(inventoryHistoryDTO2);
        inventoryHistoryDTO2.setId(inventoryHistoryDTO1.getId());
        assertThat(inventoryHistoryDTO1).isEqualTo(inventoryHistoryDTO2);
        inventoryHistoryDTO2.setId(2L);
        assertThat(inventoryHistoryDTO1).isNotEqualTo(inventoryHistoryDTO2);
        inventoryHistoryDTO1.setId(null);
        assertThat(inventoryHistoryDTO1).isNotEqualTo(inventoryHistoryDTO2);
    }
}
