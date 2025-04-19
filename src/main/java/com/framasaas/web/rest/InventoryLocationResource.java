package com.framasaas.web.rest;

import com.framasaas.repository.InventoryLocationRepository;
import com.framasaas.service.InventoryLocationQueryService;
import com.framasaas.service.InventoryLocationService;
import com.framasaas.service.criteria.InventoryLocationCriteria;
import com.framasaas.service.dto.InventoryLocationDTO;
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
 * REST controller for managing {@link com.framasaas.domain.InventoryLocation}.
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

    private final InventoryLocationQueryService inventoryLocationQueryService;

    public InventoryLocationResource(
        InventoryLocationService inventoryLocationService,
        InventoryLocationRepository inventoryLocationRepository,
        InventoryLocationQueryService inventoryLocationQueryService
    ) {
        this.inventoryLocationService = inventoryLocationService;
        this.inventoryLocationRepository = inventoryLocationRepository;
        this.inventoryLocationQueryService = inventoryLocationQueryService;
    }

    /**
     * {@code POST  /inventory-locations} : Create a new inventoryLocation.
     *
     * @param inventoryLocationDTO the inventoryLocationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventoryLocationDTO, or with status {@code 400 (Bad Request)} if the inventoryLocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InventoryLocationDTO> createInventoryLocation(@Valid @RequestBody InventoryLocationDTO inventoryLocationDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save InventoryLocation : {}", inventoryLocationDTO);
        if (inventoryLocationDTO.getId() != null) {
            throw new BadRequestAlertException("A new inventoryLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inventoryLocationDTO = inventoryLocationService.save(inventoryLocationDTO);
        return ResponseEntity.created(new URI("/api/inventory-locations/" + inventoryLocationDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, inventoryLocationDTO.getId().toString()))
            .body(inventoryLocationDTO);
    }

    /**
     * {@code PUT  /inventory-locations/:id} : Updates an existing inventoryLocation.
     *
     * @param id the id of the inventoryLocationDTO to save.
     * @param inventoryLocationDTO the inventoryLocationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryLocationDTO,
     * or with status {@code 400 (Bad Request)} if the inventoryLocationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventoryLocationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InventoryLocationDTO> updateInventoryLocation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InventoryLocationDTO inventoryLocationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update InventoryLocation : {}, {}", id, inventoryLocationDTO);
        if (inventoryLocationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inventoryLocationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inventoryLocationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inventoryLocationDTO = inventoryLocationService.update(inventoryLocationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryLocationDTO.getId().toString()))
            .body(inventoryLocationDTO);
    }

    /**
     * {@code PATCH  /inventory-locations/:id} : Partial updates given fields of an existing inventoryLocation, field will ignore if it is null
     *
     * @param id the id of the inventoryLocationDTO to save.
     * @param inventoryLocationDTO the inventoryLocationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryLocationDTO,
     * or with status {@code 400 (Bad Request)} if the inventoryLocationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the inventoryLocationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the inventoryLocationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InventoryLocationDTO> partialUpdateInventoryLocation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InventoryLocationDTO inventoryLocationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update InventoryLocation partially : {}, {}", id, inventoryLocationDTO);
        if (inventoryLocationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inventoryLocationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inventoryLocationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InventoryLocationDTO> result = inventoryLocationService.partialUpdate(inventoryLocationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryLocationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /inventory-locations} : get all the inventoryLocations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventoryLocations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<InventoryLocationDTO>> getAllInventoryLocations(
        InventoryLocationCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get InventoryLocations by criteria: {}", criteria);

        Page<InventoryLocationDTO> page = inventoryLocationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inventory-locations/count} : count all the inventoryLocations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countInventoryLocations(InventoryLocationCriteria criteria) {
        LOG.debug("REST request to count InventoryLocations by criteria: {}", criteria);
        return ResponseEntity.ok().body(inventoryLocationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /inventory-locations/:id} : get the "id" inventoryLocation.
     *
     * @param id the id of the inventoryLocationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventoryLocationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InventoryLocationDTO> getInventoryLocation(@PathVariable("id") Long id) {
        LOG.debug("REST request to get InventoryLocation : {}", id);
        Optional<InventoryLocationDTO> inventoryLocationDTO = inventoryLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inventoryLocationDTO);
    }

    /**
     * {@code DELETE  /inventory-locations/:id} : delete the "id" inventoryLocation.
     *
     * @param id the id of the inventoryLocationDTO to delete.
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
