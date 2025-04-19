package com.framasaas.domain;

import static com.framasaas.domain.FranchiseBrandMappingTestSamples.*;
import static com.framasaas.domain.FranchiseTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FranchiseBrandMappingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FranchiseBrandMapping.class);
        FranchiseBrandMapping franchiseBrandMapping1 = getFranchiseBrandMappingSample1();
        FranchiseBrandMapping franchiseBrandMapping2 = new FranchiseBrandMapping();
        assertThat(franchiseBrandMapping1).isNotEqualTo(franchiseBrandMapping2);

        franchiseBrandMapping2.setId(franchiseBrandMapping1.getId());
        assertThat(franchiseBrandMapping1).isEqualTo(franchiseBrandMapping2);

        franchiseBrandMapping2 = getFranchiseBrandMappingSample2();
        assertThat(franchiseBrandMapping1).isNotEqualTo(franchiseBrandMapping2);
    }

    @Test
    void franchiseTest() {
        FranchiseBrandMapping franchiseBrandMapping = getFranchiseBrandMappingRandomSampleGenerator();
        Franchise franchiseBack = getFranchiseRandomSampleGenerator();

        franchiseBrandMapping.setFranchise(franchiseBack);
        assertThat(franchiseBrandMapping.getFranchise()).isEqualTo(franchiseBack);

        franchiseBrandMapping.franchise(null);
        assertThat(franchiseBrandMapping.getFranchise()).isNull();
    }
}
