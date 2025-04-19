package com.framasaas.domain;

import static com.framasaas.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.domain.FranchiseDocumentTestSamples.*;
import static com.framasaas.domain.FranchiseTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
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
    void additionalAttributeTest() {
        FranchiseDocument franchiseDocument = getFranchiseDocumentRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        franchiseDocument.addAdditionalAttribute(additionalAttributeBack);
        assertThat(franchiseDocument.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getDocument()).isEqualTo(franchiseDocument);

        franchiseDocument.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(franchiseDocument.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getDocument()).isNull();

        franchiseDocument.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(franchiseDocument.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getDocument()).isEqualTo(franchiseDocument);

        franchiseDocument.setAdditionalAttributes(new HashSet<>());
        assertThat(franchiseDocument.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getDocument()).isNull();
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
