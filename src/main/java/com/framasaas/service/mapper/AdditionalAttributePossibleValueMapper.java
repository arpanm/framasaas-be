package com.framasaas.service.mapper;

import com.framasaas.domain.AdditionalAttribute;
import com.framasaas.domain.AdditionalAttributePossibleValue;
import com.framasaas.service.dto.AdditionalAttributeDTO;
import com.framasaas.service.dto.AdditionalAttributePossibleValueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdditionalAttributePossibleValue} and its DTO {@link AdditionalAttributePossibleValueDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdditionalAttributePossibleValueMapper
    extends EntityMapper<AdditionalAttributePossibleValueDTO, AdditionalAttributePossibleValue> {
    @Mapping(target = "attribute", source = "attribute", qualifiedByName = "additionalAttributeId")
    AdditionalAttributePossibleValueDTO toDto(AdditionalAttributePossibleValue s);

    @Named("additionalAttributeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdditionalAttributeDTO toDtoAdditionalAttributeId(AdditionalAttribute additionalAttribute);
}
