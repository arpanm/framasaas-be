package com.framasaas.domain;

import static com.framasaas.domain.AdditionalAttributePossibleValueTestSamples.*;
import static com.framasaas.domain.AdditionalAttributeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdditionalAttributePossibleValueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdditionalAttributePossibleValue.class);
        AdditionalAttributePossibleValue additionalAttributePossibleValue1 = getAdditionalAttributePossibleValueSample1();
        AdditionalAttributePossibleValue additionalAttributePossibleValue2 = new AdditionalAttributePossibleValue();
        assertThat(additionalAttributePossibleValue1).isNotEqualTo(additionalAttributePossibleValue2);

        additionalAttributePossibleValue2.setId(additionalAttributePossibleValue1.getId());
        assertThat(additionalAttributePossibleValue1).isEqualTo(additionalAttributePossibleValue2);

        additionalAttributePossibleValue2 = getAdditionalAttributePossibleValueSample2();
        assertThat(additionalAttributePossibleValue1).isNotEqualTo(additionalAttributePossibleValue2);
    }

    @Test
    void attributeTest() {
        AdditionalAttributePossibleValue additionalAttributePossibleValue = getAdditionalAttributePossibleValueRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        additionalAttributePossibleValue.setAttribute(additionalAttributeBack);
        assertThat(additionalAttributePossibleValue.getAttribute()).isEqualTo(additionalAttributeBack);

        additionalAttributePossibleValue.attribute(null);
        assertThat(additionalAttributePossibleValue.getAttribute()).isNull();
    }
}
