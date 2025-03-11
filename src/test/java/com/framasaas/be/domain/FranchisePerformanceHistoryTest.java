package com.framasaas.be.domain;

import static com.framasaas.be.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.be.domain.FranchisePerformanceHistoryTestSamples.*;
import static com.framasaas.be.domain.FranchiseTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FranchisePerformanceHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FranchisePerformanceHistory.class);
        FranchisePerformanceHistory franchisePerformanceHistory1 = getFranchisePerformanceHistorySample1();
        FranchisePerformanceHistory franchisePerformanceHistory2 = new FranchisePerformanceHistory();
        assertThat(franchisePerformanceHistory1).isNotEqualTo(franchisePerformanceHistory2);

        franchisePerformanceHistory2.setId(franchisePerformanceHistory1.getId());
        assertThat(franchisePerformanceHistory1).isEqualTo(franchisePerformanceHistory2);

        franchisePerformanceHistory2 = getFranchisePerformanceHistorySample2();
        assertThat(franchisePerformanceHistory1).isNotEqualTo(franchisePerformanceHistory2);
    }

    @Test
    void additionalAttributeTest() {
        FranchisePerformanceHistory franchisePerformanceHistory = getFranchisePerformanceHistoryRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        franchisePerformanceHistory.addAdditionalAttribute(additionalAttributeBack);
        assertThat(franchisePerformanceHistory.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFranchisePerformance()).isEqualTo(franchisePerformanceHistory);

        franchisePerformanceHistory.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(franchisePerformanceHistory.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFranchisePerformance()).isNull();

        franchisePerformanceHistory.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(franchisePerformanceHistory.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFranchisePerformance()).isEqualTo(franchisePerformanceHistory);

        franchisePerformanceHistory.setAdditionalAttributes(new HashSet<>());
        assertThat(franchisePerformanceHistory.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFranchisePerformance()).isNull();
    }

    @Test
    void franchiseTest() {
        FranchisePerformanceHistory franchisePerformanceHistory = getFranchisePerformanceHistoryRandomSampleGenerator();
        Franchise franchiseBack = getFranchiseRandomSampleGenerator();

        franchisePerformanceHistory.setFranchise(franchiseBack);
        assertThat(franchisePerformanceHistory.getFranchise()).isEqualTo(franchiseBack);

        franchisePerformanceHistory.franchise(null);
        assertThat(franchisePerformanceHistory.getFranchise()).isNull();
    }
}
