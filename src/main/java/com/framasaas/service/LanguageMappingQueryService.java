package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.LanguageMapping;
import com.framasaas.repository.LanguageMappingRepository;
import com.framasaas.service.criteria.LanguageMappingCriteria;
import com.framasaas.service.dto.LanguageMappingDTO;
import com.framasaas.service.mapper.LanguageMappingMapper;
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
 * Service for executing complex queries for {@link LanguageMapping} entities in the database.
 * The main input is a {@link LanguageMappingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link LanguageMappingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LanguageMappingQueryService extends QueryService<LanguageMapping> {

    private static final Logger LOG = LoggerFactory.getLogger(LanguageMappingQueryService.class);

    private final LanguageMappingRepository languageMappingRepository;

    private final LanguageMappingMapper languageMappingMapper;

    public LanguageMappingQueryService(LanguageMappingRepository languageMappingRepository, LanguageMappingMapper languageMappingMapper) {
        this.languageMappingRepository = languageMappingRepository;
        this.languageMappingMapper = languageMappingMapper;
    }

    /**
     * Return a {@link Page} of {@link LanguageMappingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LanguageMappingDTO> findByCriteria(LanguageMappingCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LanguageMapping> specification = createSpecification(criteria);
        return languageMappingRepository.findAll(specification, page).map(languageMappingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LanguageMappingCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<LanguageMapping> specification = createSpecification(criteria);
        return languageMappingRepository.count(specification);
    }

    /**
     * Function to convert {@link LanguageMappingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LanguageMapping> createSpecification(LanguageMappingCriteria criteria) {
        Specification<LanguageMapping> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), LanguageMapping_.id),
                buildSpecification(criteria.getLang(), LanguageMapping_.lang),
                buildStringSpecification(criteria.getCreateddBy(), LanguageMapping_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), LanguageMapping_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), LanguageMapping_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), LanguageMapping_.updatedTime),
                buildSpecification(criteria.getFranchiseRuleId(), root ->
                    root.join(LanguageMapping_.franchiseRule, JoinType.LEFT).get(FranchiseAllocationRule_.id)
                ),
                buildSpecification(criteria.getFieldAgentSkillRuleId(), root ->
                    root.join(LanguageMapping_.fieldAgentSkillRule, JoinType.LEFT).get(FieldAgentSkillRule_.id)
                )
            );
        }
        return specification;
    }
}
