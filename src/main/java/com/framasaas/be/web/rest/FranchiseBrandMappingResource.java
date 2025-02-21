package com.framasaas.be.web.rest;

import com.framasaas.be.domain.FranchiseBrandMapping;
import com.framasaas.be.repository.FranchiseBrandMappingRepository;
import com.framasaas.be.service.FranchiseBrandMappingService;
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
 * REST controller for managing {@link com.framasaas.be.domain.FranchiseBrandMapping}.
 */
@RestController
@RequestMapping("/api/franchise-brand-mappings")
public class FranchiseBrandMappingResource {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseBrandMappingResource.class);

    private static final String ENTITY_NAME = "franchiseBrandMapping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FranchiseBrandMappingService franchiseBrandMappingService;

    private final FranchiseBrandMappingRepository franchiseBrandMappingRepository;

    public FranchiseBrandMappingResource(
        FranchiseBrandMappingService franchiseBrandMappingService,
        FranchiseBrandMappingRepository franchiseBrandMappingRepository
    ) {
        this.franchiseBrandMappingService = franchiseBrandMappingService;
        this.franchiseBrandMappingRepository = franchiseBrandMappingRepository;
    }

    /**
     * {@code POST  /franchise-brand-mappings} : Create a new franchiseBrandMapping.
     *
     * @param franchiseBrandMapping the franchiseBrandMapping to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new franchiseBrandMapping, or with status {@code 400 (Bad Request)} if the franchiseBrandMapping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FranchiseBrandMapping> createFranchiseBrandMapping(
        @Valid @RequestBody FranchiseBrandMapping franchiseBrandMapping
    ) throws URISyntaxException {
        LOG.debug("REST request to save FranchiseBrandMapping : {}", franchiseBrandMapping);
        if (franchiseBrandMapping.getId() != null) {
            throw new BadRequestAlertException("A new franchiseBrandMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        franchiseBrandMapping = franchiseBrandMappingService.save(franchiseBrandMapping);
        return ResponseEntity.created(new URI("/api/franchise-brand-mappings/" + franchiseBrandMapping.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, franchiseBrandMapping.getId().toString()))
            .body(franchiseBrandMapping);
    }

    /**
     * {@code PUT  /franchise-brand-mappings/:id} : Updates an existing franchiseBrandMapping.
     *
     * @param id the id of the franchiseBrandMapping to save.
     * @param franchiseBrandMapping the franchiseBrandMapping to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseBrandMapping,
     * or with status {@code 400 (Bad Request)} if the franchiseBrandMapping is not valid,
     * or with status {@code 500 (Internal Server Error)} if the franchiseBrandMapping couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FranchiseBrandMapping> updateFranchiseBrandMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FranchiseBrandMapping franchiseBrandMapping
    ) throws URISyntaxException {
        LOG.debug("REST request to update FranchiseBrandMapping : {}, {}", id, franchiseBrandMapping);
        if (franchiseBrandMapping.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseBrandMapping.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseBrandMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        franchiseBrandMapping = franchiseBrandMappingService.update(franchiseBrandMapping);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseBrandMapping.getId().toString()))
            .body(franchiseBrandMapping);
    }

    /**
     * {@code PATCH  /franchise-brand-mappings/:id} : Partial updates given fields of an existing franchiseBrandMapping, field will ignore if it is null
     *
     * @param id the id of the franchiseBrandMapping to save.
     * @param franchiseBrandMapping the franchiseBrandMapping to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseBrandMapping,
     * or with status {@code 400 (Bad Request)} if the franchiseBrandMapping is not valid,
     * or with status {@code 404 (Not Found)} if the franchiseBrandMapping is not found,
     * or with status {@code 500 (Internal Server Error)} if the franchiseBrandMapping couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FranchiseBrandMapping> partialUpdateFranchiseBrandMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FranchiseBrandMapping franchiseBrandMapping
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FranchiseBrandMapping partially : {}, {}", id, franchiseBrandMapping);
        if (franchiseBrandMapping.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseBrandMapping.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseBrandMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FranchiseBrandMapping> result = franchiseBrandMappingService.partialUpdate(franchiseBrandMapping);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseBrandMapping.getId().toString())
        );
    }

    /**
     * {@code GET  /franchise-brand-mappings} : get all the franchiseBrandMappings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of franchiseBrandMappings in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FranchiseBrandMapping>> getAllFranchiseBrandMappings(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of FranchiseBrandMappings");
        Page<FranchiseBrandMapping> page = franchiseBrandMappingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /franchise-brand-mappings/:id} : get the "id" franchiseBrandMapping.
     *
     * @param id the id of the franchiseBrandMapping to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the franchiseBrandMapping, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FranchiseBrandMapping> getFranchiseBrandMapping(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FranchiseBrandMapping : {}", id);
        Optional<FranchiseBrandMapping> franchiseBrandMapping = franchiseBrandMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(franchiseBrandMapping);
    }

    /**
     * {@code DELETE  /franchise-brand-mappings/:id} : delete the "id" franchiseBrandMapping.
     *
     * @param id the id of the franchiseBrandMapping to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFranchiseBrandMapping(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FranchiseBrandMapping : {}", id);
        franchiseBrandMappingService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
