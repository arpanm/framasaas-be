package com.framasaas.service.mapper;

import com.framasaas.domain.Hsn;
import com.framasaas.service.dto.HsnDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Hsn} and its DTO {@link HsnDTO}.
 */
@Mapper(componentModel = "spring")
public interface HsnMapper extends EntityMapper<HsnDTO, Hsn> {}
