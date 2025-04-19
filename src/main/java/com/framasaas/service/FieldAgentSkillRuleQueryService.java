package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.FieldAgentSkillRule;
import com.framasaas.repository.FieldAgentSkillRuleRepository;
import com.framasaas.service.criteria.FieldAgentSkillRuleCriteria;
import com.framasaas.service.dto.FieldAgentSkillRuleDTO;
import com.framasaas.service.mapper.FieldAgentSkillRuleMapper;
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
 * Service for executing complex queries for {@link FieldAgentSkillRule} entities in the database.
 * The main input is a {@link FieldAgentSkillRuleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link FieldAgentSkillRuleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FieldAgentSkillRuleQueryService extends QueryService<FieldAgentSkillRule> {

    private static final Logger LOG = LoggerFactory.getLogger(FieldAgentSkillRuleQueryService.class);

    private final FieldAgentSkillRuleRepository fieldAgentSkillRuleRepository;

    private final FieldAgentSkillRuleMapper fieldAgentSkillRuleMapper;

    public FieldAgentSkillRuleQueryService(
        FieldAgentSkillRuleRepository fieldAgentSkillRuleRepository,
        FieldAgentSkillRuleMapper fieldAgentSkillRuleMapper
    ) {
        this.fieldAgentSkillRuleRepository = fieldAgentSkillRuleRepository;
        this.fieldAgentSkillRuleMapper = fieldAgentSkillRuleMapper;
    }

    /**
     * Return a {@link Page} of {@link FieldAgentSkillRuleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FieldAgentSkillRuleDTO> findByCriteria(FieldAgentSkillRuleCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FieldAgentSkillRule> specification = createSpecification(criteria);
        return fieldAgentSkillRuleRepository.findAll(specification, page).map(fieldAgentSkillRuleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FieldAgentSkillRuleCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<FieldAgentSkillRule> specification = createSpecification(criteria);
        return fieldAgentSkillRuleRepository.count(specification);
    }

    /**
     * Function to convert {@link FieldAgentSkillRuleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FieldAgentSkillRule> createSpecification(FieldAgentSkillRuleCriteria criteria) {
        Specification<FieldAgentSkillRule> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), FieldAgentSkillRule_.id),
                buildSpecification(criteria.getSkillType(), FieldAgentSkillRule_.skillType),
                buildSpecification(criteria.getJoinType(), FieldAgentSkillRule_.joinType),
                buildStringSpecification(criteria.getCreateddBy(), FieldAgentSkillRule_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), FieldAgentSkillRule_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), FieldAgentSkillRule_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), FieldAgentSkillRule_.updatedTime),
                buildSpecification(criteria.getBrandId(), root -> root.join(FieldAgentSkillRule_.brands, JoinType.LEFT).get(Brand_.id)),
                buildSpecification(criteria.getCategoryId(), root ->
                    root.join(FieldAgentSkillRule_.categories, JoinType.LEFT).get(Category_.id)
                ),
                buildSpecification(criteria.getPincodeId(), root -> root.join(FieldAgentSkillRule_.pincodes, JoinType.LEFT).get(Pincode_.id)
                ),
                buildSpecification(criteria.getLocationMappingId(), root ->
                    root.join(FieldAgentSkillRule_.locationMappings, JoinType.LEFT).get(LocationMapping_.id)
                ),
                buildSpecification(criteria.getLanguageMappingId(), root ->
                    root.join(FieldAgentSkillRule_.languageMappings, JoinType.LEFT).get(LanguageMapping_.id)
                ),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(FieldAgentSkillRule_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                ),
                buildSpecification(criteria.getFieldAgentSkillRuleSetId(), root ->
                    root.join(FieldAgentSkillRule_.fieldAgentSkillRuleSet, JoinType.LEFT).get(FieldAgentSkillRuleSet_.id)
                )
            );
        }
        return specification;
    }
}
