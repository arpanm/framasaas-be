package com.framasaas.domain;

import static com.framasaas.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.domain.WarrantyMasterPriceHistoryTestSamples.*;
import static com.framasaas.domain.WarrantyMasterTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class WarrantyMasterPriceHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WarrantyMasterPriceHistory.class);
        WarrantyMasterPriceHistory warrantyMasterPriceHistory1 = getWarrantyMasterPriceHistorySample1();
        WarrantyMasterPriceHistory warrantyMasterPriceHistory2 = new WarrantyMasterPriceHistory();
        assertThat(warrantyMasterPriceHistory1).isNotEqualTo(warrantyMasterPriceHistory2);

        warrantyMasterPriceHistory2.setId(warrantyMasterPriceHistory1.getId());
        assertThat(warrantyMasterPriceHistory1).isEqualTo(warrantyMasterPriceHistory2);

        warrantyMasterPriceHistory2 = getWarrantyMasterPriceHistorySample2();
        assertThat(warrantyMasterPriceHistory1).isNotEqualTo(warrantyMasterPriceHistory2);
    }

    @Test
    void additionalAttributeTest() {
        WarrantyMasterPriceHistory warrantyMasterPriceHistory = getWarrantyMasterPriceHistoryRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        warrantyMasterPriceHistory.addAdditionalAttribute(additionalAttributeBack);
        assertThat(warrantyMasterPriceHistory.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getWarrantyMasterPriceHistory()).isEqualTo(warrantyMasterPriceHistory);

        warrantyMasterPriceHistory.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(warrantyMasterPriceHistory.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getWarrantyMasterPriceHistory()).isNull();

        warrantyMasterPriceHistory.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(warrantyMasterPriceHistory.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getWarrantyMasterPriceHistory()).isEqualTo(warrantyMasterPriceHistory);

        warrantyMasterPriceHistory.setAdditionalAttributes(new HashSet<>());
        assertThat(warrantyMasterPriceHistory.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getWarrantyMasterPriceHistory()).isNull();
    }

    @Test
    void warrantyMasterTest() {
        WarrantyMasterPriceHistory warrantyMasterPriceHistory = getWarrantyMasterPriceHistoryRandomSampleGenerator();
        WarrantyMaster warrantyMasterBack = getWarrantyMasterRandomSampleGenerator();

        warrantyMasterPriceHistory.setWarrantyMaster(warrantyMasterBack);
        assertThat(warrantyMasterPriceHistory.getWarrantyMaster()).isEqualTo(warrantyMasterBack);

        warrantyMasterPriceHistory.warrantyMaster(null);
        assertThat(warrantyMasterPriceHistory.getWarrantyMaster()).isNull();
    }
}
