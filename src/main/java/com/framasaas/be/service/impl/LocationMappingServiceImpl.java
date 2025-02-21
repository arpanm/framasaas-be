package com.framasaas.be.service.impl;

import com.framasaas.be.domain.LocationMapping;
import com.framasaas.be.repository.LocationMappingRepository;
import com.framasaas.be.service.LocationMappingService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.LocationMapping}.
 */
@Service
@Transactional
public class LocationMappingServiceImpl implements LocationMappingService {

    private static final Logger LOG = LoggerFactory.getLogger(LocationMappingServiceImpl.class);

    private final LocationMappingRepository locationMappingRepository;

    public LocationMappingServiceImpl(LocationMappingRepository locationMappingRepository) {
        this.locationMappingRepository = locationMappingRepository;
    }

    @Override
    public LocationMapping save(LocationMapping locationMapping) {
        LOG.debug("Request to save LocationMapping : {}", locationMapping);
        return locationMappingRepository.save(locationMapping);
    }

    @Override
    public LocationMapping update(LocationMapping locationMapping) {
        LOG.debug("Request to update LocationMapping : {}", locationMapping);
        return locationMappingRepository.save(locationMapping);
    }

    @Override
    public Optional<LocationMapping> partialUpdate(LocationMapping locationMapping) {
        LOG.debug("Request to partially update LocationMapping : {}", locationMapping);

        return locationMappingRepository
            .findById(locationMapping.getId())
            .map(existingLocationMapping -> {
                if (locationMapping.getLocationName() != null) {
                    existingLocationMapping.setLocationName(locationMapping.getLocationName());
                }

                return existingLocationMapping;
            })
            .map(locationMappingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LocationMapping> findAll(Pageable pageable) {
        LOG.debug("Request to get all LocationMappings");
        return locationMappingRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LocationMapping> findOne(Long id) {
        LOG.debug("Request to get LocationMapping : {}", id);
        return locationMappingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete LocationMapping : {}", id);
        locationMappingRepository.deleteById(id);
    }
}
