package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SupportingDocumentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupportingDocumentDTO.class);
        SupportingDocumentDTO supportingDocumentDTO1 = new SupportingDocumentDTO();
        supportingDocumentDTO1.setId(1L);
        SupportingDocumentDTO supportingDocumentDTO2 = new SupportingDocumentDTO();
        assertThat(supportingDocumentDTO1).isNotEqualTo(supportingDocumentDTO2);
        supportingDocumentDTO2.setId(supportingDocumentDTO1.getId());
        assertThat(supportingDocumentDTO1).isEqualTo(supportingDocumentDTO2);
        supportingDocumentDTO2.setId(2L);
        assertThat(supportingDocumentDTO1).isNotEqualTo(supportingDocumentDTO2);
        supportingDocumentDTO1.setId(null);
        assertThat(supportingDocumentDTO1).isNotEqualTo(supportingDocumentDTO2);
    }
}
