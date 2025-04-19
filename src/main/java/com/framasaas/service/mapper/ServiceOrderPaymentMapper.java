package com.framasaas.service.mapper;

import com.framasaas.domain.ServiceOrderPayment;
import com.framasaas.service.dto.ServiceOrderPaymentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceOrderPayment} and its DTO {@link ServiceOrderPaymentDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServiceOrderPaymentMapper extends EntityMapper<ServiceOrderPaymentDTO, ServiceOrderPayment> {}
