package com.framasaas.web.rest;

import com.framasaas.repository.InventoryHistoryRepository;
import com.framasaas.service.InventoryHistoryQueryService;
import com.framasaas.service.InventoryHistoryService;
import com.framasaas.service.criteria.InventoryHistoryCriteria;
import com.framasaas.service.dto.InventoryHistoryDTO;
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
 * REST controller for managing {@link com.framasaas.domain.InventoryHistory}.
 */
@RestController
@RequestMapping("/api/inventory-histories")
public class InventoryHistoryResource {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryHistoryResource.class);

    private static final String ENTITY_NAME = "inventoryHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InventoryHistoryService inventoryHistoryService;

    private final InventoryHistoryRepository inventoryHistoryRepository;

    private final InventoryHistoryQueryService inventoryHistoryQueryService;

    public InventoryHistoryResource(
        InventoryHistoryService inventoryHistoryService,
        InventoryHistoryRepository inventoryHistoryRepository,
        InventoryHistoryQueryService inventoryHistoryQueryService
    ) {
        this.inventoryHistoryService = inventoryHistoryService;
        this.inventoryHistoryRepository = inventoryHistoryRepository;
        this.inventoryHistoryQueryService = inventoryHistoryQueryService;
    }

    /**
     * {@code POST  /inventory-histories} : Create a new inventoryHistory.
     *
     * @param inventoryHistoryDTO the inventoryHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventoryHistoryDTO, or with status {@code 400 (Bad Request)} if the inventoryHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InventoryHistoryDTO> createInventoryHistory(@Valid @RequestBody InventoryHistoryDTO inventoryHistoryDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save InventoryHistory : {}", inventoryHistoryDTO);
        if (inventoryHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new inventoryHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inventoryHistoryDTO = inventoryHistoryService.save(inventoryHistoryDTO);
        return ResponseEntity.created(new URI("/api/inventory-histories/" + inventoryHistoryDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, inventoryHistoryDTO.getId().toString()))
            .body(inventoryHistoryDTO);
    }

    /**
     * {@code PUT  /inventory-histories/:id} : Updates an existing inventoryHistory.
     *
     * @param id the id of the inventoryHistoryDTO to save.
     * @param inventoryHistoryDTO the inventoryHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the inventoryHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventoryHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InventoryHistoryDTO> updateInventoryHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InventoryHistoryDTO inventoryHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update InventoryHistory : {}, {}", id, inventoryHistoryDTO);
        if (inventoryHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inventoryHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inventoryHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inventoryHistoryDTO = inventoryHistoryService.update(inventoryHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryHistoryDTO.getId().toString()))
            .body(inventoryHistoryDTO);
    }

    /**
     * {@code PATCH  /inventory-histories/:id} : Partial updates given fields of an existing inventoryHistory, field will ignore if it is null
     *
     * @param id the id of the inventoryHistoryDTO to save.
     * @param inventoryHistoryDTO the inventoryHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the inventoryHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the inventoryHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the inventoryHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InventoryHistoryDTO> partialUpdateInventoryHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InventoryHistoryDTO inventoryHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update InventoryHistory partially : {}, {}", id, inventoryHistoryDTO);
        if (inventoryHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inventoryHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inventoryHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InventoryHistoryDTO> result = inventoryHistoryService.partialUpdate(inventoryHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /inventory-histories} : get all the inventoryHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventoryHistories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<InventoryHistoryDTO>> getAllInventoryHistories(
        InventoryHistoryCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get InventoryHistories by criteria: {}", criteria);

        Page<InventoryHistoryDTO> page = inventoryHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inventory-histories/count} : count all the inventoryHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countInventoryHistories(InventoryHistoryCriteria criteria) {
        LOG.debug("REST request to count InventoryHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(inventoryHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /inventory-histories/:id} : get the "id" inventoryHistory.
     *
     * @param id the id of the inventoryHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventoryHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InventoryHistoryDTO> getInventoryHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to get InventoryHistory : {}", id);
        Optional<InventoryHistoryDTO> inventoryHistoryDTO = inventoryHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inventoryHistoryDTO);
    }

    /**
     * {@code DELETE  /inventory-histories/:id} : delete the "id" inventoryHistory.
     *
     * @param id the id of the inventoryHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete InventoryHistory : {}", id);
        inventoryHistoryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
