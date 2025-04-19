package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FranchiseStatusHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FranchiseStatusHistoryDTO.class);
        FranchiseStatusHistoryDTO franchiseStatusHistoryDTO1 = new FranchiseStatusHistoryDTO();
        franchiseStatusHistoryDTO1.setId(1L);
        FranchiseStatusHistoryDTO franchiseStatusHistoryDTO2 = new FranchiseStatusHistoryDTO();
        assertThat(franchiseStatusHistoryDTO1).isNotEqualTo(franchiseStatusHistoryDTO2);
        franchiseStatusHistoryDTO2.setId(franchiseStatusHistoryDTO1.getId());
        assertThat(franchiseStatusHistoryDTO1).isEqualTo(franchiseStatusHistoryDTO2);
        franchiseStatusHistoryDTO2.setId(2L);
        assertThat(franchiseStatusHistoryDTO1).isNotEqualTo(franchiseStatusHistoryDTO2);
        franchiseStatusHistoryDTO1.setId(null);
        assertThat(franchiseStatusHistoryDTO1).isNotEqualTo(franchiseStatusHistoryDTO2);
    }
}
