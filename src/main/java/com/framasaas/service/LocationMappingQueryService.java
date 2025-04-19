package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.LocationMapping;
import com.framasaas.repository.LocationMappingRepository;
import com.framasaas.service.criteria.LocationMappingCriteria;
import com.framasaas.service.dto.LocationMappingDTO;
import com.framasaas.service.mapper.LocationMappingMapper;
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
 * Service for executing complex queries for {@link LocationMapping} entities in the database.
 * The main input is a {@link LocationMappingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link LocationMappingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LocationMappingQueryService extends QueryService<LocationMapping> {

    private static final Logger LOG = LoggerFactory.getLogger(LocationMappingQueryService.class);

    private final LocationMappingRepository locationMappingRepository;

    private final LocationMappingMapper locationMappingMapper;

    public LocationMappingQueryService(LocationMappingRepository locationMappingRepository, LocationMappingMapper locationMappingMapper) {
        this.locationMappingRepository = locationMappingRepository;
        this.locationMappingMapper = locationMappingMapper;
    }

    /**
     * Return a {@link Page} of {@link LocationMappingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LocationMappingDTO> findByCriteria(LocationMappingCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LocationMapping> specification = createSpecification(criteria);
        return locationMappingRepository.findAll(specification, page).map(locationMappingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LocationMappingCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<LocationMapping> specification = createSpecification(criteria);
        return locationMappingRepository.count(specification);
    }

    /**
     * Function to convert {@link LocationMappingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LocationMapping> createSpecification(LocationMappingCriteria criteria) {
        Specification<LocationMapping> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), LocationMapping_.id),
                buildStringSpecification(criteria.getLocationName(), LocationMapping_.locationName),
                buildStringSpecification(criteria.getCreateddBy(), LocationMapping_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), LocationMapping_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), LocationMapping_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), LocationMapping_.updatedTime),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(LocationMapping_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                ),
                buildSpecification(criteria.getFranchiseRuleId(), root ->
                    root.join(LocationMapping_.franchiseRule, JoinType.LEFT).get(FranchiseAllocationRule_.id)
                ),
                buildSpecification(criteria.getFieldAgentSkillRuleId(), root ->
                    root.join(LocationMapping_.fieldAgentSkillRule, JoinType.LEFT).get(FieldAgentSkillRule_.id)
                )
            );
        }
        return specification;
    }
}
