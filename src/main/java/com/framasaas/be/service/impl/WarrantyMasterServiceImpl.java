package com.framasaas.be.service.impl;

import com.framasaas.be.domain.WarrantyMaster;
import com.framasaas.be.repository.WarrantyMasterRepository;
import com.framasaas.be.service.WarrantyMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.WarrantyMaster}.
 */
@Service
@Transactional
public class WarrantyMasterServiceImpl implements WarrantyMasterService {

    private static final Logger LOG = LoggerFactory.getLogger(WarrantyMasterServiceImpl.class);

    private final WarrantyMasterRepository warrantyMasterRepository;

    public WarrantyMasterServiceImpl(WarrantyMasterRepository warrantyMasterRepository) {
        this.warrantyMasterRepository = warrantyMasterRepository;
    }

    @Override
    public WarrantyMaster save(WarrantyMaster warrantyMaster) {
        LOG.debug("Request to save WarrantyMaster : {}", warrantyMaster);
        return warrantyMasterRepository.save(warrantyMaster);
    }

    @Override
    public WarrantyMaster update(WarrantyMaster warrantyMaster) {
        LOG.debug("Request to update WarrantyMaster : {}", warrantyMaster);
        return warrantyMasterRepository.save(warrantyMaster);
    }

    @Override
    public Optional<WarrantyMaster> partialUpdate(WarrantyMaster warrantyMaster) {
        LOG.debug("Request to partially update WarrantyMaster : {}", warrantyMaster);

        return warrantyMasterRepository
            .findById(warrantyMaster.getId())
            .map(existingWarrantyMaster -> {
                if (warrantyMaster.getName() != null) {
                    existingWarrantyMaster.setName(warrantyMaster.getName());
                }
                if (warrantyMaster.getVendorWarrantyMasterId() != null) {
                    existingWarrantyMaster.setVendorWarrantyMasterId(warrantyMaster.getVendorWarrantyMasterId());
                }
                if (warrantyMaster.getWarrantyType() != null) {
                    existingWarrantyMaster.setWarrantyType(warrantyMaster.getWarrantyType());
                }
                if (warrantyMaster.getDescription() != null) {
                    existingWarrantyMaster.setDescription(warrantyMaster.getDescription());
                }
                if (warrantyMaster.getPrice() != null) {
                    existingWarrantyMaster.setPrice(warrantyMaster.getPrice());
                }
                if (warrantyMaster.getTax() != null) {
                    existingWarrantyMaster.setTax(warrantyMaster.getTax());
                }
                if (warrantyMaster.getFranchiseCommission() != null) {
                    existingWarrantyMaster.setFranchiseCommission(warrantyMaster.getFranchiseCommission());
                }
                if (warrantyMaster.getFranchiseTax() != null) {
                    existingWarrantyMaster.setFranchiseTax(warrantyMaster.getFranchiseTax());
                }
                if (warrantyMaster.getPeriodInMonths() != null) {
                    existingWarrantyMaster.setPeriodInMonths(warrantyMaster.getPeriodInMonths());
                }
                if (warrantyMaster.getTaxRate() != null) {
                    existingWarrantyMaster.setTaxRate(warrantyMaster.getTaxRate());
                }
                if (warrantyMaster.getCoverage() != null) {
                    existingWarrantyMaster.setCoverage(warrantyMaster.getCoverage());
                }
                if (warrantyMaster.getExclusion() != null) {
                    existingWarrantyMaster.setExclusion(warrantyMaster.getExclusion());
                }
                if (warrantyMaster.getIsActive() != null) {
                    existingWarrantyMaster.setIsActive(warrantyMaster.getIsActive());
                }
                if (warrantyMaster.getCreateddBy() != null) {
                    existingWarrantyMaster.setCreateddBy(warrantyMaster.getCreateddBy());
                }
                if (warrantyMaster.getCreatedTime() != null) {
                    existingWarrantyMaster.setCreatedTime(warrantyMaster.getCreatedTime());
                }
                if (warrantyMaster.getUpdatedBy() != null) {
                    existingWarrantyMaster.setUpdatedBy(warrantyMaster.getUpdatedBy());
                }
                if (warrantyMaster.getUpdatedTime() != null) {
                    existingWarrantyMaster.setUpdatedTime(warrantyMaster.getUpdatedTime());
                }

                return existingWarrantyMaster;
            })
            .map(warrantyMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WarrantyMaster> findAll(Pageable pageable) {
        LOG.debug("Request to get all WarrantyMasters");
        return warrantyMasterRepository.findAll(pageable);
    }

    public Page<WarrantyMaster> findAllWithEagerRelationships(Pageable pageable) {
        return warrantyMasterRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WarrantyMaster> findOne(Long id) {
        LOG.debug("Request to get WarrantyMaster : {}", id);
        return warrantyMasterRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete WarrantyMaster : {}", id);
        warrantyMasterRepository.deleteById(id);
    }
}
