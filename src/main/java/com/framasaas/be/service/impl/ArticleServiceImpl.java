package com.framasaas.be.service.impl;

import com.framasaas.be.domain.Article;
import com.framasaas.be.repository.ArticleRepository;
import com.framasaas.be.service.ArticleService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.Article}.
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private static final Logger LOG = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article save(Article article) {
        LOG.debug("Request to save Article : {}", article);
        return articleRepository.save(article);
    }

    @Override
    public Article update(Article article) {
        LOG.debug("Request to update Article : {}", article);
        return articleRepository.save(article);
    }

    @Override
    public Optional<Article> partialUpdate(Article article) {
        LOG.debug("Request to partially update Article : {}", article);

        return articleRepository
            .findById(article.getId())
            .map(existingArticle -> {
                if (article.getSerialNo() != null) {
                    existingArticle.setSerialNo(article.getSerialNo());
                }
                if (article.getVendorArticleId() != null) {
                    existingArticle.setVendorArticleId(article.getVendorArticleId());
                }
                if (article.getPurchaseDate() != null) {
                    existingArticle.setPurchaseDate(article.getPurchaseDate());
                }
                if (article.getPuchasePrice() != null) {
                    existingArticle.setPuchasePrice(article.getPuchasePrice());
                }
                if (article.getPurchaseStore() != null) {
                    existingArticle.setPurchaseStore(article.getPurchaseStore());
                }
                if (article.getInvoicePath() != null) {
                    existingArticle.setInvoicePath(article.getInvoicePath());
                }
                if (article.getIsValidated() != null) {
                    existingArticle.setIsValidated(article.getIsValidated());
                }
                if (article.getValidatedBy() != null) {
                    existingArticle.setValidatedBy(article.getValidatedBy());
                }
                if (article.getValidatedTime() != null) {
                    existingArticle.setValidatedTime(article.getValidatedTime());
                }
                if (article.getCreateddBy() != null) {
                    existingArticle.setCreateddBy(article.getCreateddBy());
                }
                if (article.getCreatedTime() != null) {
                    existingArticle.setCreatedTime(article.getCreatedTime());
                }
                if (article.getUpdatedBy() != null) {
                    existingArticle.setUpdatedBy(article.getUpdatedBy());
                }
                if (article.getUpdatedTime() != null) {
                    existingArticle.setUpdatedTime(article.getUpdatedTime());
                }

                return existingArticle;
            })
            .map(articleRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Article> findAll(Pageable pageable) {
        LOG.debug("Request to get all Articles");
        return articleRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Article> findOne(Long id) {
        LOG.debug("Request to get Article : {}", id);
        return articleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Article : {}", id);
        articleRepository.deleteById(id);
    }
}
