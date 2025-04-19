package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.Pincode;
import com.framasaas.repository.PincodeRepository;
import com.framasaas.service.criteria.PincodeCriteria;
import com.framasaas.service.dto.PincodeDTO;
import com.framasaas.service.mapper.PincodeMapper;
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
 * Service for executing complex queries for {@link Pincode} entities in the database.
 * The main input is a {@link PincodeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link PincodeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PincodeQueryService extends QueryService<Pincode> {

    private static final Logger LOG = LoggerFactory.getLogger(PincodeQueryService.class);

    private final PincodeRepository pincodeRepository;

    private final PincodeMapper pincodeMapper;

    public PincodeQueryService(PincodeRepository pincodeRepository, PincodeMapper pincodeMapper) {
        this.pincodeRepository = pincodeRepository;
        this.pincodeMapper = pincodeMapper;
    }

    /**
     * Return a {@link Page} of {@link PincodeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PincodeDTO> findByCriteria(PincodeCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Pincode> specification = createSpecification(criteria);
        return pincodeRepository.findAll(specification, page).map(pincodeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PincodeCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Pincode> specification = createSpecification(criteria);
        return pincodeRepository.count(specification);
    }

    /**
     * Function to convert {@link PincodeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Pincode> createSpecification(PincodeCriteria criteria) {
        Specification<Pincode> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Pincode_.id),
                buildStringSpecification(criteria.getPincode(), Pincode_.pincode),
                buildStringSpecification(criteria.getCreateddBy(), Pincode_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), Pincode_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), Pincode_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), Pincode_.updatedTime),
                buildSpecification(criteria.getFranchiseRuleId(), root ->
                    root.join(Pincode_.franchiseRule, JoinType.LEFT).get(FranchiseAllocationRule_.id)
                ),
                buildSpecification(criteria.getFieldAgentSkillRuleId(), root ->
                    root.join(Pincode_.fieldAgentSkillRule, JoinType.LEFT).get(FieldAgentSkillRule_.id)
                )
            );
        }
        return specification;
    }
}
