package com.framasaas.be.web.rest;

import com.framasaas.be.domain.FranchisePerformanceHistory;
import com.framasaas.be.repository.FranchisePerformanceHistoryRepository;
import com.framasaas.be.service.FranchisePerformanceHistoryService;
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
 * REST controller for managing {@link com.framasaas.be.domain.FranchisePerformanceHistory}.
 */
@RestController
@RequestMapping("/api/franchise-performance-histories")
public class FranchisePerformanceHistoryResource {

    private static final Logger LOG = LoggerFactory.getLogger(FranchisePerformanceHistoryResource.class);

    private static final String ENTITY_NAME = "franchisePerformanceHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FranchisePerformanceHistoryService franchisePerformanceHistoryService;

    private final FranchisePerformanceHistoryRepository franchisePerformanceHistoryRepository;

    public FranchisePerformanceHistoryResource(
        FranchisePerformanceHistoryService franchisePerformanceHistoryService,
        FranchisePerformanceHistoryRepository franchisePerformanceHistoryRepository
    ) {
        this.franchisePerformanceHistoryService = franchisePerformanceHistoryService;
        this.franchisePerformanceHistoryRepository = franchisePerformanceHistoryRepository;
    }

    /**
     * {@code POST  /franchise-performance-histories} : Create a new franchisePerformanceHistory.
     *
     * @param franchisePerformanceHistory the franchisePerformanceHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new franchisePerformanceHistory, or with status {@code 400 (Bad Request)} if the franchisePerformanceHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FranchisePerformanceHistory> createFranchisePerformanceHistory(
        @Valid @RequestBody FranchisePerformanceHistory franchisePerformanceHistory
    ) throws URISyntaxException {
        LOG.debug("REST request to save FranchisePerformanceHistory : {}", franchisePerformanceHistory);
        if (franchisePerformanceHistory.getId() != null) {
            throw new BadRequestAlertException("A new franchisePerformanceHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        franchisePerformanceHistory = franchisePerformanceHistoryService.save(franchisePerformanceHistory);
        return ResponseEntity.created(new URI("/api/franchise-performance-histories/" + franchisePerformanceHistory.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, franchisePerformanceHistory.getId().toString())
            )
            .body(franchisePerformanceHistory);
    }

    /**
     * {@code PUT  /franchise-performance-histories/:id} : Updates an existing franchisePerformanceHistory.
     *
     * @param id the id of the franchisePerformanceHistory to save.
     * @param franchisePerformanceHistory the franchisePerformanceHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchisePerformanceHistory,
     * or with status {@code 400 (Bad Request)} if the franchisePerformanceHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the franchisePerformanceHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FranchisePerformanceHistory> updateFranchisePerformanceHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FranchisePerformanceHistory franchisePerformanceHistory
    ) throws URISyntaxException {
        LOG.debug("REST request to update FranchisePerformanceHistory : {}, {}", id, franchisePerformanceHistory);
        if (franchisePerformanceHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchisePerformanceHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchisePerformanceHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        franchisePerformanceHistory = franchisePerformanceHistoryService.update(franchisePerformanceHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchisePerformanceHistory.getId().toString()))
            .body(franchisePerformanceHistory);
    }

    /**
     * {@code PATCH  /franchise-performance-histories/:id} : Partial updates given fields of an existing franchisePerformanceHistory, field will ignore if it is null
     *
     * @param id the id of the franchisePerformanceHistory to save.
     * @param franchisePerformanceHistory the franchisePerformanceHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchisePerformanceHistory,
     * or with status {@code 400 (Bad Request)} if the franchisePerformanceHistory is not valid,
     * or with status {@code 404 (Not Found)} if the franchisePerformanceHistory is not found,
     * or with status {@code 500 (Internal Server Error)} if the franchisePerformanceHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FranchisePerformanceHistory> partialUpdateFranchisePerformanceHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FranchisePerformanceHistory franchisePerformanceHistory
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FranchisePerformanceHistory partially : {}, {}", id, franchisePerformanceHistory);
        if (franchisePerformanceHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchisePerformanceHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchisePerformanceHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FranchisePerformanceHistory> result = franchisePerformanceHistoryService.partialUpdate(franchisePerformanceHistory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchisePerformanceHistory.getId().toString())
        );
    }

    /**
     * {@code GET  /franchise-performance-histories} : get all the franchisePerformanceHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of franchisePerformanceHistories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FranchisePerformanceHistory>> getAllFranchisePerformanceHistories(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of FranchisePerformanceHistories");
        Page<FranchisePerformanceHistory> page = franchisePerformanceHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /franchise-performance-histories/:id} : get the "id" franchisePerformanceHistory.
     *
     * @param id the id of the franchisePerformanceHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the franchisePerformanceHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FranchisePerformanceHistory> getFranchisePerformanceHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FranchisePerformanceHistory : {}", id);
        Optional<FranchisePerformanceHistory> franchisePerformanceHistory = franchisePerformanceHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(franchisePerformanceHistory);
    }

    /**
     * {@code DELETE  /franchise-performance-histories/:id} : delete the "id" franchisePerformanceHistory.
     *
     * @param id the id of the franchisePerformanceHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFranchisePerformanceHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FranchisePerformanceHistory : {}", id);
        franchisePerformanceHistoryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
