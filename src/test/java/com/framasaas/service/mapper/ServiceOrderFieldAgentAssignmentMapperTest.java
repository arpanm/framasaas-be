package com.framasaas.service.mapper;

import static com.framasaas.domain.ServiceOrderFieldAgentAssignmentAsserts.*;
import static com.framasaas.domain.ServiceOrderFieldAgentAssignmentTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceOrderFieldAgentAssignmentMapperTest {

    private ServiceOrderFieldAgentAssignmentMapper serviceOrderFieldAgentAssignmentMapper;

    @BeforeEach
    void setUp() {
        serviceOrderFieldAgentAssignmentMapper = new ServiceOrderFieldAgentAssignmentMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getServiceOrderFieldAgentAssignmentSample1();
        var actual = serviceOrderFieldAgentAssignmentMapper.toEntity(serviceOrderFieldAgentAssignmentMapper.toDto(expected));
        assertServiceOrderFieldAgentAssignmentAllPropertiesEquals(expected, actual);
    }
}
