package com.framasaas.domain;

import static com.framasaas.domain.FranchiseUserStatusHistoryTestSamples.*;
import static com.framasaas.domain.FranchiseUserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FranchiseUserStatusHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FranchiseUserStatusHistory.class);
        FranchiseUserStatusHistory franchiseUserStatusHistory1 = getFranchiseUserStatusHistorySample1();
        FranchiseUserStatusHistory franchiseUserStatusHistory2 = new FranchiseUserStatusHistory();
        assertThat(franchiseUserStatusHistory1).isNotEqualTo(franchiseUserStatusHistory2);

        franchiseUserStatusHistory2.setId(franchiseUserStatusHistory1.getId());
        assertThat(franchiseUserStatusHistory1).isEqualTo(franchiseUserStatusHistory2);

        franchiseUserStatusHistory2 = getFranchiseUserStatusHistorySample2();
        assertThat(franchiseUserStatusHistory1).isNotEqualTo(franchiseUserStatusHistory2);
    }

    @Test
    void franchiseUserTest() {
        FranchiseUserStatusHistory franchiseUserStatusHistory = getFranchiseUserStatusHistoryRandomSampleGenerator();
        FranchiseUser franchiseUserBack = getFranchiseUserRandomSampleGenerator();

        franchiseUserStatusHistory.setFranchiseUser(franchiseUserBack);
        assertThat(franchiseUserStatusHistory.getFranchiseUser()).isEqualTo(franchiseUserBack);

        franchiseUserStatusHistory.franchiseUser(null);
        assertThat(franchiseUserStatusHistory.getFranchiseUser()).isNull();
    }
}
