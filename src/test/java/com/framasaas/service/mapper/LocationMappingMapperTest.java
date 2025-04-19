package com.framasaas.service.mapper;

import static com.framasaas.domain.LocationMappingAsserts.*;
import static com.framasaas.domain.LocationMappingTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocationMappingMapperTest {

    private LocationMappingMapper locationMappingMapper;

    @BeforeEach
    void setUp() {
        locationMappingMapper = new LocationMappingMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getLocationMappingSample1();
        var actual = locationMappingMapper.toEntity(locationMappingMapper.toDto(expected));
        assertLocationMappingAllPropertiesEquals(expected, actual);
    }
}
