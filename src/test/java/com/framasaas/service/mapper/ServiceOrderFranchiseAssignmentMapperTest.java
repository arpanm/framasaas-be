package com.framasaas.service.mapper;

import static com.framasaas.domain.ServiceOrderFranchiseAssignmentAsserts.*;
import static com.framasaas.domain.ServiceOrderFranchiseAssignmentTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceOrderFranchiseAssignmentMapperTest {

    private ServiceOrderFranchiseAssignmentMapper serviceOrderFranchiseAssignmentMapper;

    @BeforeEach
    void setUp() {
        serviceOrderFranchiseAssignmentMapper = new ServiceOrderFranchiseAssignmentMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getServiceOrderFranchiseAssignmentSample1();
        var actual = serviceOrderFranchiseAssignmentMapper.toEntity(serviceOrderFranchiseAssignmentMapper.toDto(expected));
        assertServiceOrderFranchiseAssignmentAllPropertiesEquals(expected, actual);
    }
}
