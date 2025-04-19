package com.framasaas.service.mapper;

import static com.framasaas.domain.ServiceOrderMasterAsserts.*;
import static com.framasaas.domain.ServiceOrderMasterTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceOrderMasterMapperTest {

    private ServiceOrderMasterMapper serviceOrderMasterMapper;

    @BeforeEach
    void setUp() {
        serviceOrderMasterMapper = new ServiceOrderMasterMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getServiceOrderMasterSample1();
        var actual = serviceOrderMasterMapper.toEntity(serviceOrderMasterMapper.toDto(expected));
        assertServiceOrderMasterAllPropertiesEquals(expected, actual);
    }
}
