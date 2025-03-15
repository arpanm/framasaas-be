package com.framasaas.be.web.rest;

import com.framasaas.be.domain.SupportingDocument;
import com.framasaas.be.repository.SupportingDocumentRepository;
import com.framasaas.be.service.SupportingDocumentService;
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
 * REST controller for managing {@link com.framasaas.be.domain.SupportingDocument}.
 */
@RestController
@RequestMapping("/api/supporting-documents")
public class SupportingDocumentResource {

    private static final Logger LOG = LoggerFactory.getLogger(SupportingDocumentResource.class);

    private static final String ENTITY_NAME = "supportingDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupportingDocumentService supportingDocumentService;

    private final SupportingDocumentRepository supportingDocumentRepository;

    public SupportingDocumentResource(
        SupportingDocumentService supportingDocumentService,
        SupportingDocumentRepository supportingDocumentRepository
    ) {
        this.supportingDocumentService = supportingDocumentService;
        this.supportingDocumentRepository = supportingDocumentRepository;
    }

    /**
     * {@code POST  /supporting-documents} : Create a new supportingDocument.
     *
     * @param supportingDocument the supportingDocument to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supportingDocument, or with status {@code 400 (Bad Request)} if the supportingDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SupportingDocument> createSupportingDocument(@Valid @RequestBody SupportingDocument supportingDocument)
        throws URISyntaxException {
        LOG.debug("REST request to save SupportingDocument : {}", supportingDocument);
        if (supportingDocument.getId() != null) {
            throw new BadRequestAlertException("A new supportingDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        supportingDocument = supportingDocumentService.save(supportingDocument);
        return ResponseEntity.created(new URI("/api/supporting-documents/" + supportingDocument.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, supportingDocument.getId().toString()))
            .body(supportingDocument);
    }

    /**
     * {@code PUT  /supporting-documents/:id} : Updates an existing supportingDocument.
     *
     * @param id the id of the supportingDocument to save.
     * @param supportingDocument the supportingDocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supportingDocument,
     * or with status {@code 400 (Bad Request)} if the supportingDocument is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supportingDocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SupportingDocument> updateSupportingDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SupportingDocument supportingDocument
    ) throws URISyntaxException {
        LOG.debug("REST request to update SupportingDocument : {}, {}", id, supportingDocument);
        if (supportingDocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, supportingDocument.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!supportingDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        supportingDocument = supportingDocumentService.update(supportingDocument);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supportingDocument.getId().toString()))
            .body(supportingDocument);
    }

    /**
     * {@code PATCH  /supporting-documents/:id} : Partial updates given fields of an existing supportingDocument, field will ignore if it is null
     *
     * @param id the id of the supportingDocument to save.
     * @param supportingDocument the supportingDocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supportingDocument,
     * or with status {@code 400 (Bad Request)} if the supportingDocument is not valid,
     * or with status {@code 404 (Not Found)} if the supportingDocument is not found,
     * or with status {@code 500 (Internal Server Error)} if the supportingDocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SupportingDocument> partialUpdateSupportingDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SupportingDocument supportingDocument
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SupportingDocument partially : {}, {}", id, supportingDocument);
        if (supportingDocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, supportingDocument.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!supportingDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SupportingDocument> result = supportingDocumentService.partialUpdate(supportingDocument);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supportingDocument.getId().toString())
        );
    }

    /**
     * {@code GET  /supporting-documents} : get all the supportingDocuments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supportingDocuments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SupportingDocument>> getAllSupportingDocuments(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of SupportingDocuments");
        Page<SupportingDocument> page = supportingDocumentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /supporting-documents/:id} : get the "id" supportingDocument.
     *
     * @param id the id of the supportingDocument to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supportingDocument, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SupportingDocument> getSupportingDocument(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SupportingDocument : {}", id);
        Optional<SupportingDocument> supportingDocument = supportingDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supportingDocument);
    }

    /**
     * {@code DELETE  /supporting-documents/:id} : delete the "id" supportingDocument.
     *
     * @param id the id of the supportingDocument to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupportingDocument(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SupportingDocument : {}", id);
        supportingDocumentService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
