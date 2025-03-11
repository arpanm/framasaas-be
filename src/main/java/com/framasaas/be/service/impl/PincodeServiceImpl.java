package com.framasaas.be.service.impl;

import com.framasaas.be.domain.Pincode;
import com.framasaas.be.repository.PincodeRepository;
import com.framasaas.be.service.PincodeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.Pincode}.
 */
@Service
@Transactional
public class PincodeServiceImpl implements PincodeService {

    private static final Logger LOG = LoggerFactory.getLogger(PincodeServiceImpl.class);

    private final PincodeRepository pincodeRepository;

    public PincodeServiceImpl(PincodeRepository pincodeRepository) {
        this.pincodeRepository = pincodeRepository;
    }

    @Override
    public Pincode save(Pincode pincode) {
        LOG.debug("Request to save Pincode : {}", pincode);
        return pincodeRepository.save(pincode);
    }

    @Override
    public Pincode update(Pincode pincode) {
        LOG.debug("Request to update Pincode : {}", pincode);
        return pincodeRepository.save(pincode);
    }

    @Override
    public Optional<Pincode> partialUpdate(Pincode pincode) {
        LOG.debug("Request to partially update Pincode : {}", pincode);

        return pincodeRepository
            .findById(pincode.getId())
            .map(existingPincode -> {
                if (pincode.getPincode() != null) {
                    existingPincode.setPincode(pincode.getPincode());
                }
                if (pincode.getCreateddBy() != null) {
                    existingPincode.setCreateddBy(pincode.getCreateddBy());
                }
                if (pincode.getCreatedTime() != null) {
                    existingPincode.setCreatedTime(pincode.getCreatedTime());
                }
                if (pincode.getUpdatedBy() != null) {
                    existingPincode.setUpdatedBy(pincode.getUpdatedBy());
                }
                if (pincode.getUpdatedTime() != null) {
                    existingPincode.setUpdatedTime(pincode.getUpdatedTime());
                }

                return existingPincode;
            })
            .map(pincodeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Pincode> findAll(Pageable pageable) {
        LOG.debug("Request to get all Pincodes");
        return pincodeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pincode> findOne(Long id) {
        LOG.debug("Request to get Pincode : {}", id);
        return pincodeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Pincode : {}", id);
        pincodeRepository.deleteById(id);
    }
}
