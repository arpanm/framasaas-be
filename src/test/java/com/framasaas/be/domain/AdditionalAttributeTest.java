package com.framasaas.be.domain;

import static com.framasaas.be.domain.AdditionalAttributePossibleValueTestSamples.*;
import static com.framasaas.be.domain.AdditionalAttributeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AdditionalAttributeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdditionalAttribute.class);
        AdditionalAttribute additionalAttribute1 = getAdditionalAttributeSample1();
        AdditionalAttribute additionalAttribute2 = new AdditionalAttribute();
        assertThat(additionalAttribute1).isNotEqualTo(additionalAttribute2);

        additionalAttribute2.setId(additionalAttribute1.getId());
        assertThat(additionalAttribute1).isEqualTo(additionalAttribute2);

        additionalAttribute2 = getAdditionalAttributeSample2();
        assertThat(additionalAttribute1).isNotEqualTo(additionalAttribute2);
    }

    @Test
    void additionalAttributePossibleValueTest() {
        AdditionalAttribute additionalAttribute = getAdditionalAttributeRandomSampleGenerator();
        AdditionalAttributePossibleValue additionalAttributePossibleValueBack = getAdditionalAttributePossibleValueRandomSampleGenerator();

        additionalAttribute.addAdditionalAttributePossibleValue(additionalAttributePossibleValueBack);
        assertThat(additionalAttribute.getAdditionalAttributePossibleValues()).containsOnly(additionalAttributePossibleValueBack);
        assertThat(additionalAttributePossibleValueBack.getAttribute()).isEqualTo(additionalAttribute);

        additionalAttribute.removeAdditionalAttributePossibleValue(additionalAttributePossibleValueBack);
        assertThat(additionalAttribute.getAdditionalAttributePossibleValues()).doesNotContain(additionalAttributePossibleValueBack);
        assertThat(additionalAttributePossibleValueBack.getAttribute()).isNull();

        additionalAttribute.additionalAttributePossibleValues(new HashSet<>(Set.of(additionalAttributePossibleValueBack)));
        assertThat(additionalAttribute.getAdditionalAttributePossibleValues()).containsOnly(additionalAttributePossibleValueBack);
        assertThat(additionalAttributePossibleValueBack.getAttribute()).isEqualTo(additionalAttribute);

        additionalAttribute.setAdditionalAttributePossibleValues(new HashSet<>());
        assertThat(additionalAttribute.getAdditionalAttributePossibleValues()).doesNotContain(additionalAttributePossibleValueBack);
        assertThat(additionalAttributePossibleValueBack.getAttribute()).isNull();
    }
}
