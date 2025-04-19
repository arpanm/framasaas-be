package com.framasaas.domain;

import static com.framasaas.domain.ProductTestSamples.*;
import static com.framasaas.domain.ServiceOrderMasterTestSamples.*;
import static com.framasaas.domain.ServiceOrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
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
    void serviceOrderTest() {
        ServiceOrderMaster serviceOrderMaster = getServiceOrderMasterRandomSampleGenerator();
        ServiceOrder serviceOrderBack = getServiceOrderRandomSampleGenerator();

        serviceOrderMaster.addServiceOrder(serviceOrderBack);
        assertThat(serviceOrderMaster.getServiceOrders()).containsOnly(serviceOrderBack);
        assertThat(serviceOrderBack.getServiceMaster()).isEqualTo(serviceOrderMaster);

        serviceOrderMaster.removeServiceOrder(serviceOrderBack);
        assertThat(serviceOrderMaster.getServiceOrders()).doesNotContain(serviceOrderBack);
        assertThat(serviceOrderBack.getServiceMaster()).isNull();

        serviceOrderMaster.serviceOrders(new HashSet<>(Set.of(serviceOrderBack)));
        assertThat(serviceOrderMaster.getServiceOrders()).containsOnly(serviceOrderBack);
        assertThat(serviceOrderBack.getServiceMaster()).isEqualTo(serviceOrderMaster);

        serviceOrderMaster.setServiceOrders(new HashSet<>());
        assertThat(serviceOrderMaster.getServiceOrders()).doesNotContain(serviceOrderBack);
        assertThat(serviceOrderBack.getServiceMaster()).isNull();
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
