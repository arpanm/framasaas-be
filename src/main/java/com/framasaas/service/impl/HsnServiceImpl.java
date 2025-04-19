package com.framasaas.service.impl;

import com.framasaas.domain.Hsn;
import com.framasaas.repository.HsnRepository;
import com.framasaas.service.HsnService;
import com.framasaas.service.dto.HsnDTO;
import com.framasaas.service.mapper.HsnMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.Hsn}.
 */
@Service
@Transactional
public class HsnServiceImpl implements HsnService {

    private static final Logger LOG = LoggerFactory.getLogger(HsnServiceImpl.class);

    private final HsnRepository hsnRepository;

    private final HsnMapper hsnMapper;

    public HsnServiceImpl(HsnRepository hsnRepository, HsnMapper hsnMapper) {
        this.hsnRepository = hsnRepository;
        this.hsnMapper = hsnMapper;
    }

    @Override
    public HsnDTO save(HsnDTO hsnDTO) {
        LOG.debug("Request to save Hsn : {}", hsnDTO);
        Hsn hsn = hsnMapper.toEntity(hsnDTO);
        hsn = hsnRepository.save(hsn);
        return hsnMapper.toDto(hsn);
    }

    @Override
    public HsnDTO update(HsnDTO hsnDTO) {
        LOG.debug("Request to update Hsn : {}", hsnDTO);
        Hsn hsn = hsnMapper.toEntity(hsnDTO);
        hsn = hsnRepository.save(hsn);
        return hsnMapper.toDto(hsn);
    }

    @Override
    public Optional<HsnDTO> partialUpdate(HsnDTO hsnDTO) {
        LOG.debug("Request to partially update Hsn : {}", hsnDTO);

        return hsnRepository
            .findById(hsnDTO.getId())
            .map(existingHsn -> {
                hsnMapper.partialUpdate(existingHsn, hsnDTO);

                return existingHsn;
            })
            .map(hsnRepository::save)
            .map(hsnMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HsnDTO> findOne(Long id) {
        LOG.debug("Request to get Hsn : {}", id);
        return hsnRepository.findById(id).map(hsnMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Hsn : {}", id);
        hsnRepository.deleteById(id);
    }
}
