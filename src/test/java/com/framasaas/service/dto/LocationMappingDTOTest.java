package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocationMappingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationMappingDTO.class);
        LocationMappingDTO locationMappingDTO1 = new LocationMappingDTO();
        locationMappingDTO1.setId(1L);
        LocationMappingDTO locationMappingDTO2 = new LocationMappingDTO();
        assertThat(locationMappingDTO1).isNotEqualTo(locationMappingDTO2);
        locationMappingDTO2.setId(locationMappingDTO1.getId());
        assertThat(locationMappingDTO1).isEqualTo(locationMappingDTO2);
        locationMappingDTO2.setId(2L);
        assertThat(locationMappingDTO1).isNotEqualTo(locationMappingDTO2);
        locationMappingDTO1.setId(null);
        assertThat(locationMappingDTO1).isNotEqualTo(locationMappingDTO2);
    }
}
