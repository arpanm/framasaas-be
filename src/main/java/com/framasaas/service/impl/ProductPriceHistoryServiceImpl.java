package com.framasaas.service.impl;

import com.framasaas.domain.ProductPriceHistory;
import com.framasaas.repository.ProductPriceHistoryRepository;
import com.framasaas.service.ProductPriceHistoryService;
import com.framasaas.service.dto.ProductPriceHistoryDTO;
import com.framasaas.service.mapper.ProductPriceHistoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.ProductPriceHistory}.
 */
@Service
@Transactional
public class ProductPriceHistoryServiceImpl implements ProductPriceHistoryService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductPriceHistoryServiceImpl.class);

    private final ProductPriceHistoryRepository productPriceHistoryRepository;

    private final ProductPriceHistoryMapper productPriceHistoryMapper;

    public ProductPriceHistoryServiceImpl(
        ProductPriceHistoryRepository productPriceHistoryRepository,
        ProductPriceHistoryMapper productPriceHistoryMapper
    ) {
        this.productPriceHistoryRepository = productPriceHistoryRepository;
        this.productPriceHistoryMapper = productPriceHistoryMapper;
    }

    @Override
    public ProductPriceHistoryDTO save(ProductPriceHistoryDTO productPriceHistoryDTO) {
        LOG.debug("Request to save ProductPriceHistory : {}", productPriceHistoryDTO);
        ProductPriceHistory productPriceHistory = productPriceHistoryMapper.toEntity(productPriceHistoryDTO);
        productPriceHistory = productPriceHistoryRepository.save(productPriceHistory);
        return productPriceHistoryMapper.toDto(productPriceHistory);
    }

    @Override
    public ProductPriceHistoryDTO update(ProductPriceHistoryDTO productPriceHistoryDTO) {
        LOG.debug("Request to update ProductPriceHistory : {}", productPriceHistoryDTO);
        ProductPriceHistory productPriceHistory = productPriceHistoryMapper.toEntity(productPriceHistoryDTO);
        productPriceHistory = productPriceHistoryRepository.save(productPriceHistory);
        return productPriceHistoryMapper.toDto(productPriceHistory);
    }

    @Override
    public Optional<ProductPriceHistoryDTO> partialUpdate(ProductPriceHistoryDTO productPriceHistoryDTO) {
        LOG.debug("Request to partially update ProductPriceHistory : {}", productPriceHistoryDTO);

        return productPriceHistoryRepository
            .findById(productPriceHistoryDTO.getId())
            .map(existingProductPriceHistory -> {
                productPriceHistoryMapper.partialUpdate(existingProductPriceHistory, productPriceHistoryDTO);

                return existingProductPriceHistory;
            })
            .map(productPriceHistoryRepository::save)
            .map(productPriceHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductPriceHistoryDTO> findOne(Long id) {
        LOG.debug("Request to get ProductPriceHistory : {}", id);
        return productPriceHistoryRepository.findById(id).map(productPriceHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ProductPriceHistory : {}", id);
        productPriceHistoryRepository.deleteById(id);
    }
}
