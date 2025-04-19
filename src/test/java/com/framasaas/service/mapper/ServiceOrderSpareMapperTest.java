package com.framasaas.service.mapper;

import static com.framasaas.domain.ServiceOrderSpareAsserts.*;
import static com.framasaas.domain.ServiceOrderSpareTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceOrderSpareMapperTest {

    private ServiceOrderSpareMapper serviceOrderSpareMapper;

    @BeforeEach
    void setUp() {
        serviceOrderSpareMapper = new ServiceOrderSpareMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getServiceOrderSpareSample1();
        var actual = serviceOrderSpareMapper.toEntity(serviceOrderSpareMapper.toDto(expected));
        assertServiceOrderSpareAllPropertiesEquals(expected, actual);
    }
}
