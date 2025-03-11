package com.framasaas.be.web.rest;

import com.framasaas.be.domain.WarrantyMasterPriceHistory;
import com.framasaas.be.repository.WarrantyMasterPriceHistoryRepository;
import com.framasaas.be.service.WarrantyMasterPriceHistoryService;
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
 * REST controller for managing {@link com.framasaas.be.domain.WarrantyMasterPriceHistory}.
 */
@RestController
@RequestMapping("/api/warranty-master-price-histories")
public class WarrantyMasterPriceHistoryResource {

    private static final Logger LOG = LoggerFactory.getLogger(WarrantyMasterPriceHistoryResource.class);

    private static final String ENTITY_NAME = "warrantyMasterPriceHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WarrantyMasterPriceHistoryService warrantyMasterPriceHistoryService;

    private final WarrantyMasterPriceHistoryRepository warrantyMasterPriceHistoryRepository;

    public WarrantyMasterPriceHistoryResource(
        WarrantyMasterPriceHistoryService warrantyMasterPriceHistoryService,
        WarrantyMasterPriceHistoryRepository warrantyMasterPriceHistoryRepository
    ) {
        this.warrantyMasterPriceHistoryService = warrantyMasterPriceHistoryService;
        this.warrantyMasterPriceHistoryRepository = warrantyMasterPriceHistoryRepository;
    }

    /**
     * {@code POST  /warranty-master-price-histories} : Create a new warrantyMasterPriceHistory.
     *
     * @param warrantyMasterPriceHistory the warrantyMasterPriceHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new warrantyMasterPriceHistory, or with status {@code 400 (Bad Request)} if the warrantyMasterPriceHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<WarrantyMasterPriceHistory> createWarrantyMasterPriceHistory(
        @Valid @RequestBody WarrantyMasterPriceHistory warrantyMasterPriceHistory
    ) throws URISyntaxException {
        LOG.debug("REST request to save WarrantyMasterPriceHistory : {}", warrantyMasterPriceHistory);
        if (warrantyMasterPriceHistory.getId() != null) {
            throw new BadRequestAlertException("A new warrantyMasterPriceHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        warrantyMasterPriceHistory = warrantyMasterPriceHistoryService.save(warrantyMasterPriceHistory);
        return ResponseEntity.created(new URI("/api/warranty-master-price-histories/" + warrantyMasterPriceHistory.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, warrantyMasterPriceHistory.getId().toString())
            )
            .body(warrantyMasterPriceHistory);
    }

    /**
     * {@code PUT  /warranty-master-price-histories/:id} : Updates an existing warrantyMasterPriceHistory.
     *
     * @param id the id of the warrantyMasterPriceHistory to save.
     * @param warrantyMasterPriceHistory the warrantyMasterPriceHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated warrantyMasterPriceHistory,
     * or with status {@code 400 (Bad Request)} if the warrantyMasterPriceHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the warrantyMasterPriceHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<WarrantyMasterPriceHistory> updateWarrantyMasterPriceHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WarrantyMasterPriceHistory warrantyMasterPriceHistory
    ) throws URISyntaxException {
        LOG.debug("REST request to update WarrantyMasterPriceHistory : {}, {}", id, warrantyMasterPriceHistory);
        if (warrantyMasterPriceHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, warrantyMasterPriceHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!warrantyMasterPriceHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        warrantyMasterPriceHistory = warrantyMasterPriceHistoryService.update(warrantyMasterPriceHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, warrantyMasterPriceHistory.getId().toString()))
            .body(warrantyMasterPriceHistory);
    }

    /**
     * {@code PATCH  /warranty-master-price-histories/:id} : Partial updates given fields of an existing warrantyMasterPriceHistory, field will ignore if it is null
     *
     * @param id the id of the warrantyMasterPriceHistory to save.
     * @param warrantyMasterPriceHistory the warrantyMasterPriceHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated warrantyMasterPriceHistory,
     * or with status {@code 400 (Bad Request)} if the warrantyMasterPriceHistory is not valid,
     * or with status {@code 404 (Not Found)} if the warrantyMasterPriceHistory is not found,
     * or with status {@code 500 (Internal Server Error)} if the warrantyMasterPriceHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WarrantyMasterPriceHistory> partialUpdateWarrantyMasterPriceHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WarrantyMasterPriceHistory warrantyMasterPriceHistory
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update WarrantyMasterPriceHistory partially : {}, {}", id, warrantyMasterPriceHistory);
        if (warrantyMasterPriceHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, warrantyMasterPriceHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!warrantyMasterPriceHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WarrantyMasterPriceHistory> result = warrantyMasterPriceHistoryService.partialUpdate(warrantyMasterPriceHistory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, warrantyMasterPriceHistory.getId().toString())
        );
    }

    /**
     * {@code GET  /warranty-master-price-histories} : get all the warrantyMasterPriceHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of warrantyMasterPriceHistories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<WarrantyMasterPriceHistory>> getAllWarrantyMasterPriceHistories(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of WarrantyMasterPriceHistories");
        Page<WarrantyMasterPriceHistory> page = warrantyMasterPriceHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /warranty-master-price-histories/:id} : get the "id" warrantyMasterPriceHistory.
     *
     * @param id the id of the warrantyMasterPriceHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the warrantyMasterPriceHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<WarrantyMasterPriceHistory> getWarrantyMasterPriceHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to get WarrantyMasterPriceHistory : {}", id);
        Optional<WarrantyMasterPriceHistory> warrantyMasterPriceHistory = warrantyMasterPriceHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(warrantyMasterPriceHistory);
    }

    /**
     * {@code DELETE  /warranty-master-price-histories/:id} : delete the "id" warrantyMasterPriceHistory.
     *
     * @param id the id of the warrantyMasterPriceHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarrantyMasterPriceHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete WarrantyMasterPriceHistory : {}", id);
        warrantyMasterPriceHistoryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
