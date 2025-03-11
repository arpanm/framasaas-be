package com.framasaas.be.service.impl;

import com.framasaas.be.domain.Hsn;
import com.framasaas.be.repository.HsnRepository;
import com.framasaas.be.service.HsnService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.Hsn}.
 */
@Service
@Transactional
public class HsnServiceImpl implements HsnService {

    private static final Logger LOG = LoggerFactory.getLogger(HsnServiceImpl.class);

    private final HsnRepository hsnRepository;

    public HsnServiceImpl(HsnRepository hsnRepository) {
        this.hsnRepository = hsnRepository;
    }

    @Override
    public Hsn save(Hsn hsn) {
        LOG.debug("Request to save Hsn : {}", hsn);
        return hsnRepository.save(hsn);
    }

    @Override
    public Hsn update(Hsn hsn) {
        LOG.debug("Request to update Hsn : {}", hsn);
        return hsnRepository.save(hsn);
    }

    @Override
    public Optional<Hsn> partialUpdate(Hsn hsn) {
        LOG.debug("Request to partially update Hsn : {}", hsn);

        return hsnRepository
            .findById(hsn.getId())
            .map(existingHsn -> {
                if (hsn.getHsnCD() != null) {
                    existingHsn.setHsnCD(hsn.getHsnCD());
                }
                if (hsn.getDescription() != null) {
                    existingHsn.setDescription(hsn.getDescription());
                }
                if (hsn.getTaxRate() != null) {
                    existingHsn.setTaxRate(hsn.getTaxRate());
                }
                if (hsn.getIsActive() != null) {
                    existingHsn.setIsActive(hsn.getIsActive());
                }
                if (hsn.getCreateddBy() != null) {
                    existingHsn.setCreateddBy(hsn.getCreateddBy());
                }
                if (hsn.getCreatedTime() != null) {
                    existingHsn.setCreatedTime(hsn.getCreatedTime());
                }
                if (hsn.getUpdatedBy() != null) {
                    existingHsn.setUpdatedBy(hsn.getUpdatedBy());
                }
                if (hsn.getUpdatedTime() != null) {
                    existingHsn.setUpdatedTime(hsn.getUpdatedTime());
                }

                return existingHsn;
            })
            .map(hsnRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Hsn> findAll(Pageable pageable) {
        LOG.debug("Request to get all Hsns");
        return hsnRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Hsn> findOne(Long id) {
        LOG.debug("Request to get Hsn : {}", id);
        return hsnRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Hsn : {}", id);
        hsnRepository.deleteById(id);
    }
}
