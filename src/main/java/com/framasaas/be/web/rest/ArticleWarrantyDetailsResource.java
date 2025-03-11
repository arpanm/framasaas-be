package com.framasaas.be.web.rest;

import com.framasaas.be.domain.ArticleWarrantyDetails;
import com.framasaas.be.repository.ArticleWarrantyDetailsRepository;
import com.framasaas.be.service.ArticleWarrantyDetailsService;
import com.framasaas.be.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.framasaas.be.domain.ArticleWarrantyDetails}.
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

    public ArticleWarrantyDetailsResource(
        ArticleWarrantyDetailsService articleWarrantyDetailsService,
        ArticleWarrantyDetailsRepository articleWarrantyDetailsRepository
    ) {
        this.articleWarrantyDetailsService = articleWarrantyDetailsService;
        this.articleWarrantyDetailsRepository = articleWarrantyDetailsRepository;
    }

    /**
     * {@code POST  /article-warranty-details} : Create a new articleWarrantyDetails.
     *
     * @param articleWarrantyDetails the articleWarrantyDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articleWarrantyDetails, or with status {@code 400 (Bad Request)} if the articleWarrantyDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ArticleWarrantyDetails> createArticleWarrantyDetails(
        @Valid @RequestBody ArticleWarrantyDetails articleWarrantyDetails
    ) throws URISyntaxException {
        LOG.debug("REST request to save ArticleWarrantyDetails : {}", articleWarrantyDetails);
        if (articleWarrantyDetails.getId() != null) {
            throw new BadRequestAlertException("A new articleWarrantyDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        articleWarrantyDetails = articleWarrantyDetailsService.save(articleWarrantyDetails);
        return ResponseEntity.created(new URI("/api/article-warranty-details/" + articleWarrantyDetails.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, articleWarrantyDetails.getId().toString()))
            .body(articleWarrantyDetails);
    }

    /**
     * {@code PUT  /article-warranty-details/:id} : Updates an existing articleWarrantyDetails.
     *
     * @param id the id of the articleWarrantyDetails to save.
     * @param articleWarrantyDetails the articleWarrantyDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleWarrantyDetails,
     * or with status {@code 400 (Bad Request)} if the articleWarrantyDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articleWarrantyDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArticleWarrantyDetails> updateArticleWarrantyDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ArticleWarrantyDetails articleWarrantyDetails
    ) throws URISyntaxException {
        LOG.debug("REST request to update ArticleWarrantyDetails : {}, {}", id, articleWarrantyDetails);
        if (articleWarrantyDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articleWarrantyDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articleWarrantyDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        articleWarrantyDetails = articleWarrantyDetailsService.update(articleWarrantyDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articleWarrantyDetails.getId().toString()))
            .body(articleWarrantyDetails);
    }

    /**
     * {@code PATCH  /article-warranty-details/:id} : Partial updates given fields of an existing articleWarrantyDetails, field will ignore if it is null
     *
     * @param id the id of the articleWarrantyDetails to save.
     * @param articleWarrantyDetails the articleWarrantyDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleWarrantyDetails,
     * or with status {@code 400 (Bad Request)} if the articleWarrantyDetails is not valid,
     * or with status {@code 404 (Not Found)} if the articleWarrantyDetails is not found,
     * or with status {@code 500 (Internal Server Error)} if the articleWarrantyDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArticleWarrantyDetails> partialUpdateArticleWarrantyDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ArticleWarrantyDetails articleWarrantyDetails
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ArticleWarrantyDetails partially : {}, {}", id, articleWarrantyDetails);
        if (articleWarrantyDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articleWarrantyDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articleWarrantyDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArticleWarrantyDetails> result = articleWarrantyDetailsService.partialUpdate(articleWarrantyDetails);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articleWarrantyDetails.getId().toString())
        );
    }

    /**
     * {@code GET  /article-warranty-details} : get all the articleWarrantyDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articleWarrantyDetails in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ArticleWarrantyDetails>> getAllArticleWarrantyDetails(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of ArticleWarrantyDetails");
        Page<ArticleWarrantyDetails> page = articleWarrantyDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /article-warranty-details/:id} : get the "id" articleWarrantyDetails.
     *
     * @param id the id of the articleWarrantyDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articleWarrantyDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleWarrantyDetails> getArticleWarrantyDetails(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ArticleWarrantyDetails : {}", id);
        Optional<ArticleWarrantyDetails> articleWarrantyDetails = articleWarrantyDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(articleWarrantyDetails);
    }

    /**
     * {@code DELETE  /article-warranty-details/:id} : delete the "id" articleWarrantyDetails.
     *
     * @param id the id of the articleWarrantyDetails to delete.
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
