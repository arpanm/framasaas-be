package com.framasaas.service.impl;

import com.framasaas.domain.ArticleWarrantyDetails;
import com.framasaas.repository.ArticleWarrantyDetailsRepository;
import com.framasaas.service.ArticleWarrantyDetailsService;
import com.framasaas.service.dto.ArticleWarrantyDetailsDTO;
import com.framasaas.service.mapper.ArticleWarrantyDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.ArticleWarrantyDetails}.
 */
@Service
@Transactional
public class ArticleWarrantyDetailsServiceImpl implements ArticleWarrantyDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(ArticleWarrantyDetailsServiceImpl.class);

    private final ArticleWarrantyDetailsRepository articleWarrantyDetailsRepository;

    private final ArticleWarrantyDetailsMapper articleWarrantyDetailsMapper;

    public ArticleWarrantyDetailsServiceImpl(
        ArticleWarrantyDetailsRepository articleWarrantyDetailsRepository,
        ArticleWarrantyDetailsMapper articleWarrantyDetailsMapper
    ) {
        this.articleWarrantyDetailsRepository = articleWarrantyDetailsRepository;
        this.articleWarrantyDetailsMapper = articleWarrantyDetailsMapper;
    }

    @Override
    public ArticleWarrantyDetailsDTO save(ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO) {
        LOG.debug("Request to save ArticleWarrantyDetails : {}", articleWarrantyDetailsDTO);
        ArticleWarrantyDetails articleWarrantyDetails = articleWarrantyDetailsMapper.toEntity(articleWarrantyDetailsDTO);
        articleWarrantyDetails = articleWarrantyDetailsRepository.save(articleWarrantyDetails);
        return articleWarrantyDetailsMapper.toDto(articleWarrantyDetails);
    }

    @Override
    public ArticleWarrantyDetailsDTO update(ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO) {
        LOG.debug("Request to update ArticleWarrantyDetails : {}", articleWarrantyDetailsDTO);
        ArticleWarrantyDetails articleWarrantyDetails = articleWarrantyDetailsMapper.toEntity(articleWarrantyDetailsDTO);
        articleWarrantyDetails = articleWarrantyDetailsRepository.save(articleWarrantyDetails);
        return articleWarrantyDetailsMapper.toDto(articleWarrantyDetails);
    }

    @Override
    public Optional<ArticleWarrantyDetailsDTO> partialUpdate(ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO) {
        LOG.debug("Request to partially update ArticleWarrantyDetails : {}", articleWarrantyDetailsDTO);

        return articleWarrantyDetailsRepository
            .findById(articleWarrantyDetailsDTO.getId())
            .map(existingArticleWarrantyDetails -> {
                articleWarrantyDetailsMapper.partialUpdate(existingArticleWarrantyDetails, articleWarrantyDetailsDTO);

                return existingArticleWarrantyDetails;
            })
            .map(articleWarrantyDetailsRepository::save)
            .map(articleWarrantyDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArticleWarrantyDetailsDTO> findOne(Long id) {
        LOG.debug("Request to get ArticleWarrantyDetails : {}", id);
        return articleWarrantyDetailsRepository.findById(id).map(articleWarrantyDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ArticleWarrantyDetails : {}", id);
        articleWarrantyDetailsRepository.deleteById(id);
    }
}
