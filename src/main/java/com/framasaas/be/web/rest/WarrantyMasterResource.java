package com.framasaas.be.web.rest;

import com.framasaas.be.domain.WarrantyMaster;
import com.framasaas.be.repository.WarrantyMasterRepository;
import com.framasaas.be.service.WarrantyMasterService;
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
 * REST controller for managing {@link com.framasaas.be.domain.WarrantyMaster}.
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

    public WarrantyMasterResource(WarrantyMasterService warrantyMasterService, WarrantyMasterRepository warrantyMasterRepository) {
        this.warrantyMasterService = warrantyMasterService;
        this.warrantyMasterRepository = warrantyMasterRepository;
    }

    /**
     * {@code POST  /warranty-masters} : Create a new warrantyMaster.
     *
     * @param warrantyMaster the warrantyMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new warrantyMaster, or with status {@code 400 (Bad Request)} if the warrantyMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<WarrantyMaster> createWarrantyMaster(@Valid @RequestBody WarrantyMaster warrantyMaster)
        throws URISyntaxException {
        LOG.debug("REST request to save WarrantyMaster : {}", warrantyMaster);
        if (warrantyMaster.getId() != null) {
            throw new BadRequestAlertException("A new warrantyMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        warrantyMaster = warrantyMasterService.save(warrantyMaster);
        return ResponseEntity.created(new URI("/api/warranty-masters/" + warrantyMaster.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, warrantyMaster.getId().toString()))
            .body(warrantyMaster);
    }

    /**
     * {@code PUT  /warranty-masters/:id} : Updates an existing warrantyMaster.
     *
     * @param id the id of the warrantyMaster to save.
     * @param warrantyMaster the warrantyMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated warrantyMaster,
     * or with status {@code 400 (Bad Request)} if the warrantyMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the warrantyMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<WarrantyMaster> updateWarrantyMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WarrantyMaster warrantyMaster
    ) throws URISyntaxException {
        LOG.debug("REST request to update WarrantyMaster : {}, {}", id, warrantyMaster);
        if (warrantyMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, warrantyMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!warrantyMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        warrantyMaster = warrantyMasterService.update(warrantyMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, warrantyMaster.getId().toString()))
            .body(warrantyMaster);
    }

    /**
     * {@code PATCH  /warranty-masters/:id} : Partial updates given fields of an existing warrantyMaster, field will ignore if it is null
     *
     * @param id the id of the warrantyMaster to save.
     * @param warrantyMaster the warrantyMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated warrantyMaster,
     * or with status {@code 400 (Bad Request)} if the warrantyMaster is not valid,
     * or with status {@code 404 (Not Found)} if the warrantyMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the warrantyMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WarrantyMaster> partialUpdateWarrantyMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WarrantyMaster warrantyMaster
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update WarrantyMaster partially : {}, {}", id, warrantyMaster);
        if (warrantyMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, warrantyMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!warrantyMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WarrantyMaster> result = warrantyMasterService.partialUpdate(warrantyMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, warrantyMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /warranty-masters} : get all the warrantyMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of warrantyMasters in body.
     */
    @GetMapping("")
    public ResponseEntity<List<WarrantyMaster>> getAllWarrantyMasters(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of WarrantyMasters");
        Page<WarrantyMaster> page = warrantyMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /warranty-masters/:id} : get the "id" warrantyMaster.
     *
     * @param id the id of the warrantyMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the warrantyMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<WarrantyMaster> getWarrantyMaster(@PathVariable("id") Long id) {
        LOG.debug("REST request to get WarrantyMaster : {}", id);
        Optional<WarrantyMaster> warrantyMaster = warrantyMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(warrantyMaster);
    }

    /**
     * {@code DELETE  /warranty-masters/:id} : delete the "id" warrantyMaster.
     *
     * @param id the id of the warrantyMaster to delete.
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
