package com.framasaas.be.service.impl;

import com.framasaas.be.domain.Product;
import com.framasaas.be.repository.ProductRepository;
import com.framasaas.be.service.ProductService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.Product}.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        LOG.debug("Request to save Product : {}", product);
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        LOG.debug("Request to update Product : {}", product);
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> partialUpdate(Product product) {
        LOG.debug("Request to partially update Product : {}", product);

        return productRepository
            .findById(product.getId())
            .map(existingProduct -> {
                if (product.getProductName() != null) {
                    existingProduct.setProductName(product.getProductName());
                }
                if (product.getVendorProductId() != null) {
                    existingProduct.setVendorProductId(product.getVendorProductId());
                }
                if (product.getDescription() != null) {
                    existingProduct.setDescription(product.getDescription());
                }
                if (product.getPrice() != null) {
                    existingProduct.setPrice(product.getPrice());
                }
                if (product.getTax() != null) {
                    existingProduct.setTax(product.getTax());
                }
                if (product.getFranchiseCommission() != null) {
                    existingProduct.setFranchiseCommission(product.getFranchiseCommission());
                }
                if (product.getFranchiseTax() != null) {
                    existingProduct.setFranchiseTax(product.getFranchiseTax());
                }
                if (product.getProductType() != null) {
                    existingProduct.setProductType(product.getProductType());
                }
                if (product.getIsActive() != null) {
                    existingProduct.setIsActive(product.getIsActive());
                }
                if (product.getCreateddBy() != null) {
                    existingProduct.setCreateddBy(product.getCreateddBy());
                }
                if (product.getCreatedTime() != null) {
                    existingProduct.setCreatedTime(product.getCreatedTime());
                }
                if (product.getUpdatedBy() != null) {
                    existingProduct.setUpdatedBy(product.getUpdatedBy());
                }
                if (product.getUpdatedTime() != null) {
                    existingProduct.setUpdatedTime(product.getUpdatedTime());
                }

                return existingProduct;
            })
            .map(productRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findAll(Pageable pageable) {
        LOG.debug("Request to get all Products");
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findOne(Long id) {
        LOG.debug("Request to get Product : {}", id);
        return productRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Product : {}", id);
        productRepository.deleteById(id);
    }
}
