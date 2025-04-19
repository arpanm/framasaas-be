package com.framasaas.service.mapper;

import com.framasaas.domain.Franchise;
import com.framasaas.domain.ServiceOrder;
import com.framasaas.domain.ServiceOrderFranchiseAssignment;
import com.framasaas.service.dto.FranchiseDTO;
import com.framasaas.service.dto.ServiceOrderDTO;
import com.framasaas.service.dto.ServiceOrderFranchiseAssignmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceOrderFranchiseAssignment} and its DTO {@link ServiceOrderFranchiseAssignmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServiceOrderFranchiseAssignmentMapper
    extends EntityMapper<ServiceOrderFranchiseAssignmentDTO, ServiceOrderFranchiseAssignment> {
    @Mapping(target = "serviceOrder", source = "serviceOrder", qualifiedByName = "serviceOrderId")
    @Mapping(target = "franchise", source = "franchise", qualifiedByName = "franchiseId")
    ServiceOrderFranchiseAssignmentDTO toDto(ServiceOrderFranchiseAssignment s);

    @Named("serviceOrderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServiceOrderDTO toDtoServiceOrderId(ServiceOrder serviceOrder);

    @Named("franchiseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FranchiseDTO toDtoFranchiseId(Franchise franchise);
}
