package com.framasaas.service.mapper;

import static com.framasaas.domain.SupportingDocumentAsserts.*;
import static com.framasaas.domain.SupportingDocumentTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupportingDocumentMapperTest {

    private SupportingDocumentMapper supportingDocumentMapper;

    @BeforeEach
    void setUp() {
        supportingDocumentMapper = new SupportingDocumentMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSupportingDocumentSample1();
        var actual = supportingDocumentMapper.toEntity(supportingDocumentMapper.toDto(expected));
        assertSupportingDocumentAllPropertiesEquals(expected, actual);
    }
}
