package com.framasaas.service.impl;

import com.framasaas.domain.LocationMapping;
import com.framasaas.repository.LocationMappingRepository;
import com.framasaas.service.LocationMappingService;
import com.framasaas.service.dto.LocationMappingDTO;
import com.framasaas.service.mapper.LocationMappingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.LocationMapping}.
 */
@Service
@Transactional
public class LocationMappingServiceImpl implements LocationMappingService {

    private static final Logger LOG = LoggerFactory.getLogger(LocationMappingServiceImpl.class);

    private final LocationMappingRepository locationMappingRepository;

    private final LocationMappingMapper locationMappingMapper;

    public LocationMappingServiceImpl(LocationMappingRepository locationMappingRepository, LocationMappingMapper locationMappingMapper) {
        this.locationMappingRepository = locationMappingRepository;
        this.locationMappingMapper = locationMappingMapper;
    }

    @Override
    public LocationMappingDTO save(LocationMappingDTO locationMappingDTO) {
        LOG.debug("Request to save LocationMapping : {}", locationMappingDTO);
        LocationMapping locationMapping = locationMappingMapper.toEntity(locationMappingDTO);
        locationMapping = locationMappingRepository.save(locationMapping);
        return locationMappingMapper.toDto(locationMapping);
    }

    @Override
    public LocationMappingDTO update(LocationMappingDTO locationMappingDTO) {
        LOG.debug("Request to update LocationMapping : {}", locationMappingDTO);
        LocationMapping locationMapping = locationMappingMapper.toEntity(locationMappingDTO);
        locationMapping = locationMappingRepository.save(locationMapping);
        return locationMappingMapper.toDto(locationMapping);
    }

    @Override
    public Optional<LocationMappingDTO> partialUpdate(LocationMappingDTO locationMappingDTO) {
        LOG.debug("Request to partially update LocationMapping : {}", locationMappingDTO);

        return locationMappingRepository
            .findById(locationMappingDTO.getId())
            .map(existingLocationMapping -> {
                locationMappingMapper.partialUpdate(existingLocationMapping, locationMappingDTO);

                return existingLocationMapping;
            })
            .map(locationMappingRepository::save)
            .map(locationMappingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LocationMappingDTO> findOne(Long id) {
        LOG.debug("Request to get LocationMapping : {}", id);
        return locationMappingRepository.findById(id).map(locationMappingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete LocationMapping : {}", id);
        locationMappingRepository.deleteById(id);
    }
}
