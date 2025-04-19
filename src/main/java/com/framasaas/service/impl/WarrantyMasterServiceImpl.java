package com.framasaas.service.impl;

import com.framasaas.domain.WarrantyMaster;
import com.framasaas.repository.WarrantyMasterRepository;
import com.framasaas.service.WarrantyMasterService;
import com.framasaas.service.dto.WarrantyMasterDTO;
import com.framasaas.service.mapper.WarrantyMasterMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.WarrantyMaster}.
 */
@Service
@Transactional
public class WarrantyMasterServiceImpl implements WarrantyMasterService {

    private static final Logger LOG = LoggerFactory.getLogger(WarrantyMasterServiceImpl.class);

    private final WarrantyMasterRepository warrantyMasterRepository;

    private final WarrantyMasterMapper warrantyMasterMapper;

    public WarrantyMasterServiceImpl(WarrantyMasterRepository warrantyMasterRepository, WarrantyMasterMapper warrantyMasterMapper) {
        this.warrantyMasterRepository = warrantyMasterRepository;
        this.warrantyMasterMapper = warrantyMasterMapper;
    }

    @Override
    public WarrantyMasterDTO save(WarrantyMasterDTO warrantyMasterDTO) {
        LOG.debug("Request to save WarrantyMaster : {}", warrantyMasterDTO);
        WarrantyMaster warrantyMaster = warrantyMasterMapper.toEntity(warrantyMasterDTO);
        warrantyMaster = warrantyMasterRepository.save(warrantyMaster);
        return warrantyMasterMapper.toDto(warrantyMaster);
    }

    @Override
    public WarrantyMasterDTO update(WarrantyMasterDTO warrantyMasterDTO) {
        LOG.debug("Request to update WarrantyMaster : {}", warrantyMasterDTO);
        WarrantyMaster warrantyMaster = warrantyMasterMapper.toEntity(warrantyMasterDTO);
        warrantyMaster = warrantyMasterRepository.save(warrantyMaster);
        return warrantyMasterMapper.toDto(warrantyMaster);
    }

    @Override
    public Optional<WarrantyMasterDTO> partialUpdate(WarrantyMasterDTO warrantyMasterDTO) {
        LOG.debug("Request to partially update WarrantyMaster : {}", warrantyMasterDTO);

        return warrantyMasterRepository
            .findById(warrantyMasterDTO.getId())
            .map(existingWarrantyMaster -> {
                warrantyMasterMapper.partialUpdate(existingWarrantyMaster, warrantyMasterDTO);

                return existingWarrantyMaster;
            })
            .map(warrantyMasterRepository::save)
            .map(warrantyMasterMapper::toDto);
    }

    public Page<WarrantyMasterDTO> findAllWithEagerRelationships(Pageable pageable) {
        return warrantyMasterRepository.findAllWithEagerRelationships(pageable).map(warrantyMasterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WarrantyMasterDTO> findOne(Long id) {
        LOG.debug("Request to get WarrantyMaster : {}", id);
        return warrantyMasterRepository.findOneWithEagerRelationships(id).map(warrantyMasterMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete WarrantyMaster : {}", id);
        warrantyMasterRepository.deleteById(id);
    }
}
