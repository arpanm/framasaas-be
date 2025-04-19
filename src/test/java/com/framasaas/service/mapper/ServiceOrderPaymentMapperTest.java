package com.framasaas.service.mapper;

import static com.framasaas.domain.ServiceOrderPaymentAsserts.*;
import static com.framasaas.domain.ServiceOrderPaymentTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceOrderPaymentMapperTest {

    private ServiceOrderPaymentMapper serviceOrderPaymentMapper;

    @BeforeEach
    void setUp() {
        serviceOrderPaymentMapper = new ServiceOrderPaymentMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getServiceOrderPaymentSample1();
        var actual = serviceOrderPaymentMapper.toEntity(serviceOrderPaymentMapper.toDto(expected));
        assertServiceOrderPaymentAllPropertiesEquals(expected, actual);
    }
}
