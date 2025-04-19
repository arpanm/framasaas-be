package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FranchiseUserDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FranchiseUserDTO.class);
        FranchiseUserDTO franchiseUserDTO1 = new FranchiseUserDTO();
        franchiseUserDTO1.setId(1L);
        FranchiseUserDTO franchiseUserDTO2 = new FranchiseUserDTO();
        assertThat(franchiseUserDTO1).isNotEqualTo(franchiseUserDTO2);
        franchiseUserDTO2.setId(franchiseUserDTO1.getId());
        assertThat(franchiseUserDTO1).isEqualTo(franchiseUserDTO2);
        franchiseUserDTO2.setId(2L);
        assertThat(franchiseUserDTO1).isNotEqualTo(franchiseUserDTO2);
        franchiseUserDTO1.setId(null);
        assertThat(franchiseUserDTO1).isNotEqualTo(franchiseUserDTO2);
    }
}
