package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.FieldAgentSkillRuleSet;
import com.framasaas.repository.FieldAgentSkillRuleSetRepository;
import com.framasaas.service.criteria.FieldAgentSkillRuleSetCriteria;
import com.framasaas.service.dto.FieldAgentSkillRuleSetDTO;
import com.framasaas.service.mapper.FieldAgentSkillRuleSetMapper;
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
 * Service for executing complex queries for {@link FieldAgentSkillRuleSet} entities in the database.
 * The main input is a {@link FieldAgentSkillRuleSetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link FieldAgentSkillRuleSetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FieldAgentSkillRuleSetQueryService extends QueryService<FieldAgentSkillRuleSet> {

    private static final Logger LOG = LoggerFactory.getLogger(FieldAgentSkillRuleSetQueryService.class);

    private final FieldAgentSkillRuleSetRepository fieldAgentSkillRuleSetRepository;

    private final FieldAgentSkillRuleSetMapper fieldAgentSkillRuleSetMapper;

    public FieldAgentSkillRuleSetQueryService(
        FieldAgentSkillRuleSetRepository fieldAgentSkillRuleSetRepository,
        FieldAgentSkillRuleSetMapper fieldAgentSkillRuleSetMapper
    ) {
        this.fieldAgentSkillRuleSetRepository = fieldAgentSkillRuleSetRepository;
        this.fieldAgentSkillRuleSetMapper = fieldAgentSkillRuleSetMapper;
    }

    /**
     * Return a {@link Page} of {@link FieldAgentSkillRuleSetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FieldAgentSkillRuleSetDTO> findByCriteria(FieldAgentSkillRuleSetCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FieldAgentSkillRuleSet> specification = createSpecification(criteria);
        return fieldAgentSkillRuleSetRepository.findAll(specification, page).map(fieldAgentSkillRuleSetMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FieldAgentSkillRuleSetCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<FieldAgentSkillRuleSet> specification = createSpecification(criteria);
        return fieldAgentSkillRuleSetRepository.count(specification);
    }

    /**
     * Function to convert {@link FieldAgentSkillRuleSetCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FieldAgentSkillRuleSet> createSpecification(FieldAgentSkillRuleSetCriteria criteria) {
        Specification<FieldAgentSkillRuleSet> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), FieldAgentSkillRuleSet_.id),
                buildSpecification(criteria.getSortType(), FieldAgentSkillRuleSet_.sortType),
                buildStringSpecification(criteria.getCreateddBy(), FieldAgentSkillRuleSet_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), FieldAgentSkillRuleSet_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), FieldAgentSkillRuleSet_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), FieldAgentSkillRuleSet_.updatedTime),
                buildSpecification(criteria.getFieldAgentSkillRuleId(), root ->
                    root.join(FieldAgentSkillRuleSet_.fieldAgentSkillRules, JoinType.LEFT).get(FieldAgentSkillRule_.id)
                ),
                buildSpecification(criteria.getFranchiseUserId(), root ->
                    root.join(FieldAgentSkillRuleSet_.franchiseUsers, JoinType.LEFT).get(FranchiseUser_.id)
                ),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(FieldAgentSkillRuleSet_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                )
            );
        }
        return specification;
    }
}
