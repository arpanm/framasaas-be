package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FranchiseDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FranchiseDTO.class);
        FranchiseDTO franchiseDTO1 = new FranchiseDTO();
        franchiseDTO1.setId(1L);
        FranchiseDTO franchiseDTO2 = new FranchiseDTO();
        assertThat(franchiseDTO1).isNotEqualTo(franchiseDTO2);
        franchiseDTO2.setId(franchiseDTO1.getId());
        assertThat(franchiseDTO1).isEqualTo(franchiseDTO2);
        franchiseDTO2.setId(2L);
        assertThat(franchiseDTO1).isNotEqualTo(franchiseDTO2);
        franchiseDTO1.setId(null);
        assertThat(franchiseDTO1).isNotEqualTo(franchiseDTO2);
    }
}
