package com.framasaas.service.mapper;

import com.framasaas.domain.ServiceOrderFieldAgentAssignment;
import com.framasaas.service.dto.ServiceOrderFieldAgentAssignmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceOrderFieldAgentAssignment} and its DTO {@link ServiceOrderFieldAgentAssignmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServiceOrderFieldAgentAssignmentMapper
    extends EntityMapper<ServiceOrderFieldAgentAssignmentDTO, ServiceOrderFieldAgentAssignment> {}
