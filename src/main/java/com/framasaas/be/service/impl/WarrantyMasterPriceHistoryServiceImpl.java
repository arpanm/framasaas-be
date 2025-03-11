package com.framasaas.be.service.impl;

import com.framasaas.be.domain.WarrantyMasterPriceHistory;
import com.framasaas.be.repository.WarrantyMasterPriceHistoryRepository;
import com.framasaas.be.service.WarrantyMasterPriceHistoryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.WarrantyMasterPriceHistory}.
 */
@Service
@Transactional
public class WarrantyMasterPriceHistoryServiceImpl implements WarrantyMasterPriceHistoryService {

    private static final Logger LOG = LoggerFactory.getLogger(WarrantyMasterPriceHistoryServiceImpl.class);

    private final WarrantyMasterPriceHistoryRepository warrantyMasterPriceHistoryRepository;

    public WarrantyMasterPriceHistoryServiceImpl(WarrantyMasterPriceHistoryRepository warrantyMasterPriceHistoryRepository) {
        this.warrantyMasterPriceHistoryRepository = warrantyMasterPriceHistoryRepository;
    }

    @Override
    public WarrantyMasterPriceHistory save(WarrantyMasterPriceHistory warrantyMasterPriceHistory) {
        LOG.debug("Request to save WarrantyMasterPriceHistory : {}", warrantyMasterPriceHistory);
        return warrantyMasterPriceHistoryRepository.save(warrantyMasterPriceHistory);
    }

    @Override
    public WarrantyMasterPriceHistory update(WarrantyMasterPriceHistory warrantyMasterPriceHistory) {
        LOG.debug("Request to update WarrantyMasterPriceHistory : {}", warrantyMasterPriceHistory);
        return warrantyMasterPriceHistoryRepository.save(warrantyMasterPriceHistory);
    }

    @Override
    public Optional<WarrantyMasterPriceHistory> partialUpdate(WarrantyMasterPriceHistory warrantyMasterPriceHistory) {
        LOG.debug("Request to partially update WarrantyMasterPriceHistory : {}", warrantyMasterPriceHistory);

        return warrantyMasterPriceHistoryRepository
            .findById(warrantyMasterPriceHistory.getId())
            .map(existingWarrantyMasterPriceHistory -> {
                if (warrantyMasterPriceHistory.getPrice() != null) {
                    existingWarrantyMasterPriceHistory.setPrice(warrantyMasterPriceHistory.getPrice());
                }
                if (warrantyMasterPriceHistory.getTax() != null) {
                    existingWarrantyMasterPriceHistory.setTax(warrantyMasterPriceHistory.getTax());
                }
                if (warrantyMasterPriceHistory.getFranchiseCommission() != null) {
                    existingWarrantyMasterPriceHistory.setFranchiseCommission(warrantyMasterPriceHistory.getFranchiseCommission());
                }
                if (warrantyMasterPriceHistory.getFranchiseTax() != null) {
                    existingWarrantyMasterPriceHistory.setFranchiseTax(warrantyMasterPriceHistory.getFranchiseTax());
                }
                if (warrantyMasterPriceHistory.getUpdatedBy() != null) {
                    existingWarrantyMasterPriceHistory.setUpdatedBy(warrantyMasterPriceHistory.getUpdatedBy());
                }
                if (warrantyMasterPriceHistory.getUpdatedTime() != null) {
                    existingWarrantyMasterPriceHistory.setUpdatedTime(warrantyMasterPriceHistory.getUpdatedTime());
                }

                return existingWarrantyMasterPriceHistory;
            })
            .map(warrantyMasterPriceHistoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WarrantyMasterPriceHistory> findAll(Pageable pageable) {
        LOG.debug("Request to get all WarrantyMasterPriceHistories");
        return warrantyMasterPriceHistoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WarrantyMasterPriceHistory> findOne(Long id) {
        LOG.debug("Request to get WarrantyMasterPriceHistory : {}", id);
        return warrantyMasterPriceHistoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete WarrantyMasterPriceHistory : {}", id);
        warrantyMasterPriceHistoryRepository.deleteById(id);
    }
}
