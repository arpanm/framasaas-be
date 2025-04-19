package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.ProductPriceHistory;
import com.framasaas.repository.ProductPriceHistoryRepository;
import com.framasaas.service.criteria.ProductPriceHistoryCriteria;
import com.framasaas.service.dto.ProductPriceHistoryDTO;
import com.framasaas.service.mapper.ProductPriceHistoryMapper;
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
 * Service for executing complex queries for {@link ProductPriceHistory} entities in the database.
 * The main input is a {@link ProductPriceHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ProductPriceHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductPriceHistoryQueryService extends QueryService<ProductPriceHistory> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductPriceHistoryQueryService.class);

    private final ProductPriceHistoryRepository productPriceHistoryRepository;

    private final ProductPriceHistoryMapper productPriceHistoryMapper;

    public ProductPriceHistoryQueryService(
        ProductPriceHistoryRepository productPriceHistoryRepository,
        ProductPriceHistoryMapper productPriceHistoryMapper
    ) {
        this.productPriceHistoryRepository = productPriceHistoryRepository;
        this.productPriceHistoryMapper = productPriceHistoryMapper;
    }

    /**
     * Return a {@link Page} of {@link ProductPriceHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductPriceHistoryDTO> findByCriteria(ProductPriceHistoryCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductPriceHistory> specification = createSpecification(criteria);
        return productPriceHistoryRepository.findAll(specification, page).map(productPriceHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductPriceHistoryCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<ProductPriceHistory> specification = createSpecification(criteria);
        return productPriceHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductPriceHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductPriceHistory> createSpecification(ProductPriceHistoryCriteria criteria) {
        Specification<ProductPriceHistory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), ProductPriceHistory_.id),
                buildRangeSpecification(criteria.getPrice(), ProductPriceHistory_.price),
                buildRangeSpecification(criteria.getTax(), ProductPriceHistory_.tax),
                buildRangeSpecification(criteria.getFranchiseCommission(), ProductPriceHistory_.franchiseCommission),
                buildRangeSpecification(criteria.getFranchiseTax(), ProductPriceHistory_.franchiseTax),
                buildStringSpecification(criteria.getUpdateDescription(), ProductPriceHistory_.updateDescription),
                buildStringSpecification(criteria.getUpdatedBy(), ProductPriceHistory_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), ProductPriceHistory_.updatedTime),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(ProductPriceHistory_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                ),
                buildSpecification(criteria.getProductId(), root -> root.join(ProductPriceHistory_.product, JoinType.LEFT).get(Product_.id))
            );
        }
        return specification;
    }
}
