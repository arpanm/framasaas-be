package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.Product;
import com.framasaas.repository.ProductRepository;
import com.framasaas.service.criteria.ProductCriteria;
import com.framasaas.service.dto.ProductDTO;
import com.framasaas.service.mapper.ProductMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Product} entities in the database.
 * The main input is a {@link ProductCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ProductDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductQueryService extends QueryService<Product> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductQueryService.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductQueryService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Return a {@link Page} of {@link ProductDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> findByCriteria(ProductCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.findAll(specification, page).map(productMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Product> createSpecification(ProductCriteria criteria) {
        Specification<Product> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Product_.id),
                buildStringSpecification(criteria.getProductName(), Product_.productName),
                buildStringSpecification(criteria.getVendorProductId(), Product_.vendorProductId),
                buildStringSpecification(criteria.getDescription(), Product_.description),
                buildRangeSpecification(criteria.getPrice(), Product_.price),
                buildRangeSpecification(criteria.getTax(), Product_.tax),
                buildRangeSpecification(criteria.getFranchiseCommission(), Product_.franchiseCommission),
                buildRangeSpecification(criteria.getFranchiseTax(), Product_.franchiseTax),
                buildSpecification(criteria.getProductType(), Product_.productType),
                buildSpecification(criteria.getIsActive(), Product_.isActive),
                buildStringSpecification(criteria.getCreateddBy(), Product_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), Product_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), Product_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), Product_.updatedTime),
                buildSpecification(criteria.getProductPriceHistoryId(), root ->
                    root.join(Product_.productPriceHistories, JoinType.LEFT).get(ProductPriceHistory_.id)
                ),
                buildSpecification(criteria.getWarrantyMasterId(), root ->
                    root.join(Product_.warrantyMasters, JoinType.LEFT).get(WarrantyMaster_.id)
                ),
                buildSpecification(criteria.getArticleId(), root -> root.join(Product_.articles, JoinType.LEFT).get(Article_.id)),
                buildSpecification(criteria.getServiceOrderMasterId(), root ->
                    root.join(Product_.serviceOrderMasters, JoinType.LEFT).get(ServiceOrderMaster_.id)
                ),
                buildSpecification(criteria.getServiceOrderSpareId(), root ->
                    root.join(Product_.serviceOrderSpares, JoinType.LEFT).get(ServiceOrderSpare_.id)
                ),
                buildSpecification(criteria.getInventoryId(), root -> root.join(Product_.inventories, JoinType.LEFT).get(Inventory_.id)),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(Product_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                ),
                buildSpecification(criteria.getCategoryId(), root -> root.join(Product_.category, JoinType.LEFT).get(Category_.id)),
                buildSpecification(criteria.getBrandId(), root -> root.join(Product_.brand, JoinType.LEFT).get(Brand_.id)),
                buildSpecification(criteria.getHsnId(), root -> root.join(Product_.hsn, JoinType.LEFT).get(Hsn_.id)),
                buildSpecification(criteria.getCoveredUnderWarrantyId(), root ->
                    root.join(Product_.coveredUnderWarranties, JoinType.LEFT).get(WarrantyMaster_.id)
                )
            );
        }
        return specification;
    }
}
