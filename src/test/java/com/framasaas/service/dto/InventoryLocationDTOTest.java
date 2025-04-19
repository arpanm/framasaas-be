package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InventoryLocationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryLocationDTO.class);
        InventoryLocationDTO inventoryLocationDTO1 = new InventoryLocationDTO();
        inventoryLocationDTO1.setId(1L);
        InventoryLocationDTO inventoryLocationDTO2 = new InventoryLocationDTO();
        assertThat(inventoryLocationDTO1).isNotEqualTo(inventoryLocationDTO2);
        inventoryLocationDTO2.setId(inventoryLocationDTO1.getId());
        assertThat(inventoryLocationDTO1).isEqualTo(inventoryLocationDTO2);
        inventoryLocationDTO2.setId(2L);
        assertThat(inventoryLocationDTO1).isNotEqualTo(inventoryLocationDTO2);
        inventoryLocationDTO1.setId(null);
        assertThat(inventoryLocationDTO1).isNotEqualTo(inventoryLocationDTO2);
    }
}
