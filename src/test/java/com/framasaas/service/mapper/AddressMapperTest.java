package com.framasaas.service.mapper;

import static com.framasaas.domain.AddressAsserts.*;
import static com.framasaas.domain.AddressTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddressMapperTest {

    private AddressMapper addressMapper;

    @BeforeEach
    void setUp() {
        addressMapper = new AddressMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAddressSample1();
        var actual = addressMapper.toEntity(addressMapper.toDto(expected));
        assertAddressAllPropertiesEquals(expected, actual);
    }
}
