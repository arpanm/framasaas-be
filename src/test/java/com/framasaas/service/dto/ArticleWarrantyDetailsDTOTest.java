package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArticleWarrantyDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleWarrantyDetailsDTO.class);
        ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO1 = new ArticleWarrantyDetailsDTO();
        articleWarrantyDetailsDTO1.setId(1L);
        ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO2 = new ArticleWarrantyDetailsDTO();
        assertThat(articleWarrantyDetailsDTO1).isNotEqualTo(articleWarrantyDetailsDTO2);
        articleWarrantyDetailsDTO2.setId(articleWarrantyDetailsDTO1.getId());
        assertThat(articleWarrantyDetailsDTO1).isEqualTo(articleWarrantyDetailsDTO2);
        articleWarrantyDetailsDTO2.setId(2L);
        assertThat(articleWarrantyDetailsDTO1).isNotEqualTo(articleWarrantyDetailsDTO2);
        articleWarrantyDetailsDTO1.setId(null);
        assertThat(articleWarrantyDetailsDTO1).isNotEqualTo(articleWarrantyDetailsDTO2);
    }
}
