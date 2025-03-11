package com.framasaas.be.service.impl;

import com.framasaas.be.domain.Category;
import com.framasaas.be.repository.CategoryRepository;
import com.framasaas.be.service.CategoryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.Category}.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        LOG.debug("Request to save Category : {}", category);
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        LOG.debug("Request to update Category : {}", category);
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> partialUpdate(Category category) {
        LOG.debug("Request to partially update Category : {}", category);

        return categoryRepository
            .findById(category.getId())
            .map(existingCategory -> {
                if (category.getCategoryName() != null) {
                    existingCategory.setCategoryName(category.getCategoryName());
                }
                if (category.getImagePath() != null) {
                    existingCategory.setImagePath(category.getImagePath());
                }
                if (category.getVendorCategoryId() != null) {
                    existingCategory.setVendorCategoryId(category.getVendorCategoryId());
                }
                if (category.getDescription() != null) {
                    existingCategory.setDescription(category.getDescription());
                }
                if (category.getIsActive() != null) {
                    existingCategory.setIsActive(category.getIsActive());
                }
                if (category.getCreateddBy() != null) {
                    existingCategory.setCreateddBy(category.getCreateddBy());
                }
                if (category.getCreatedTime() != null) {
                    existingCategory.setCreatedTime(category.getCreatedTime());
                }
                if (category.getUpdatedBy() != null) {
                    existingCategory.setUpdatedBy(category.getUpdatedBy());
                }
                if (category.getUpdatedTime() != null) {
                    existingCategory.setUpdatedTime(category.getUpdatedTime());
                }

                return existingCategory;
            })
            .map(categoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Category> findAll(Pageable pageable) {
        LOG.debug("Request to get all Categories");
        return categoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> findOne(Long id) {
        LOG.debug("Request to get Category : {}", id);
        return categoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Category : {}", id);
        categoryRepository.deleteById(id);
    }
}
