package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.Category;
import com.framasaas.repository.CategoryRepository;
import com.framasaas.service.criteria.CategoryCriteria;
import com.framasaas.service.dto.CategoryDTO;
import com.framasaas.service.mapper.CategoryMapper;
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
 * Service for executing complex queries for {@link Category} entities in the database.
 * The main input is a {@link CategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link CategoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategoryQueryService extends QueryService<Category> {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryQueryService.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryQueryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    /**
     * Return a {@link Page} of {@link CategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoryDTO> findByCriteria(CategoryCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Category> specification = createSpecification(criteria);
        return categoryRepository.findAll(specification, page).map(categoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategoryCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Category> specification = createSpecification(criteria);
        return categoryRepository.count(specification);
    }

    /**
     * Function to convert {@link CategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Category> createSpecification(CategoryCriteria criteria) {
        Specification<Category> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Category_.id),
                buildStringSpecification(criteria.getCategoryName(), Category_.categoryName),
                buildStringSpecification(criteria.getImagePath(), Category_.imagePath),
                buildStringSpecification(criteria.getVendorCategoryId(), Category_.vendorCategoryId),
                buildStringSpecification(criteria.getDescription(), Category_.description),
                buildSpecification(criteria.getIsActive(), Category_.isActive),
                buildStringSpecification(criteria.getCreateddBy(), Category_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), Category_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), Category_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), Category_.updatedTime),
                buildSpecification(criteria.getProductId(), root -> root.join(Category_.products, JoinType.LEFT).get(Product_.id)),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(Category_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                ),
                buildSpecification(criteria.getFranchiseRuleId(), root ->
                    root.join(Category_.franchiseRule, JoinType.LEFT).get(FranchiseAllocationRule_.id)
                ),
                buildSpecification(criteria.getFieldAgentSkillRuleId(), root ->
                    root.join(Category_.fieldAgentSkillRule, JoinType.LEFT).get(FieldAgentSkillRule_.id)
                )
            );
        }
        return specification;
    }
}
