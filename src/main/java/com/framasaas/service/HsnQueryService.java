package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.Hsn;
import com.framasaas.repository.HsnRepository;
import com.framasaas.service.criteria.HsnCriteria;
import com.framasaas.service.dto.HsnDTO;
import com.framasaas.service.mapper.HsnMapper;
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
 * Service for executing complex queries for {@link Hsn} entities in the database.
 * The main input is a {@link HsnCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link HsnDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HsnQueryService extends QueryService<Hsn> {

    private static final Logger LOG = LoggerFactory.getLogger(HsnQueryService.class);

    private final HsnRepository hsnRepository;

    private final HsnMapper hsnMapper;

    public HsnQueryService(HsnRepository hsnRepository, HsnMapper hsnMapper) {
        this.hsnRepository = hsnRepository;
        this.hsnMapper = hsnMapper;
    }

    /**
     * Return a {@link Page} of {@link HsnDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HsnDTO> findByCriteria(HsnCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Hsn> specification = createSpecification(criteria);
        return hsnRepository.findAll(specification, page).map(hsnMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HsnCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Hsn> specification = createSpecification(criteria);
        return hsnRepository.count(specification);
    }

    /**
     * Function to convert {@link HsnCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Hsn> createSpecification(HsnCriteria criteria) {
        Specification<Hsn> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Hsn_.id),
                buildStringSpecification(criteria.getHsnCD(), Hsn_.hsnCD),
                buildStringSpecification(criteria.getDescription(), Hsn_.description),
                buildRangeSpecification(criteria.getTaxRate(), Hsn_.taxRate),
                buildSpecification(criteria.getIsActive(), Hsn_.isActive),
                buildStringSpecification(criteria.getCreateddBy(), Hsn_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), Hsn_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), Hsn_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), Hsn_.updatedTime),
                buildSpecification(criteria.getProductId(), root -> root.join(Hsn_.products, JoinType.LEFT).get(Product_.id)),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(Hsn_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                )
            );
        }
        return specification;
    }
}
