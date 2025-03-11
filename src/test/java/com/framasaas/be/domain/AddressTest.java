package com.framasaas.be.domain;

import static com.framasaas.be.domain.AddressTestSamples.*;
import static com.framasaas.be.domain.CustomerTestSamples.*;
import static com.framasaas.be.domain.FranchiseTestSamples.*;
import static com.framasaas.be.domain.LocationMappingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Address.class);
        Address address1 = getAddressSample1();
        Address address2 = new Address();
        assertThat(address1).isNotEqualTo(address2);

        address2.setId(address1.getId());
        assertThat(address1).isEqualTo(address2);

        address2 = getAddressSample2();
        assertThat(address1).isNotEqualTo(address2);
    }

    @Test
    void locationTest() {
        Address address = getAddressRandomSampleGenerator();
        LocationMapping locationMappingBack = getLocationMappingRandomSampleGenerator();

        address.setLocation(locationMappingBack);
        assertThat(address.getLocation()).isEqualTo(locationMappingBack);

        address.location(null);
        assertThat(address.getLocation()).isNull();
    }

    @Test
    void franchiseTest() {
        Address address = getAddressRandomSampleGenerator();
        Franchise franchiseBack = getFranchiseRandomSampleGenerator();

        address.setFranchise(franchiseBack);
        assertThat(address.getFranchise()).isEqualTo(franchiseBack);
        assertThat(franchiseBack.getAddress()).isEqualTo(address);

        address.franchise(null);
        assertThat(address.getFranchise()).isNull();
        assertThat(franchiseBack.getAddress()).isNull();
    }

    @Test
    void customerTest() {
        Address address = getAddressRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        address.setCustomer(customerBack);
        assertThat(address.getCustomer()).isEqualTo(customerBack);

        address.customer(null);
        assertThat(address.getCustomer()).isNull();
    }
}
