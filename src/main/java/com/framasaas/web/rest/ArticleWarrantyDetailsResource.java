package com.framasaas.web.rest;

import com.framasaas.repository.ArticleWarrantyDetailsRepository;
import com.framasaas.service.ArticleWarrantyDetailsQueryService;
import com.framasaas.service.ArticleWarrantyDetailsService;
import com.framasaas.service.criteria.ArticleWarrantyDetailsCriteria;
import com.framasaas.service.dto.ArticleWarrantyDetailsDTO;
import com.framasaas.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.framasaas.domain.ArticleWarrantyDetails}.
 */
@RestController
@RequestMapping("/api/article-warranty-details")
public class ArticleWarrantyDetailsResource {

    private static final Logger LOG = LoggerFactory.getLogger(ArticleWarrantyDetailsResource.class);

    private static final String ENTITY_NAME = "articleWarrantyDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticleWarrantyDetailsService articleWarrantyDetailsService;

    private final ArticleWarrantyDetailsRepository articleWarrantyDetailsRepository;

    private final ArticleWarrantyDetailsQueryService articleWarrantyDetailsQueryService;

    public ArticleWarrantyDetailsResource(
        ArticleWarrantyDetailsService articleWarrantyDetailsService,
        ArticleWarrantyDetailsRepository articleWarrantyDetailsRepository,
        ArticleWarrantyDetailsQueryService articleWarrantyDetailsQueryService
    ) {
        this.articleWarrantyDetailsService = articleWarrantyDetailsService;
        this.articleWarrantyDetailsRepository = articleWarrantyDetailsRepository;
        this.articleWarrantyDetailsQueryService = articleWarrantyDetailsQueryService;
    }

    /**
     * {@code POST  /article-warranty-details} : Create a new articleWarrantyDetails.
     *
     * @param articleWarrantyDetailsDTO the articleWarrantyDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articleWarrantyDetailsDTO, or with status {@code 400 (Bad Request)} if the articleWarrantyDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ArticleWarrantyDetailsDTO> createArticleWarrantyDetails(
        @Valid @RequestBody ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save ArticleWarrantyDetails : {}", articleWarrantyDetailsDTO);
        if (articleWarrantyDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new articleWarrantyDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        articleWarrantyDetailsDTO = articleWarrantyDetailsService.save(articleWarrantyDetailsDTO);
        return ResponseEntity.created(new URI("/api/article-warranty-details/" + articleWarrantyDetailsDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, articleWarrantyDetailsDTO.getId().toString()))
            .body(articleWarrantyDetailsDTO);
    }

    /**
     * {@code PUT  /article-warranty-details/:id} : Updates an existing articleWarrantyDetails.
     *
     * @param id the id of the articleWarrantyDetailsDTO to save.
     * @param articleWarrantyDetailsDTO the articleWarrantyDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleWarrantyDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the articleWarrantyDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articleWarrantyDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArticleWarrantyDetailsDTO> updateArticleWarrantyDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ArticleWarrantyDetails : {}, {}", id, articleWarrantyDetailsDTO);
        if (articleWarrantyDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articleWarrantyDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articleWarrantyDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        articleWarrantyDetailsDTO = articleWarrantyDetailsService.update(articleWarrantyDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articleWarrantyDetailsDTO.getId().toString()))
            .body(articleWarrantyDetailsDTO);
    }

    /**
     * {@code PATCH  /article-warranty-details/:id} : Partial updates given fields of an existing articleWarrantyDetails, field will ignore if it is null
     *
     * @param id the id of the articleWarrantyDetailsDTO to save.
     * @param articleWarrantyDetailsDTO the articleWarrantyDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleWarrantyDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the articleWarrantyDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the articleWarrantyDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the articleWarrantyDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArticleWarrantyDetailsDTO> partialUpdateArticleWarrantyDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ArticleWarrantyDetails partially : {}, {}", id, articleWarrantyDetailsDTO);
        if (articleWarrantyDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articleWarrantyDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articleWarrantyDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArticleWarrantyDetailsDTO> result = articleWarrantyDetailsService.partialUpdate(articleWarrantyDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articleWarrantyDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /article-warranty-details} : get all the articleWarrantyDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articleWarrantyDetails in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ArticleWarrantyDetailsDTO>> getAllArticleWarrantyDetails(
        ArticleWarrantyDetailsCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get ArticleWarrantyDetails by criteria: {}", criteria);

        Page<ArticleWarrantyDetailsDTO> page = articleWarrantyDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /article-warranty-details/count} : count all the articleWarrantyDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countArticleWarrantyDetails(ArticleWarrantyDetailsCriteria criteria) {
        LOG.debug("REST request to count ArticleWarrantyDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(articleWarrantyDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /article-warranty-details/:id} : get the "id" articleWarrantyDetails.
     *
     * @param id the id of the articleWarrantyDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articleWarrantyDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleWarrantyDetailsDTO> getArticleWarrantyDetails(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ArticleWarrantyDetails : {}", id);
        Optional<ArticleWarrantyDetailsDTO> articleWarrantyDetailsDTO = articleWarrantyDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(articleWarrantyDetailsDTO);
    }

    /**
     * {@code DELETE  /article-warranty-details/:id} : delete the "id" articleWarrantyDetails.
     *
     * @param id the id of the articleWarrantyDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticleWarrantyDetails(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ArticleWarrantyDetails : {}", id);
        articleWarrantyDetailsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
