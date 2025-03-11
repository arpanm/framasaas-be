package com.framasaas.be.web.rest;

import com.framasaas.be.domain.InventoryLocation;
import com.framasaas.be.repository.InventoryLocationRepository;
import com.framasaas.be.service.InventoryLocationService;
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
 * REST controller for managing {@link com.framasaas.be.domain.InventoryLocation}.
 */
@RestController
@RequestMapping("/api/inventory-locations")
public class InventoryLocationResource {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryLocationResource.class);

    private static final String ENTITY_NAME = "inventoryLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InventoryLocationService inventoryLocationService;

    private final InventoryLocationRepository inventoryLocationRepository;

    public InventoryLocationResource(
        InventoryLocationService inventoryLocationService,
        InventoryLocationRepository inventoryLocationRepository
    ) {
        this.inventoryLocationService = inventoryLocationService;
        this.inventoryLocationRepository = inventoryLocationRepository;
    }

    /**
     * {@code POST  /inventory-locations} : Create a new inventoryLocation.
     *
     * @param inventoryLocation the inventoryLocation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventoryLocation, or with status {@code 400 (Bad Request)} if the inventoryLocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InventoryLocation> createInventoryLocation(@Valid @RequestBody InventoryLocation inventoryLocation)
        throws URISyntaxException {
        LOG.debug("REST request to save InventoryLocation : {}", inventoryLocation);
        if (inventoryLocation.getId() != null) {
            throw new BadRequestAlertException("A new inventoryLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inventoryLocation = inventoryLocationService.save(inventoryLocation);
        return ResponseEntity.created(new URI("/api/inventory-locations/" + inventoryLocation.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, inventoryLocation.getId().toString()))
            .body(inventoryLocation);
    }

    /**
     * {@code PUT  /inventory-locations/:id} : Updates an existing inventoryLocation.
     *
     * @param id the id of the inventoryLocation to save.
     * @param inventoryLocation the inventoryLocation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryLocation,
     * or with status {@code 400 (Bad Request)} if the inventoryLocation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventoryLocation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InventoryLocation> updateInventoryLocation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InventoryLocation inventoryLocation
    ) throws URISyntaxException {
        LOG.debug("REST request to update InventoryLocation : {}, {}", id, inventoryLocation);
        if (inventoryLocation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inventoryLocation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inventoryLocationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inventoryLocation = inventoryLocationService.update(inventoryLocation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryLocation.getId().toString()))
            .body(inventoryLocation);
    }

    /**
     * {@code PATCH  /inventory-locations/:id} : Partial updates given fields of an existing inventoryLocation, field will ignore if it is null
     *
     * @param id the id of the inventoryLocation to save.
     * @param inventoryLocation the inventoryLocation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryLocation,
     * or with status {@code 400 (Bad Request)} if the inventoryLocation is not valid,
     * or with status {@code 404 (Not Found)} if the inventoryLocation is not found,
     * or with status {@code 500 (Internal Server Error)} if the inventoryLocation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InventoryLocation> partialUpdateInventoryLocation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InventoryLocation inventoryLocation
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update InventoryLocation partially : {}, {}", id, inventoryLocation);
        if (inventoryLocation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inventoryLocation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inventoryLocationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InventoryLocation> result = inventoryLocationService.partialUpdate(inventoryLocation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryLocation.getId().toString())
        );
    }

    /**
     * {@code GET  /inventory-locations} : get all the inventoryLocations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventoryLocations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<InventoryLocation>> getAllInventoryLocations(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of InventoryLocations");
        Page<InventoryLocation> page = inventoryLocationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inventory-locations/:id} : get the "id" inventoryLocation.
     *
     * @param id the id of the inventoryLocation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventoryLocation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InventoryLocation> getInventoryLocation(@PathVariable("id") Long id) {
        LOG.debug("REST request to get InventoryLocation : {}", id);
        Optional<InventoryLocation> inventoryLocation = inventoryLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inventoryLocation);
    }

    /**
     * {@code DELETE  /inventory-locations/:id} : delete the "id" inventoryLocation.
     *
     * @param id the id of the inventoryLocation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryLocation(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete InventoryLocation : {}", id);
        inventoryLocationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
