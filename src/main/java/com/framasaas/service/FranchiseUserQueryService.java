package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.FranchiseUser;
import com.framasaas.repository.FranchiseUserRepository;
import com.framasaas.service.criteria.FranchiseUserCriteria;
import com.framasaas.service.dto.FranchiseUserDTO;
import com.framasaas.service.mapper.FranchiseUserMapper;
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
 * Service for executing complex queries for {@link FranchiseUser} entities in the database.
 * The main input is a {@link FranchiseUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link FranchiseUserDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FranchiseUserQueryService extends QueryService<FranchiseUser> {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseUserQueryService.class);

    private final FranchiseUserRepository franchiseUserRepository;

    private final FranchiseUserMapper franchiseUserMapper;

    public FranchiseUserQueryService(FranchiseUserRepository franchiseUserRepository, FranchiseUserMapper franchiseUserMapper) {
        this.franchiseUserRepository = franchiseUserRepository;
        this.franchiseUserMapper = franchiseUserMapper;
    }

    /**
     * Return a {@link Page} of {@link FranchiseUserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FranchiseUserDTO> findByCriteria(FranchiseUserCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FranchiseUser> specification = createSpecification(criteria);
        return franchiseUserRepository.findAll(specification, page).map(franchiseUserMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FranchiseUserCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<FranchiseUser> specification = createSpecification(criteria);
        return franchiseUserRepository.count(specification);
    }

    /**
     * Function to convert {@link FranchiseUserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FranchiseUser> createSpecification(FranchiseUserCriteria criteria) {
        Specification<FranchiseUser> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), FranchiseUser_.id),
                buildStringSpecification(criteria.getUserName(), FranchiseUser_.userName),
                buildStringSpecification(criteria.getEmail(), FranchiseUser_.email),
                buildRangeSpecification(criteria.getContact(), FranchiseUser_.contact),
                buildSpecification(criteria.getUserStatus(), FranchiseUser_.userStatus),
                buildSpecification(criteria.getUserRole(), FranchiseUser_.userRole),
                buildStringSpecification(criteria.getCreateddBy(), FranchiseUser_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), FranchiseUser_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), FranchiseUser_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), FranchiseUser_.updatedTime),
                buildSpecification(criteria.getFranchiseUserStatusHistoryId(), root ->
                    root.join(FranchiseUser_.franchiseUserStatusHistories, JoinType.LEFT).get(FranchiseUserStatusHistory_.id)
                ),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(FranchiseUser_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                ),
                buildSpecification(criteria.getFranchiseId(), root -> root.join(FranchiseUser_.franchise, JoinType.LEFT).get(Franchise_.id)
                ),
                buildSpecification(criteria.getSkillRuleSetId(), root ->
                    root.join(FranchiseUser_.skillRuleSet, JoinType.LEFT).get(FieldAgentSkillRuleSet_.id)
                )
            );
        }
        return specification;
    }
}
