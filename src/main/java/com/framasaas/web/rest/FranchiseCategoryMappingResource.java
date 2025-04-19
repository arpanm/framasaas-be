package com.framasaas.web.rest;

import com.framasaas.domain.FranchiseCategoryMapping;
import com.framasaas.repository.FranchiseCategoryMappingRepository;
import com.framasaas.service.FranchiseCategoryMappingService;
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
 * REST controller for managing {@link com.framasaas.domain.FranchiseCategoryMapping}.
 */
@RestController
@RequestMapping("/api/franchise-category-mappings")
public class FranchiseCategoryMappingResource {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseCategoryMappingResource.class);

    private static final String ENTITY_NAME = "franchiseCategoryMapping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FranchiseCategoryMappingService franchiseCategoryMappingService;

    private final FranchiseCategoryMappingRepository franchiseCategoryMappingRepository;

    public FranchiseCategoryMappingResource(
        FranchiseCategoryMappingService franchiseCategoryMappingService,
        FranchiseCategoryMappingRepository franchiseCategoryMappingRepository
    ) {
        this.franchiseCategoryMappingService = franchiseCategoryMappingService;
        this.franchiseCategoryMappingRepository = franchiseCategoryMappingRepository;
    }

    /**
     * {@code POST  /franchise-category-mappings} : Create a new franchiseCategoryMapping.
     *
     * @param franchiseCategoryMapping the franchiseCategoryMapping to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new franchiseCategoryMapping, or with status {@code 400 (Bad Request)} if the franchiseCategoryMapping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FranchiseCategoryMapping> createFranchiseCategoryMapping(
        @Valid @RequestBody FranchiseCategoryMapping franchiseCategoryMapping
    ) throws URISyntaxException {
        LOG.debug("REST request to save FranchiseCategoryMapping : {}", franchiseCategoryMapping);
        if (franchiseCategoryMapping.getId() != null) {
            throw new BadRequestAlertException("A new franchiseCategoryMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        franchiseCategoryMapping = franchiseCategoryMappingService.save(franchiseCategoryMapping);
        return ResponseEntity.created(new URI("/api/franchise-category-mappings/" + franchiseCategoryMapping.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, franchiseCategoryMapping.getId().toString()))
            .body(franchiseCategoryMapping);
    }

    /**
     * {@code PUT  /franchise-category-mappings/:id} : Updates an existing franchiseCategoryMapping.
     *
     * @param id the id of the franchiseCategoryMapping to save.
     * @param franchiseCategoryMapping the franchiseCategoryMapping to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseCategoryMapping,
     * or with status {@code 400 (Bad Request)} if the franchiseCategoryMapping is not valid,
     * or with status {@code 500 (Internal Server Error)} if the franchiseCategoryMapping couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FranchiseCategoryMapping> updateFranchiseCategoryMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FranchiseCategoryMapping franchiseCategoryMapping
    ) throws URISyntaxException {
        LOG.debug("REST request to update FranchiseCategoryMapping : {}, {}", id, franchiseCategoryMapping);
        if (franchiseCategoryMapping.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseCategoryMapping.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseCategoryMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        franchiseCategoryMapping = franchiseCategoryMappingService.update(franchiseCategoryMapping);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseCategoryMapping.getId().toString()))
            .body(franchiseCategoryMapping);
    }

    /**
     * {@code PATCH  /franchise-category-mappings/:id} : Partial updates given fields of an existing franchiseCategoryMapping, field will ignore if it is null
     *
     * @param id the id of the franchiseCategoryMapping to save.
     * @param franchiseCategoryMapping the franchiseCategoryMapping to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseCategoryMapping,
     * or with status {@code 400 (Bad Request)} if the franchiseCategoryMapping is not valid,
     * or with status {@code 404 (Not Found)} if the franchiseCategoryMapping is not found,
     * or with status {@code 500 (Internal Server Error)} if the franchiseCategoryMapping couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FranchiseCategoryMapping> partialUpdateFranchiseCategoryMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FranchiseCategoryMapping franchiseCategoryMapping
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FranchiseCategoryMapping partially : {}, {}", id, franchiseCategoryMapping);
        if (franchiseCategoryMapping.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseCategoryMapping.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseCategoryMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FranchiseCategoryMapping> result = franchiseCategoryMappingService.partialUpdate(franchiseCategoryMapping);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseCategoryMapping.getId().toString())
        );
    }

    /**
     * {@code GET  /franchise-category-mappings} : get all the franchiseCategoryMappings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of franchiseCategoryMappings in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FranchiseCategoryMapping>> getAllFranchiseCategoryMappings(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of FranchiseCategoryMappings");
        Page<FranchiseCategoryMapping> page = franchiseCategoryMappingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /franchise-category-mappings/:id} : get the "id" franchiseCategoryMapping.
     *
     * @param id the id of the franchiseCategoryMapping to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the franchiseCategoryMapping, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FranchiseCategoryMapping> getFranchiseCategoryMapping(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FranchiseCategoryMapping : {}", id);
        Optional<FranchiseCategoryMapping> franchiseCategoryMapping = franchiseCategoryMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(franchiseCategoryMapping);
    }

    /**
     * {@code DELETE  /franchise-category-mappings/:id} : delete the "id" franchiseCategoryMapping.
     *
     * @param id the id of the franchiseCategoryMapping to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFranchiseCategoryMapping(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FranchiseCategoryMapping : {}", id);
        franchiseCategoryMappingService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
