package com.framasaas.be.domain;

import static com.framasaas.be.domain.ProductTestSamples.*;
import static com.framasaas.be.domain.ServiceOrderMasterTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceOrderMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOrderMaster.class);
        ServiceOrderMaster serviceOrderMaster1 = getServiceOrderMasterSample1();
        ServiceOrderMaster serviceOrderMaster2 = new ServiceOrderMaster();
        assertThat(serviceOrderMaster1).isNotEqualTo(serviceOrderMaster2);

        serviceOrderMaster2.setId(serviceOrderMaster1.getId());
        assertThat(serviceOrderMaster1).isEqualTo(serviceOrderMaster2);

        serviceOrderMaster2 = getServiceOrderMasterSample2();
        assertThat(serviceOrderMaster1).isNotEqualTo(serviceOrderMaster2);
    }

    @Test
    void productTest() {
        ServiceOrderMaster serviceOrderMaster = getServiceOrderMasterRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        serviceOrderMaster.setProduct(productBack);
        assertThat(serviceOrderMaster.getProduct()).isEqualTo(productBack);

        serviceOrderMaster.product(null);
        assertThat(serviceOrderMaster.getProduct()).isNull();
    }
}
