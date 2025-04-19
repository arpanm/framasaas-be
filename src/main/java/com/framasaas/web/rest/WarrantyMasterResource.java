package com.framasaas.web.rest;

import com.framasaas.repository.WarrantyMasterRepository;
import com.framasaas.service.WarrantyMasterQueryService;
import com.framasaas.service.WarrantyMasterService;
import com.framasaas.service.criteria.WarrantyMasterCriteria;
import com.framasaas.service.dto.WarrantyMasterDTO;
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
 * REST controller for managing {@link com.framasaas.domain.WarrantyMaster}.
 */
@RestController
@RequestMapping("/api/warranty-masters")
public class WarrantyMasterResource {

    private static final Logger LOG = LoggerFactory.getLogger(WarrantyMasterResource.class);

    private static final String ENTITY_NAME = "warrantyMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WarrantyMasterService warrantyMasterService;

    private final WarrantyMasterRepository warrantyMasterRepository;

    private final WarrantyMasterQueryService warrantyMasterQueryService;

    public WarrantyMasterResource(
        WarrantyMasterService warrantyMasterService,
        WarrantyMasterRepository warrantyMasterRepository,
        WarrantyMasterQueryService warrantyMasterQueryService
    ) {
        this.warrantyMasterService = warrantyMasterService;
        this.warrantyMasterRepository = warrantyMasterRepository;
        this.warrantyMasterQueryService = warrantyMasterQueryService;
    }

    /**
     * {@code POST  /warranty-masters} : Create a new warrantyMaster.
     *
     * @param warrantyMasterDTO the warrantyMasterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new warrantyMasterDTO, or with status {@code 400 (Bad Request)} if the warrantyMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<WarrantyMasterDTO> createWarrantyMaster(@Valid @RequestBody WarrantyMasterDTO warrantyMasterDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save WarrantyMaster : {}", warrantyMasterDTO);
        if (warrantyMasterDTO.getId() != null) {
            throw new BadRequestAlertException("A new warrantyMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        warrantyMasterDTO = warrantyMasterService.save(warrantyMasterDTO);
        return ResponseEntity.created(new URI("/api/warranty-masters/" + warrantyMasterDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, warrantyMasterDTO.getId().toString()))
            .body(warrantyMasterDTO);
    }

    /**
     * {@code PUT  /warranty-masters/:id} : Updates an existing warrantyMaster.
     *
     * @param id the id of the warrantyMasterDTO to save.
     * @param warrantyMasterDTO the warrantyMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated warrantyMasterDTO,
     * or with status {@code 400 (Bad Request)} if the warrantyMasterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the warrantyMasterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<WarrantyMasterDTO> updateWarrantyMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WarrantyMasterDTO warrantyMasterDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update WarrantyMaster : {}, {}", id, warrantyMasterDTO);
        if (warrantyMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, warrantyMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!warrantyMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        warrantyMasterDTO = warrantyMasterService.update(warrantyMasterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, warrantyMasterDTO.getId().toString()))
            .body(warrantyMasterDTO);
    }

    /**
     * {@code PATCH  /warranty-masters/:id} : Partial updates given fields of an existing warrantyMaster, field will ignore if it is null
     *
     * @param id the id of the warrantyMasterDTO to save.
     * @param warrantyMasterDTO the warrantyMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated warrantyMasterDTO,
     * or with status {@code 400 (Bad Request)} if the warrantyMasterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the warrantyMasterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the warrantyMasterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WarrantyMasterDTO> partialUpdateWarrantyMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WarrantyMasterDTO warrantyMasterDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update WarrantyMaster partially : {}, {}", id, warrantyMasterDTO);
        if (warrantyMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, warrantyMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!warrantyMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WarrantyMasterDTO> result = warrantyMasterService.partialUpdate(warrantyMasterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, warrantyMasterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /warranty-masters} : get all the warrantyMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of warrantyMasters in body.
     */
    @GetMapping("")
    public ResponseEntity<List<WarrantyMasterDTO>> getAllWarrantyMasters(
        WarrantyMasterCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get WarrantyMasters by criteria: {}", criteria);

        Page<WarrantyMasterDTO> page = warrantyMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /warranty-masters/count} : count all the warrantyMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countWarrantyMasters(WarrantyMasterCriteria criteria) {
        LOG.debug("REST request to count WarrantyMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(warrantyMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /warranty-masters/:id} : get the "id" warrantyMaster.
     *
     * @param id the id of the warrantyMasterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the warrantyMasterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<WarrantyMasterDTO> getWarrantyMaster(@PathVariable("id") Long id) {
        LOG.debug("REST request to get WarrantyMaster : {}", id);
        Optional<WarrantyMasterDTO> warrantyMasterDTO = warrantyMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(warrantyMasterDTO);
    }

    /**
     * {@code DELETE  /warranty-masters/:id} : delete the "id" warrantyMaster.
     *
     * @param id the id of the warrantyMasterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarrantyMaster(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete WarrantyMaster : {}", id);
        warrantyMasterService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
