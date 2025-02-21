package com.framasaas.be.domain;

import static com.framasaas.be.domain.FranchiseCategoryMappingTestSamples.*;
import static com.framasaas.be.domain.FranchiseTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FranchiseCategoryMappingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FranchiseCategoryMapping.class);
        FranchiseCategoryMapping franchiseCategoryMapping1 = getFranchiseCategoryMappingSample1();
        FranchiseCategoryMapping franchiseCategoryMapping2 = new FranchiseCategoryMapping();
        assertThat(franchiseCategoryMapping1).isNotEqualTo(franchiseCategoryMapping2);

        franchiseCategoryMapping2.setId(franchiseCategoryMapping1.getId());
        assertThat(franchiseCategoryMapping1).isEqualTo(franchiseCategoryMapping2);

        franchiseCategoryMapping2 = getFranchiseCategoryMappingSample2();
        assertThat(franchiseCategoryMapping1).isNotEqualTo(franchiseCategoryMapping2);
    }

    @Test
    void franchiseTest() {
        FranchiseCategoryMapping franchiseCategoryMapping = getFranchiseCategoryMappingRandomSampleGenerator();
        Franchise franchiseBack = getFranchiseRandomSampleGenerator();

        franchiseCategoryMapping.setFranchise(franchiseBack);
        assertThat(franchiseCategoryMapping.getFranchise()).isEqualTo(franchiseBack);

        franchiseCategoryMapping.franchise(null);
        assertThat(franchiseCategoryMapping.getFranchise()).isNull();
    }
}
