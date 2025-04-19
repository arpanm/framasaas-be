package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FranchisePerformanceHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FranchisePerformanceHistoryDTO.class);
        FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO1 = new FranchisePerformanceHistoryDTO();
        franchisePerformanceHistoryDTO1.setId(1L);
        FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO2 = new FranchisePerformanceHistoryDTO();
        assertThat(franchisePerformanceHistoryDTO1).isNotEqualTo(franchisePerformanceHistoryDTO2);
        franchisePerformanceHistoryDTO2.setId(franchisePerformanceHistoryDTO1.getId());
        assertThat(franchisePerformanceHistoryDTO1).isEqualTo(franchisePerformanceHistoryDTO2);
        franchisePerformanceHistoryDTO2.setId(2L);
        assertThat(franchisePerformanceHistoryDTO1).isNotEqualTo(franchisePerformanceHistoryDTO2);
        franchisePerformanceHistoryDTO1.setId(null);
        assertThat(franchisePerformanceHistoryDTO1).isNotEqualTo(franchisePerformanceHistoryDTO2);
    }
}
