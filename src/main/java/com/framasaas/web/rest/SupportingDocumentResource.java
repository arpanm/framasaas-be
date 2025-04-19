package com.framasaas.web.rest;

import com.framasaas.repository.SupportingDocumentRepository;
import com.framasaas.service.SupportingDocumentQueryService;
import com.framasaas.service.SupportingDocumentService;
import com.framasaas.service.criteria.SupportingDocumentCriteria;
import com.framasaas.service.dto.SupportingDocumentDTO;
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
 * REST controller for managing {@link com.framasaas.domain.SupportingDocument}.
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

    private final SupportingDocumentQueryService supportingDocumentQueryService;

    public SupportingDocumentResource(
        SupportingDocumentService supportingDocumentService,
        SupportingDocumentRepository supportingDocumentRepository,
        SupportingDocumentQueryService supportingDocumentQueryService
    ) {
        this.supportingDocumentService = supportingDocumentService;
        this.supportingDocumentRepository = supportingDocumentRepository;
        this.supportingDocumentQueryService = supportingDocumentQueryService;
    }

    /**
     * {@code POST  /supporting-documents} : Create a new supportingDocument.
     *
     * @param supportingDocumentDTO the supportingDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supportingDocumentDTO, or with status {@code 400 (Bad Request)} if the supportingDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SupportingDocumentDTO> createSupportingDocument(@Valid @RequestBody SupportingDocumentDTO supportingDocumentDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SupportingDocument : {}", supportingDocumentDTO);
        if (supportingDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new supportingDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        supportingDocumentDTO = supportingDocumentService.save(supportingDocumentDTO);
        return ResponseEntity.created(new URI("/api/supporting-documents/" + supportingDocumentDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, supportingDocumentDTO.getId().toString()))
            .body(supportingDocumentDTO);
    }

    /**
     * {@code PUT  /supporting-documents/:id} : Updates an existing supportingDocument.
     *
     * @param id the id of the supportingDocumentDTO to save.
     * @param supportingDocumentDTO the supportingDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supportingDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the supportingDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supportingDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SupportingDocumentDTO> updateSupportingDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SupportingDocumentDTO supportingDocumentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SupportingDocument : {}, {}", id, supportingDocumentDTO);
        if (supportingDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, supportingDocumentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!supportingDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        supportingDocumentDTO = supportingDocumentService.update(supportingDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supportingDocumentDTO.getId().toString()))
            .body(supportingDocumentDTO);
    }

    /**
     * {@code PATCH  /supporting-documents/:id} : Partial updates given fields of an existing supportingDocument, field will ignore if it is null
     *
     * @param id the id of the supportingDocumentDTO to save.
     * @param supportingDocumentDTO the supportingDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supportingDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the supportingDocumentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the supportingDocumentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the supportingDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SupportingDocumentDTO> partialUpdateSupportingDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SupportingDocumentDTO supportingDocumentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SupportingDocument partially : {}, {}", id, supportingDocumentDTO);
        if (supportingDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, supportingDocumentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!supportingDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SupportingDocumentDTO> result = supportingDocumentService.partialUpdate(supportingDocumentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supportingDocumentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /supporting-documents} : get all the supportingDocuments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supportingDocuments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SupportingDocumentDTO>> getAllSupportingDocuments(
        SupportingDocumentCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SupportingDocuments by criteria: {}", criteria);

        Page<SupportingDocumentDTO> page = supportingDocumentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /supporting-documents/count} : count all the supportingDocuments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSupportingDocuments(SupportingDocumentCriteria criteria) {
        LOG.debug("REST request to count SupportingDocuments by criteria: {}", criteria);
        return ResponseEntity.ok().body(supportingDocumentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /supporting-documents/:id} : get the "id" supportingDocument.
     *
     * @param id the id of the supportingDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supportingDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SupportingDocumentDTO> getSupportingDocument(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SupportingDocument : {}", id);
        Optional<SupportingDocumentDTO> supportingDocumentDTO = supportingDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supportingDocumentDTO);
    }

    /**
     * {@code DELETE  /supporting-documents/:id} : delete the "id" supportingDocument.
     *
     * @param id the id of the supportingDocumentDTO to delete.
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
