package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FranchiseAllocationRuleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FranchiseAllocationRuleDTO.class);
        FranchiseAllocationRuleDTO franchiseAllocationRuleDTO1 = new FranchiseAllocationRuleDTO();
        franchiseAllocationRuleDTO1.setId(1L);
        FranchiseAllocationRuleDTO franchiseAllocationRuleDTO2 = new FranchiseAllocationRuleDTO();
        assertThat(franchiseAllocationRuleDTO1).isNotEqualTo(franchiseAllocationRuleDTO2);
        franchiseAllocationRuleDTO2.setId(franchiseAllocationRuleDTO1.getId());
        assertThat(franchiseAllocationRuleDTO1).isEqualTo(franchiseAllocationRuleDTO2);
        franchiseAllocationRuleDTO2.setId(2L);
        assertThat(franchiseAllocationRuleDTO1).isNotEqualTo(franchiseAllocationRuleDTO2);
        franchiseAllocationRuleDTO1.setId(null);
        assertThat(franchiseAllocationRuleDTO1).isNotEqualTo(franchiseAllocationRuleDTO2);
    }
}
