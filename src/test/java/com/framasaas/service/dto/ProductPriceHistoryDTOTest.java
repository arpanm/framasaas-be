package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductPriceHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductPriceHistoryDTO.class);
        ProductPriceHistoryDTO productPriceHistoryDTO1 = new ProductPriceHistoryDTO();
        productPriceHistoryDTO1.setId(1L);
        ProductPriceHistoryDTO productPriceHistoryDTO2 = new ProductPriceHistoryDTO();
        assertThat(productPriceHistoryDTO1).isNotEqualTo(productPriceHistoryDTO2);
        productPriceHistoryDTO2.setId(productPriceHistoryDTO1.getId());
        assertThat(productPriceHistoryDTO1).isEqualTo(productPriceHistoryDTO2);
        productPriceHistoryDTO2.setId(2L);
        assertThat(productPriceHistoryDTO1).isNotEqualTo(productPriceHistoryDTO2);
        productPriceHistoryDTO1.setId(null);
        assertThat(productPriceHistoryDTO1).isNotEqualTo(productPriceHistoryDTO2);
    }
}
