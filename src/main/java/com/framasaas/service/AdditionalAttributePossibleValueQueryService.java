package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.AdditionalAttributePossibleValue;
import com.framasaas.repository.AdditionalAttributePossibleValueRepository;
import com.framasaas.service.criteria.AdditionalAttributePossibleValueCriteria;
import com.framasaas.service.dto.AdditionalAttributePossibleValueDTO;
import com.framasaas.service.mapper.AdditionalAttributePossibleValueMapper;
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
 * Service for executing complex queries for {@link AdditionalAttributePossibleValue} entities in the database.
 * The main input is a {@link AdditionalAttributePossibleValueCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link AdditionalAttributePossibleValueDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdditionalAttributePossibleValueQueryService extends QueryService<AdditionalAttributePossibleValue> {

    private static final Logger LOG = LoggerFactory.getLogger(AdditionalAttributePossibleValueQueryService.class);

    private final AdditionalAttributePossibleValueRepository additionalAttributePossibleValueRepository;

    private final AdditionalAttributePossibleValueMapper additionalAttributePossibleValueMapper;

    public AdditionalAttributePossibleValueQueryService(
        AdditionalAttributePossibleValueRepository additionalAttributePossibleValueRepository,
        AdditionalAttributePossibleValueMapper additionalAttributePossibleValueMapper
    ) {
        this.additionalAttributePossibleValueRepository = additionalAttributePossibleValueRepository;
        this.additionalAttributePossibleValueMapper = additionalAttributePossibleValueMapper;
    }

    /**
     * Return a {@link Page} of {@link AdditionalAttributePossibleValueDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdditionalAttributePossibleValueDTO> findByCriteria(AdditionalAttributePossibleValueCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdditionalAttributePossibleValue> specification = createSpecification(criteria);
        return additionalAttributePossibleValueRepository.findAll(specification, page).map(additionalAttributePossibleValueMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdditionalAttributePossibleValueCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<AdditionalAttributePossibleValue> specification = createSpecification(criteria);
        return additionalAttributePossibleValueRepository.count(specification);
    }

    /**
     * Function to convert {@link AdditionalAttributePossibleValueCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdditionalAttributePossibleValue> createSpecification(AdditionalAttributePossibleValueCriteria criteria) {
        Specification<AdditionalAttributePossibleValue> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), AdditionalAttributePossibleValue_.id),
                buildStringSpecification(criteria.getPossibleValue(), AdditionalAttributePossibleValue_.possibleValue),
                buildStringSpecification(criteria.getCreateddBy(), AdditionalAttributePossibleValue_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), AdditionalAttributePossibleValue_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), AdditionalAttributePossibleValue_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), AdditionalAttributePossibleValue_.updatedTime),
                buildSpecification(criteria.getAttributeId(), root ->
                    root.join(AdditionalAttributePossibleValue_.attribute, JoinType.LEFT).get(AdditionalAttribute_.id)
                )
            );
        }
        return specification;
    }
}
