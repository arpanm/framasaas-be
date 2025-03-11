package com.framasaas.be.service.impl;

import com.framasaas.be.domain.ArticleWarrantyDetails;
import com.framasaas.be.repository.ArticleWarrantyDetailsRepository;
import com.framasaas.be.service.ArticleWarrantyDetailsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.ArticleWarrantyDetails}.
 */
@Service
@Transactional
public class ArticleWarrantyDetailsServiceImpl implements ArticleWarrantyDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(ArticleWarrantyDetailsServiceImpl.class);

    private final ArticleWarrantyDetailsRepository articleWarrantyDetailsRepository;

    public ArticleWarrantyDetailsServiceImpl(ArticleWarrantyDetailsRepository articleWarrantyDetailsRepository) {
        this.articleWarrantyDetailsRepository = articleWarrantyDetailsRepository;
    }

    @Override
    public ArticleWarrantyDetails save(ArticleWarrantyDetails articleWarrantyDetails) {
        LOG.debug("Request to save ArticleWarrantyDetails : {}", articleWarrantyDetails);
        return articleWarrantyDetailsRepository.save(articleWarrantyDetails);
    }

    @Override
    public ArticleWarrantyDetails update(ArticleWarrantyDetails articleWarrantyDetails) {
        LOG.debug("Request to update ArticleWarrantyDetails : {}", articleWarrantyDetails);
        return articleWarrantyDetailsRepository.save(articleWarrantyDetails);
    }

    @Override
    public Optional<ArticleWarrantyDetails> partialUpdate(ArticleWarrantyDetails articleWarrantyDetails) {
        LOG.debug("Request to partially update ArticleWarrantyDetails : {}", articleWarrantyDetails);

        return articleWarrantyDetailsRepository
            .findById(articleWarrantyDetails.getId())
            .map(existingArticleWarrantyDetails -> {
                if (articleWarrantyDetails.getWarrantyType() != null) {
                    existingArticleWarrantyDetails.setWarrantyType(articleWarrantyDetails.getWarrantyType());
                }
                if (articleWarrantyDetails.getVendorArticleWarrantyId() != null) {
                    existingArticleWarrantyDetails.setVendorArticleWarrantyId(articleWarrantyDetails.getVendorArticleWarrantyId());
                }
                if (articleWarrantyDetails.getVendorWarrantyMasterId() != null) {
                    existingArticleWarrantyDetails.setVendorWarrantyMasterId(articleWarrantyDetails.getVendorWarrantyMasterId());
                }
                if (articleWarrantyDetails.getStartDate() != null) {
                    existingArticleWarrantyDetails.setStartDate(articleWarrantyDetails.getStartDate());
                }
                if (articleWarrantyDetails.getEndDate() != null) {
                    existingArticleWarrantyDetails.setEndDate(articleWarrantyDetails.getEndDate());
                }
                if (articleWarrantyDetails.getSoldBy() != null) {
                    existingArticleWarrantyDetails.setSoldBy(articleWarrantyDetails.getSoldBy());
                }
                if (articleWarrantyDetails.getSoldByUser() != null) {
                    existingArticleWarrantyDetails.setSoldByUser(articleWarrantyDetails.getSoldByUser());
                }
                if (articleWarrantyDetails.getSoldDate() != null) {
                    existingArticleWarrantyDetails.setSoldDate(articleWarrantyDetails.getSoldDate());
                }
                if (articleWarrantyDetails.getIsActive() != null) {
                    existingArticleWarrantyDetails.setIsActive(articleWarrantyDetails.getIsActive());
                }
                if (articleWarrantyDetails.getCreateddBy() != null) {
                    existingArticleWarrantyDetails.setCreateddBy(articleWarrantyDetails.getCreateddBy());
                }
                if (articleWarrantyDetails.getCreatedTime() != null) {
                    existingArticleWarrantyDetails.setCreatedTime(articleWarrantyDetails.getCreatedTime());
                }
                if (articleWarrantyDetails.getUpdatedBy() != null) {
                    existingArticleWarrantyDetails.setUpdatedBy(articleWarrantyDetails.getUpdatedBy());
                }
                if (articleWarrantyDetails.getUpdatedTime() != null) {
                    existingArticleWarrantyDetails.setUpdatedTime(articleWarrantyDetails.getUpdatedTime());
                }

                return existingArticleWarrantyDetails;
            })
            .map(articleWarrantyDetailsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleWarrantyDetails> findAll(Pageable pageable) {
        LOG.debug("Request to get all ArticleWarrantyDetails");
        return articleWarrantyDetailsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArticleWarrantyDetails> findOne(Long id) {
        LOG.debug("Request to get ArticleWarrantyDetails : {}", id);
        return articleWarrantyDetailsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ArticleWarrantyDetails : {}", id);
        articleWarrantyDetailsRepository.deleteById(id);
    }
}
