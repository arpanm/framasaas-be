package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.Franchise;
import com.framasaas.repository.FranchiseRepository;
import com.framasaas.service.criteria.FranchiseCriteria;
import com.framasaas.service.dto.FranchiseDTO;
import com.framasaas.service.mapper.FranchiseMapper;
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
 * Service for executing complex queries for {@link Franchise} entities in the database.
 * The main input is a {@link FranchiseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link FranchiseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FranchiseQueryService extends QueryService<Franchise> {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseQueryService.class);

    private final FranchiseRepository franchiseRepository;

    private final FranchiseMapper franchiseMapper;

    public FranchiseQueryService(FranchiseRepository franchiseRepository, FranchiseMapper franchiseMapper) {
        this.franchiseRepository = franchiseRepository;
        this.franchiseMapper = franchiseMapper;
    }

    /**
     * Return a {@link Page} of {@link FranchiseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FranchiseDTO> findByCriteria(FranchiseCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Franchise> specification = createSpecification(criteria);
        return franchiseRepository.findAll(specification, page).map(franchiseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FranchiseCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Franchise> specification = createSpecification(criteria);
        return franchiseRepository.count(specification);
    }

    /**
     * Function to convert {@link FranchiseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Franchise> createSpecification(FranchiseCriteria criteria) {
        Specification<Franchise> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Franchise_.id),
                buildStringSpecification(criteria.getFranchiseName(), Franchise_.franchiseName),
                buildStringSpecification(criteria.getOwner(), Franchise_.owner),
                buildStringSpecification(criteria.getEmail(), Franchise_.email),
                buildRangeSpecification(criteria.getContact(), Franchise_.contact),
                buildSpecification(criteria.getFranchiseStatus(), Franchise_.franchiseStatus),
                buildStringSpecification(criteria.getGstNumber(), Franchise_.gstNumber),
                buildStringSpecification(criteria.getRegistrationNumber(), Franchise_.registrationNumber),
                buildRangeSpecification(criteria.getPerformanceScore(), Franchise_.performanceScore),
                buildSpecification(criteria.getPerformanceTag(), Franchise_.performanceTag),
                buildRangeSpecification(criteria.getDailyMaxServiceLimit(), Franchise_.dailyMaxServiceLimit),
                buildStringSpecification(criteria.getCreateddBy(), Franchise_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), Franchise_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), Franchise_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), Franchise_.updatedTime),
                buildSpecification(criteria.getAddressId(), root -> root.join(Franchise_.address, JoinType.LEFT).get(Address_.id)),
                buildSpecification(criteria.getFranchiseStatusHistoryId(), root ->
                    root.join(Franchise_.franchiseStatusHistories, JoinType.LEFT).get(FranchiseStatusHistory_.id)
                ),
                buildSpecification(criteria.getFranchisePerformanceHistoryId(), root ->
                    root.join(Franchise_.franchisePerformanceHistories, JoinType.LEFT).get(FranchisePerformanceHistory_.id)
                ),
                buildSpecification(criteria.getSupportingDocumentId(), root ->
                    root.join(Franchise_.supportingDocuments, JoinType.LEFT).get(SupportingDocument_.id)
                ),
                buildSpecification(criteria.getFranchiseUserId(), root ->
                    root.join(Franchise_.franchiseUsers, JoinType.LEFT).get(FranchiseUser_.id)
                ),
                buildSpecification(criteria.getServiceOrderFranchiseAssignmentId(), root ->
                    root.join(Franchise_.serviceOrderFranchiseAssignments, JoinType.LEFT).get(ServiceOrderFranchiseAssignment_.id)
                ),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(Franchise_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                ),
                buildSpecification(criteria.getRulesetId(), root ->
                    root.join(Franchise_.ruleset, JoinType.LEFT).get(FranchiseAllocationRuleSet_.id)
                ),
                buildSpecification(criteria.getBrandsId(), root ->
                    root.join(Franchise_.brands, JoinType.LEFT).get(FranchiseBrandMapping_.id)
                ),
                buildSpecification(criteria.getCategoriesId(), root ->
                    root.join(Franchise_.categories, JoinType.LEFT).get(FranchiseCategoryMapping_.id)
                )
            );
        }
        return specification;
    }
}
