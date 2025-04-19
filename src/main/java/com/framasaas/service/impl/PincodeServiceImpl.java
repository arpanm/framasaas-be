package com.framasaas.service.impl;

import com.framasaas.domain.Pincode;
import com.framasaas.repository.PincodeRepository;
import com.framasaas.service.PincodeService;
import com.framasaas.service.dto.PincodeDTO;
import com.framasaas.service.mapper.PincodeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.Pincode}.
 */
@Service
@Transactional
public class PincodeServiceImpl implements PincodeService {

    private static final Logger LOG = LoggerFactory.getLogger(PincodeServiceImpl.class);

    private final PincodeRepository pincodeRepository;

    private final PincodeMapper pincodeMapper;

    public PincodeServiceImpl(PincodeRepository pincodeRepository, PincodeMapper pincodeMapper) {
        this.pincodeRepository = pincodeRepository;
        this.pincodeMapper = pincodeMapper;
    }

    @Override
    public PincodeDTO save(PincodeDTO pincodeDTO) {
        LOG.debug("Request to save Pincode : {}", pincodeDTO);
        Pincode pincode = pincodeMapper.toEntity(pincodeDTO);
        pincode = pincodeRepository.save(pincode);
        return pincodeMapper.toDto(pincode);
    }

    @Override
    public PincodeDTO update(PincodeDTO pincodeDTO) {
        LOG.debug("Request to update Pincode : {}", pincodeDTO);
        Pincode pincode = pincodeMapper.toEntity(pincodeDTO);
        pincode = pincodeRepository.save(pincode);
        return pincodeMapper.toDto(pincode);
    }

    @Override
    public Optional<PincodeDTO> partialUpdate(PincodeDTO pincodeDTO) {
        LOG.debug("Request to partially update Pincode : {}", pincodeDTO);

        return pincodeRepository
            .findById(pincodeDTO.getId())
            .map(existingPincode -> {
                pincodeMapper.partialUpdate(existingPincode, pincodeDTO);

                return existingPincode;
            })
            .map(pincodeRepository::save)
            .map(pincodeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PincodeDTO> findOne(Long id) {
        LOG.debug("Request to get Pincode : {}", id);
        return pincodeRepository.findById(id).map(pincodeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Pincode : {}", id);
        pincodeRepository.deleteById(id);
    }
}
