package com.framasaas.be.service.impl;

import com.framasaas.be.domain.ArticleWarrantyDetailsDocument;
import com.framasaas.be.repository.ArticleWarrantyDetailsDocumentRepository;
import com.framasaas.be.service.ArticleWarrantyDetailsDocumentService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.ArticleWarrantyDetailsDocument}.
 */
@Service
@Transactional
public class ArticleWarrantyDetailsDocumentServiceImpl implements ArticleWarrantyDetailsDocumentService {

    private static final Logger LOG = LoggerFactory.getLogger(ArticleWarrantyDetailsDocumentServiceImpl.class);

    private final ArticleWarrantyDetailsDocumentRepository articleWarrantyDetailsDocumentRepository;

    public ArticleWarrantyDetailsDocumentServiceImpl(ArticleWarrantyDetailsDocumentRepository articleWarrantyDetailsDocumentRepository) {
        this.articleWarrantyDetailsDocumentRepository = articleWarrantyDetailsDocumentRepository;
    }

    @Override
    public ArticleWarrantyDetailsDocument save(ArticleWarrantyDetailsDocument articleWarrantyDetailsDocument) {
        LOG.debug("Request to save ArticleWarrantyDetailsDocument : {}", articleWarrantyDetailsDocument);
        return articleWarrantyDetailsDocumentRepository.save(articleWarrantyDetailsDocument);
    }

    @Override
    public ArticleWarrantyDetailsDocument update(ArticleWarrantyDetailsDocument articleWarrantyDetailsDocument) {
        LOG.debug("Request to update ArticleWarrantyDetailsDocument : {}", articleWarrantyDetailsDocument);
        return articleWarrantyDetailsDocumentRepository.save(articleWarrantyDetailsDocument);
    }

    @Override
    public Optional<ArticleWarrantyDetailsDocument> partialUpdate(ArticleWarrantyDetailsDocument articleWarrantyDetailsDocument) {
        LOG.debug("Request to partially update ArticleWarrantyDetailsDocument : {}", articleWarrantyDetailsDocument);

        return articleWarrantyDetailsDocumentRepository
            .findById(articleWarrantyDetailsDocument.getId())
            .map(existingArticleWarrantyDetailsDocument -> {
                if (articleWarrantyDetailsDocument.getDocumentPath() != null) {
                    existingArticleWarrantyDetailsDocument.setDocumentPath(articleWarrantyDetailsDocument.getDocumentPath());
                }
                if (articleWarrantyDetailsDocument.getIsValidated() != null) {
                    existingArticleWarrantyDetailsDocument.setIsValidated(articleWarrantyDetailsDocument.getIsValidated());
                }
                if (articleWarrantyDetailsDocument.getValidatedBy() != null) {
                    existingArticleWarrantyDetailsDocument.setValidatedBy(articleWarrantyDetailsDocument.getValidatedBy());
                }
                if (articleWarrantyDetailsDocument.getValidatedTime() != null) {
                    existingArticleWarrantyDetailsDocument.setValidatedTime(articleWarrantyDetailsDocument.getValidatedTime());
                }
                if (articleWarrantyDetailsDocument.getCreateddBy() != null) {
                    existingArticleWarrantyDetailsDocument.setCreateddBy(articleWarrantyDetailsDocument.getCreateddBy());
                }
                if (articleWarrantyDetailsDocument.getCreatedTime() != null) {
                    existingArticleWarrantyDetailsDocument.setCreatedTime(articleWarrantyDetailsDocument.getCreatedTime());
                }
                if (articleWarrantyDetailsDocument.getUpdatedBy() != null) {
                    existingArticleWarrantyDetailsDocument.setUpdatedBy(articleWarrantyDetailsDocument.getUpdatedBy());
                }
                if (articleWarrantyDetailsDocument.getUpdatedTime() != null) {
                    existingArticleWarrantyDetailsDocument.setUpdatedTime(articleWarrantyDetailsDocument.getUpdatedTime());
                }

                return existingArticleWarrantyDetailsDocument;
            })
            .map(articleWarrantyDetailsDocumentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleWarrantyDetailsDocument> findAll(Pageable pageable) {
        LOG.debug("Request to get all ArticleWarrantyDetailsDocuments");
        return articleWarrantyDetailsDocumentRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArticleWarrantyDetailsDocument> findOne(Long id) {
        LOG.debug("Request to get ArticleWarrantyDetailsDocument : {}", id);
        return articleWarrantyDetailsDocumentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ArticleWarrantyDetailsDocument : {}", id);
        articleWarrantyDetailsDocumentRepository.deleteById(id);
    }
}
