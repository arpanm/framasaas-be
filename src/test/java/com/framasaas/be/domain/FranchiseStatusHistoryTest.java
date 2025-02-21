package com.framasaas.be.domain;

import static com.framasaas.be.domain.FranchiseStatusHistoryTestSamples.*;
import static com.framasaas.be.domain.FranchiseTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FranchiseStatusHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FranchiseStatusHistory.class);
        FranchiseStatusHistory franchiseStatusHistory1 = getFranchiseStatusHistorySample1();
        FranchiseStatusHistory franchiseStatusHistory2 = new FranchiseStatusHistory();
        assertThat(franchiseStatusHistory1).isNotEqualTo(franchiseStatusHistory2);

        franchiseStatusHistory2.setId(franchiseStatusHistory1.getId());
        assertThat(franchiseStatusHistory1).isEqualTo(franchiseStatusHistory2);

        franchiseStatusHistory2 = getFranchiseStatusHistorySample2();
        assertThat(franchiseStatusHistory1).isNotEqualTo(franchiseStatusHistory2);
    }

    @Test
    void franchiseTest() {
        FranchiseStatusHistory franchiseStatusHistory = getFranchiseStatusHistoryRandomSampleGenerator();
        Franchise franchiseBack = getFranchiseRandomSampleGenerator();

        franchiseStatusHistory.setFranchise(franchiseBack);
        assertThat(franchiseStatusHistory.getFranchise()).isEqualTo(franchiseBack);

        franchiseStatusHistory.franchise(null);
        assertThat(franchiseStatusHistory.getFranchise()).isNull();
    }
}
