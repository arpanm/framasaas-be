package com.framasaas.be.domain;

import static com.framasaas.be.domain.FranchiseAllocationRuleSetTestSamples.*;
import static com.framasaas.be.domain.FranchiseTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FranchiseAllocationRuleSetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FranchiseAllocationRuleSet.class);
        FranchiseAllocationRuleSet franchiseAllocationRuleSet1 = getFranchiseAllocationRuleSetSample1();
        FranchiseAllocationRuleSet franchiseAllocationRuleSet2 = new FranchiseAllocationRuleSet();
        assertThat(franchiseAllocationRuleSet1).isNotEqualTo(franchiseAllocationRuleSet2);

        franchiseAllocationRuleSet2.setId(franchiseAllocationRuleSet1.getId());
        assertThat(franchiseAllocationRuleSet1).isEqualTo(franchiseAllocationRuleSet2);

        franchiseAllocationRuleSet2 = getFranchiseAllocationRuleSetSample2();
        assertThat(franchiseAllocationRuleSet1).isNotEqualTo(franchiseAllocationRuleSet2);
    }

    @Test
    void franchiseTest() {
        FranchiseAllocationRuleSet franchiseAllocationRuleSet = getFranchiseAllocationRuleSetRandomSampleGenerator();
        Franchise franchiseBack = getFranchiseRandomSampleGenerator();

        franchiseAllocationRuleSet.addFranchise(franchiseBack);
        assertThat(franchiseAllocationRuleSet.getFranchises()).containsOnly(franchiseBack);
        assertThat(franchiseBack.getRuleset()).isEqualTo(franchiseAllocationRuleSet);

        franchiseAllocationRuleSet.removeFranchise(franchiseBack);
        assertThat(franchiseAllocationRuleSet.getFranchises()).doesNotContain(franchiseBack);
        assertThat(franchiseBack.getRuleset()).isNull();

        franchiseAllocationRuleSet.franchises(new HashSet<>(Set.of(franchiseBack)));
        assertThat(franchiseAllocationRuleSet.getFranchises()).containsOnly(franchiseBack);
        assertThat(franchiseBack.getRuleset()).isEqualTo(franchiseAllocationRuleSet);

        franchiseAllocationRuleSet.setFranchises(new HashSet<>());
        assertThat(franchiseAllocationRuleSet.getFranchises()).doesNotContain(franchiseBack);
        assertThat(franchiseBack.getRuleset()).isNull();
    }
}
