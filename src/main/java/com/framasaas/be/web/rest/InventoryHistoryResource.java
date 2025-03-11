package com.framasaas.be.web.rest;

import com.framasaas.be.domain.InventoryHistory;
import com.framasaas.be.repository.InventoryHistoryRepository;
import com.framasaas.be.service.InventoryHistoryService;
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
 * REST controller for managing {@link com.framasaas.be.domain.InventoryHistory}.
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

    public InventoryHistoryResource(
        InventoryHistoryService inventoryHistoryService,
        InventoryHistoryRepository inventoryHistoryRepository
    ) {
        this.inventoryHistoryService = inventoryHistoryService;
        this.inventoryHistoryRepository = inventoryHistoryRepository;
    }

    /**
     * {@code POST  /inventory-histories} : Create a new inventoryHistory.
     *
     * @param inventoryHistory the inventoryHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventoryHistory, or with status {@code 400 (Bad Request)} if the inventoryHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InventoryHistory> createInventoryHistory(@Valid @RequestBody InventoryHistory inventoryHistory)
        throws URISyntaxException {
        LOG.debug("REST request to save InventoryHistory : {}", inventoryHistory);
        if (inventoryHistory.getId() != null) {
            throw new BadRequestAlertException("A new inventoryHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inventoryHistory = inventoryHistoryService.save(inventoryHistory);
        return ResponseEntity.created(new URI("/api/inventory-histories/" + inventoryHistory.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, inventoryHistory.getId().toString()))
            .body(inventoryHistory);
    }

    /**
     * {@code PUT  /inventory-histories/:id} : Updates an existing inventoryHistory.
     *
     * @param id the id of the inventoryHistory to save.
     * @param inventoryHistory the inventoryHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryHistory,
     * or with status {@code 400 (Bad Request)} if the inventoryHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventoryHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InventoryHistory> updateInventoryHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InventoryHistory inventoryHistory
    ) throws URISyntaxException {
        LOG.debug("REST request to update InventoryHistory : {}, {}", id, inventoryHistory);
        if (inventoryHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inventoryHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inventoryHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inventoryHistory = inventoryHistoryService.update(inventoryHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryHistory.getId().toString()))
            .body(inventoryHistory);
    }

    /**
     * {@code PATCH  /inventory-histories/:id} : Partial updates given fields of an existing inventoryHistory, field will ignore if it is null
     *
     * @param id the id of the inventoryHistory to save.
     * @param inventoryHistory the inventoryHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryHistory,
     * or with status {@code 400 (Bad Request)} if the inventoryHistory is not valid,
     * or with status {@code 404 (Not Found)} if the inventoryHistory is not found,
     * or with status {@code 500 (Internal Server Error)} if the inventoryHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InventoryHistory> partialUpdateInventoryHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InventoryHistory inventoryHistory
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update InventoryHistory partially : {}, {}", id, inventoryHistory);
        if (inventoryHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inventoryHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inventoryHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InventoryHistory> result = inventoryHistoryService.partialUpdate(inventoryHistory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryHistory.getId().toString())
        );
    }

    /**
     * {@code GET  /inventory-histories} : get all the inventoryHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventoryHistories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<InventoryHistory>> getAllInventoryHistories(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of InventoryHistories");
        Page<InventoryHistory> page = inventoryHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inventory-histories/:id} : get the "id" inventoryHistory.
     *
     * @param id the id of the inventoryHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventoryHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InventoryHistory> getInventoryHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to get InventoryHistory : {}", id);
        Optional<InventoryHistory> inventoryHistory = inventoryHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inventoryHistory);
    }

    /**
     * {@code DELETE  /inventory-histories/:id} : delete the "id" inventoryHistory.
     *
     * @param id the id of the inventoryHistory to delete.
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
