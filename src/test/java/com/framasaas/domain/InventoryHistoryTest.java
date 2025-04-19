package com.framasaas.domain;

import static com.framasaas.domain.InventoryHistoryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InventoryHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryHistory.class);
        InventoryHistory inventoryHistory1 = getInventoryHistorySample1();
        InventoryHistory inventoryHistory2 = new InventoryHistory();
        assertThat(inventoryHistory1).isNotEqualTo(inventoryHistory2);

        inventoryHistory2.setId(inventoryHistory1.getId());
        assertThat(inventoryHistory1).isEqualTo(inventoryHistory2);

        inventoryHistory2 = getInventoryHistorySample2();
        assertThat(inventoryHistory1).isNotEqualTo(inventoryHistory2);
    }
}
