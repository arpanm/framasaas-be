package com.framasaas.service.mapper;

import com.framasaas.domain.Address;
import com.framasaas.domain.Customer;
import com.framasaas.domain.LocationMapping;
import com.framasaas.service.dto.AddressDTO;
import com.framasaas.service.dto.CustomerDTO;
import com.framasaas.service.dto.LocationMappingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationMappingId")
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    AddressDTO toDto(Address s);

    @Named("locationMappingId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationMappingDTO toDtoLocationMappingId(LocationMapping locationMapping);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);
}
