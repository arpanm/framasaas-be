package com.framasaas.web.rest;

import com.framasaas.domain.ArticleWarrantyDetailsDocument;
import com.framasaas.repository.ArticleWarrantyDetailsDocumentRepository;
import com.framasaas.service.ArticleWarrantyDetailsDocumentService;
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
 * REST controller for managing {@link com.framasaas.domain.ArticleWarrantyDetailsDocument}.
 */
@RestController
@RequestMapping("/api/article-warranty-details-documents")
public class ArticleWarrantyDetailsDocumentResource {

    private static final Logger LOG = LoggerFactory.getLogger(ArticleWarrantyDetailsDocumentResource.class);

    private static final String ENTITY_NAME = "articleWarrantyDetailsDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticleWarrantyDetailsDocumentService articleWarrantyDetailsDocumentService;

    private final ArticleWarrantyDetailsDocumentRepository articleWarrantyDetailsDocumentRepository;

    public ArticleWarrantyDetailsDocumentResource(
        ArticleWarrantyDetailsDocumentService articleWarrantyDetailsDocumentService,
        ArticleWarrantyDetailsDocumentRepository articleWarrantyDetailsDocumentRepository
    ) {
        this.articleWarrantyDetailsDocumentService = articleWarrantyDetailsDocumentService;
        this.articleWarrantyDetailsDocumentRepository = articleWarrantyDetailsDocumentRepository;
    }

    /**
     * {@code POST  /article-warranty-details-documents} : Create a new articleWarrantyDetailsDocument.
     *
     * @param articleWarrantyDetailsDocument the articleWarrantyDetailsDocument to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articleWarrantyDetailsDocument, or with status {@code 400 (Bad Request)} if the articleWarrantyDetailsDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ArticleWarrantyDetailsDocument> createArticleWarrantyDetailsDocument(
        @Valid @RequestBody ArticleWarrantyDetailsDocument articleWarrantyDetailsDocument
    ) throws URISyntaxException {
        LOG.debug("REST request to save ArticleWarrantyDetailsDocument : {}", articleWarrantyDetailsDocument);
        if (articleWarrantyDetailsDocument.getId() != null) {
            throw new BadRequestAlertException("A new articleWarrantyDetailsDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        articleWarrantyDetailsDocument = articleWarrantyDetailsDocumentService.save(articleWarrantyDetailsDocument);
        return ResponseEntity.created(new URI("/api/article-warranty-details-documents/" + articleWarrantyDetailsDocument.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, articleWarrantyDetailsDocument.getId().toString())
            )
            .body(articleWarrantyDetailsDocument);
    }

    /**
     * {@code PUT  /article-warranty-details-documents/:id} : Updates an existing articleWarrantyDetailsDocument.
     *
     * @param id the id of the articleWarrantyDetailsDocument to save.
     * @param articleWarrantyDetailsDocument the articleWarrantyDetailsDocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleWarrantyDetailsDocument,
     * or with status {@code 400 (Bad Request)} if the articleWarrantyDetailsDocument is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articleWarrantyDetailsDocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArticleWarrantyDetailsDocument> updateArticleWarrantyDetailsDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ArticleWarrantyDetailsDocument articleWarrantyDetailsDocument
    ) throws URISyntaxException {
        LOG.debug("REST request to update ArticleWarrantyDetailsDocument : {}, {}", id, articleWarrantyDetailsDocument);
        if (articleWarrantyDetailsDocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articleWarrantyDetailsDocument.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articleWarrantyDetailsDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        articleWarrantyDetailsDocument = articleWarrantyDetailsDocumentService.update(articleWarrantyDetailsDocument);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articleWarrantyDetailsDocument.getId().toString())
            )
            .body(articleWarrantyDetailsDocument);
    }

    /**
     * {@code PATCH  /article-warranty-details-documents/:id} : Partial updates given fields of an existing articleWarrantyDetailsDocument, field will ignore if it is null
     *
     * @param id the id of the articleWarrantyDetailsDocument to save.
     * @param articleWarrantyDetailsDocument the articleWarrantyDetailsDocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleWarrantyDetailsDocument,
     * or with status {@code 400 (Bad Request)} if the articleWarrantyDetailsDocument is not valid,
     * or with status {@code 404 (Not Found)} if the articleWarrantyDetailsDocument is not found,
     * or with status {@code 500 (Internal Server Error)} if the articleWarrantyDetailsDocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArticleWarrantyDetailsDocument> partialUpdateArticleWarrantyDetailsDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ArticleWarrantyDetailsDocument articleWarrantyDetailsDocument
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ArticleWarrantyDetailsDocument partially : {}, {}", id, articleWarrantyDetailsDocument);
        if (articleWarrantyDetailsDocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articleWarrantyDetailsDocument.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articleWarrantyDetailsDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArticleWarrantyDetailsDocument> result = articleWarrantyDetailsDocumentService.partialUpdate(
            articleWarrantyDetailsDocument
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articleWarrantyDetailsDocument.getId().toString())
        );
    }

    /**
     * {@code GET  /article-warranty-details-documents} : get all the articleWarrantyDetailsDocuments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articleWarrantyDetailsDocuments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ArticleWarrantyDetailsDocument>> getAllArticleWarrantyDetailsDocuments(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of ArticleWarrantyDetailsDocuments");
        Page<ArticleWarrantyDetailsDocument> page = articleWarrantyDetailsDocumentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /article-warranty-details-documents/:id} : get the "id" articleWarrantyDetailsDocument.
     *
     * @param id the id of the articleWarrantyDetailsDocument to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articleWarrantyDetailsDocument, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleWarrantyDetailsDocument> getArticleWarrantyDetailsDocument(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ArticleWarrantyDetailsDocument : {}", id);
        Optional<ArticleWarrantyDetailsDocument> articleWarrantyDetailsDocument = articleWarrantyDetailsDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(articleWarrantyDetailsDocument);
    }

    /**
     * {@code DELETE  /article-warranty-details-documents/:id} : delete the "id" articleWarrantyDetailsDocument.
     *
     * @param id the id of the articleWarrantyDetailsDocument to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticleWarrantyDetailsDocument(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ArticleWarrantyDetailsDocument : {}", id);
        articleWarrantyDetailsDocumentService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
