package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FranchiseAllocationRuleSetDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FranchiseAllocationRuleSetDTO.class);
        FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO1 = new FranchiseAllocationRuleSetDTO();
        franchiseAllocationRuleSetDTO1.setId(1L);
        FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO2 = new FranchiseAllocationRuleSetDTO();
        assertThat(franchiseAllocationRuleSetDTO1).isNotEqualTo(franchiseAllocationRuleSetDTO2);
        franchiseAllocationRuleSetDTO2.setId(franchiseAllocationRuleSetDTO1.getId());
        assertThat(franchiseAllocationRuleSetDTO1).isEqualTo(franchiseAllocationRuleSetDTO2);
        franchiseAllocationRuleSetDTO2.setId(2L);
        assertThat(franchiseAllocationRuleSetDTO1).isNotEqualTo(franchiseAllocationRuleSetDTO2);
        franchiseAllocationRuleSetDTO1.setId(null);
        assertThat(franchiseAllocationRuleSetDTO1).isNotEqualTo(franchiseAllocationRuleSetDTO2);
    }
}
