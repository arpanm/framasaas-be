package com.framasaas.be.domain;

import static com.framasaas.be.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.be.domain.InventoryLocationTestSamples.*;
import static com.framasaas.be.domain.InventoryTestSamples.*;
import static com.framasaas.be.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class InventoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inventory.class);
        Inventory inventory1 = getInventorySample1();
        Inventory inventory2 = new Inventory();
        assertThat(inventory1).isNotEqualTo(inventory2);

        inventory2.setId(inventory1.getId());
        assertThat(inventory1).isEqualTo(inventory2);

        inventory2 = getInventorySample2();
        assertThat(inventory1).isNotEqualTo(inventory2);
    }

    @Test
    void additionalAttributeTest() {
        Inventory inventory = getInventoryRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        inventory.addAdditionalAttribute(additionalAttributeBack);
        assertThat(inventory.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getInventory()).isEqualTo(inventory);

        inventory.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(inventory.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getInventory()).isNull();

        inventory.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(inventory.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getInventory()).isEqualTo(inventory);

        inventory.setAdditionalAttributes(new HashSet<>());
        assertThat(inventory.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getInventory()).isNull();
    }

    @Test
    void productTest() {
        Inventory inventory = getInventoryRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        inventory.setProduct(productBack);
        assertThat(inventory.getProduct()).isEqualTo(productBack);

        inventory.product(null);
        assertThat(inventory.getProduct()).isNull();
    }

    @Test
    void locationTest() {
        Inventory inventory = getInventoryRandomSampleGenerator();
        InventoryLocation inventoryLocationBack = getInventoryLocationRandomSampleGenerator();

        inventory.setLocation(inventoryLocationBack);
        assertThat(inventory.getLocation()).isEqualTo(inventoryLocationBack);

        inventory.location(null);
        assertThat(inventory.getLocation()).isNull();
    }
}
