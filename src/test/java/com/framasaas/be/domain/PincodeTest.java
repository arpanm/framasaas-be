package com.framasaas.be.domain;

import static com.framasaas.be.domain.FranchiseAllocationRuleTestSamples.*;
import static com.framasaas.be.domain.PincodeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PincodeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pincode.class);
        Pincode pincode1 = getPincodeSample1();
        Pincode pincode2 = new Pincode();
        assertThat(pincode1).isNotEqualTo(pincode2);

        pincode2.setId(pincode1.getId());
        assertThat(pincode1).isEqualTo(pincode2);

        pincode2 = getPincodeSample2();
        assertThat(pincode1).isNotEqualTo(pincode2);
    }

    @Test
    void franchiseRuleTest() {
        Pincode pincode = getPincodeRandomSampleGenerator();
        FranchiseAllocationRule franchiseAllocationRuleBack = getFranchiseAllocationRuleRandomSampleGenerator();

        pincode.setFranchiseRule(franchiseAllocationRuleBack);
        assertThat(pincode.getFranchiseRule()).isEqualTo(franchiseAllocationRuleBack);

        pincode.franchiseRule(null);
        assertThat(pincode.getFranchiseRule()).isNull();
    }
}
