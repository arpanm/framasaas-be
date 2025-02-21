package com.framasaas.be.domain;

import static com.framasaas.be.domain.FranchiseDocumentTestSamples.*;
import static com.framasaas.be.domain.FranchiseTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FranchiseDocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FranchiseDocument.class);
        FranchiseDocument franchiseDocument1 = getFranchiseDocumentSample1();
        FranchiseDocument franchiseDocument2 = new FranchiseDocument();
        assertThat(franchiseDocument1).isNotEqualTo(franchiseDocument2);

        franchiseDocument2.setId(franchiseDocument1.getId());
        assertThat(franchiseDocument1).isEqualTo(franchiseDocument2);

        franchiseDocument2 = getFranchiseDocumentSample2();
        assertThat(franchiseDocument1).isNotEqualTo(franchiseDocument2);
    }

    @Test
    void franchiseTest() {
        FranchiseDocument franchiseDocument = getFranchiseDocumentRandomSampleGenerator();
        Franchise franchiseBack = getFranchiseRandomSampleGenerator();

        franchiseDocument.setFranchise(franchiseBack);
        assertThat(franchiseDocument.getFranchise()).isEqualTo(franchiseBack);

        franchiseDocument.franchise(null);
        assertThat(franchiseDocument.getFranchise()).isNull();
    }
}
