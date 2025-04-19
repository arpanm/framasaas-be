package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FranchiseUserStatusHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FranchiseUserStatusHistoryDTO.class);
        FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO1 = new FranchiseUserStatusHistoryDTO();
        franchiseUserStatusHistoryDTO1.setId(1L);
        FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO2 = new FranchiseUserStatusHistoryDTO();
        assertThat(franchiseUserStatusHistoryDTO1).isNotEqualTo(franchiseUserStatusHistoryDTO2);
        franchiseUserStatusHistoryDTO2.setId(franchiseUserStatusHistoryDTO1.getId());
        assertThat(franchiseUserStatusHistoryDTO1).isEqualTo(franchiseUserStatusHistoryDTO2);
        franchiseUserStatusHistoryDTO2.setId(2L);
        assertThat(franchiseUserStatusHistoryDTO1).isNotEqualTo(franchiseUserStatusHistoryDTO2);
        franchiseUserStatusHistoryDTO1.setId(null);
        assertThat(franchiseUserStatusHistoryDTO1).isNotEqualTo(franchiseUserStatusHistoryDTO2);
    }
}
