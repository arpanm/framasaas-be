package com.framasaas.domain;

import static com.framasaas.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.domain.InventoryLocationTestSamples.*;
import static com.framasaas.domain.InventoryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class InventoryLocationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryLocation.class);
        InventoryLocation inventoryLocation1 = getInventoryLocationSample1();
        InventoryLocation inventoryLocation2 = new InventoryLocation();
        assertThat(inventoryLocation1).isNotEqualTo(inventoryLocation2);

        inventoryLocation2.setId(inventoryLocation1.getId());
        assertThat(inventoryLocation1).isEqualTo(inventoryLocation2);

        inventoryLocation2 = getInventoryLocationSample2();
        assertThat(inventoryLocation1).isNotEqualTo(inventoryLocation2);
    }

    @Test
    void inventoryTest() {
        InventoryLocation inventoryLocation = getInventoryLocationRandomSampleGenerator();
        Inventory inventoryBack = getInventoryRandomSampleGenerator();

        inventoryLocation.addInventory(inventoryBack);
        assertThat(inventoryLocation.getInventories()).containsOnly(inventoryBack);
        assertThat(inventoryBack.getLocation()).isEqualTo(inventoryLocation);

        inventoryLocation.removeInventory(inventoryBack);
        assertThat(inventoryLocation.getInventories()).doesNotContain(inventoryBack);
        assertThat(inventoryBack.getLocation()).isNull();

        inventoryLocation.inventories(new HashSet<>(Set.of(inventoryBack)));
        assertThat(inventoryLocation.getInventories()).containsOnly(inventoryBack);
        assertThat(inventoryBack.getLocation()).isEqualTo(inventoryLocation);

        inventoryLocation.setInventories(new HashSet<>());
        assertThat(inventoryLocation.getInventories()).doesNotContain(inventoryBack);
        assertThat(inventoryBack.getLocation()).isNull();
    }

    @Test
    void additionalAttributeTest() {
        InventoryLocation inventoryLocation = getInventoryLocationRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        inventoryLocation.addAdditionalAttribute(additionalAttributeBack);
        assertThat(inventoryLocation.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getInventoryLocation()).isEqualTo(inventoryLocation);

        inventoryLocation.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(inventoryLocation.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getInventoryLocation()).isNull();

        inventoryLocation.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(inventoryLocation.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getInventoryLocation()).isEqualTo(inventoryLocation);

        inventoryLocation.setAdditionalAttributes(new HashSet<>());
        assertThat(inventoryLocation.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getInventoryLocation()).isNull();
    }
}
