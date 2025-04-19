package com.framasaas.web.rest;

import com.framasaas.domain.FranchiseDocument;
import com.framasaas.repository.FranchiseDocumentRepository;
import com.framasaas.service.FranchiseDocumentService;
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
 * REST controller for managing {@link com.framasaas.domain.FranchiseDocument}.
 */
@RestController
@RequestMapping("/api/franchise-documents")
public class FranchiseDocumentResource {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseDocumentResource.class);

    private static final String ENTITY_NAME = "franchiseDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FranchiseDocumentService franchiseDocumentService;

    private final FranchiseDocumentRepository franchiseDocumentRepository;

    public FranchiseDocumentResource(
        FranchiseDocumentService franchiseDocumentService,
        FranchiseDocumentRepository franchiseDocumentRepository
    ) {
        this.franchiseDocumentService = franchiseDocumentService;
        this.franchiseDocumentRepository = franchiseDocumentRepository;
    }

    /**
     * {@code POST  /franchise-documents} : Create a new franchiseDocument.
     *
     * @param franchiseDocument the franchiseDocument to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new franchiseDocument, or with status {@code 400 (Bad Request)} if the franchiseDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FranchiseDocument> createFranchiseDocument(@Valid @RequestBody FranchiseDocument franchiseDocument)
        throws URISyntaxException {
        LOG.debug("REST request to save FranchiseDocument : {}", franchiseDocument);
        if (franchiseDocument.getId() != null) {
            throw new BadRequestAlertException("A new franchiseDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        franchiseDocument = franchiseDocumentService.save(franchiseDocument);
        return ResponseEntity.created(new URI("/api/franchise-documents/" + franchiseDocument.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, franchiseDocument.getId().toString()))
            .body(franchiseDocument);
    }

    /**
     * {@code PUT  /franchise-documents/:id} : Updates an existing franchiseDocument.
     *
     * @param id the id of the franchiseDocument to save.
     * @param franchiseDocument the franchiseDocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseDocument,
     * or with status {@code 400 (Bad Request)} if the franchiseDocument is not valid,
     * or with status {@code 500 (Internal Server Error)} if the franchiseDocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FranchiseDocument> updateFranchiseDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FranchiseDocument franchiseDocument
    ) throws URISyntaxException {
        LOG.debug("REST request to update FranchiseDocument : {}, {}", id, franchiseDocument);
        if (franchiseDocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseDocument.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        franchiseDocument = franchiseDocumentService.update(franchiseDocument);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseDocument.getId().toString()))
            .body(franchiseDocument);
    }

    /**
     * {@code PATCH  /franchise-documents/:id} : Partial updates given fields of an existing franchiseDocument, field will ignore if it is null
     *
     * @param id the id of the franchiseDocument to save.
     * @param franchiseDocument the franchiseDocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseDocument,
     * or with status {@code 400 (Bad Request)} if the franchiseDocument is not valid,
     * or with status {@code 404 (Not Found)} if the franchiseDocument is not found,
     * or with status {@code 500 (Internal Server Error)} if the franchiseDocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FranchiseDocument> partialUpdateFranchiseDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FranchiseDocument franchiseDocument
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FranchiseDocument partially : {}, {}", id, franchiseDocument);
        if (franchiseDocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseDocument.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FranchiseDocument> result = franchiseDocumentService.partialUpdate(franchiseDocument);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseDocument.getId().toString())
        );
    }

    /**
     * {@code GET  /franchise-documents} : get all the franchiseDocuments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of franchiseDocuments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FranchiseDocument>> getAllFranchiseDocuments(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of FranchiseDocuments");
        Page<FranchiseDocument> page = franchiseDocumentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /franchise-documents/:id} : get the "id" franchiseDocument.
     *
     * @param id the id of the franchiseDocument to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the franchiseDocument, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FranchiseDocument> getFranchiseDocument(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FranchiseDocument : {}", id);
        Optional<FranchiseDocument> franchiseDocument = franchiseDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(franchiseDocument);
    }

    /**
     * {@code DELETE  /franchise-documents/:id} : delete the "id" franchiseDocument.
     *
     * @param id the id of the franchiseDocument to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFranchiseDocument(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FranchiseDocument : {}", id);
        franchiseDocumentService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
