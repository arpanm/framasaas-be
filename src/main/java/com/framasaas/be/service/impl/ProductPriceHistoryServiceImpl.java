package com.framasaas.be.service.impl;

import com.framasaas.be.domain.ProductPriceHistory;
import com.framasaas.be.repository.ProductPriceHistoryRepository;
import com.framasaas.be.service.ProductPriceHistoryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.ProductPriceHistory}.
 */
@Service
@Transactional
public class ProductPriceHistoryServiceImpl implements ProductPriceHistoryService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductPriceHistoryServiceImpl.class);

    private final ProductPriceHistoryRepository productPriceHistoryRepository;

    public ProductPriceHistoryServiceImpl(ProductPriceHistoryRepository productPriceHistoryRepository) {
        this.productPriceHistoryRepository = productPriceHistoryRepository;
    }

    @Override
    public ProductPriceHistory save(ProductPriceHistory productPriceHistory) {
        LOG.debug("Request to save ProductPriceHistory : {}", productPriceHistory);
        return productPriceHistoryRepository.save(productPriceHistory);
    }

    @Override
    public ProductPriceHistory update(ProductPriceHistory productPriceHistory) {
        LOG.debug("Request to update ProductPriceHistory : {}", productPriceHistory);
        return productPriceHistoryRepository.save(productPriceHistory);
    }

    @Override
    public Optional<ProductPriceHistory> partialUpdate(ProductPriceHistory productPriceHistory) {
        LOG.debug("Request to partially update ProductPriceHistory : {}", productPriceHistory);

        return productPriceHistoryRepository
            .findById(productPriceHistory.getId())
            .map(existingProductPriceHistory -> {
                if (productPriceHistory.getPrice() != null) {
                    existingProductPriceHistory.setPrice(productPriceHistory.getPrice());
                }
                if (productPriceHistory.getTax() != null) {
                    existingProductPriceHistory.setTax(productPriceHistory.getTax());
                }
                if (productPriceHistory.getFranchiseCommission() != null) {
                    existingProductPriceHistory.setFranchiseCommission(productPriceHistory.getFranchiseCommission());
                }
                if (productPriceHistory.getFranchiseTax() != null) {
                    existingProductPriceHistory.setFranchiseTax(productPriceHistory.getFranchiseTax());
                }
                if (productPriceHistory.getUpdateDescription() != null) {
                    existingProductPriceHistory.setUpdateDescription(productPriceHistory.getUpdateDescription());
                }
                if (productPriceHistory.getUpdatedBy() != null) {
                    existingProductPriceHistory.setUpdatedBy(productPriceHistory.getUpdatedBy());
                }
                if (productPriceHistory.getUpdatedTime() != null) {
                    existingProductPriceHistory.setUpdatedTime(productPriceHistory.getUpdatedTime());
                }

                return existingProductPriceHistory;
            })
            .map(productPriceHistoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductPriceHistory> findAll(Pageable pageable) {
        LOG.debug("Request to get all ProductPriceHistories");
        return productPriceHistoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductPriceHistory> findOne(Long id) {
        LOG.debug("Request to get ProductPriceHistory : {}", id);
        return productPriceHistoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ProductPriceHistory : {}", id);
        productPriceHistoryRepository.deleteById(id);
    }
}
