package com.framasaas.be.web.rest;

import com.framasaas.be.domain.FranchiseStatusHistory;
import com.framasaas.be.repository.FranchiseStatusHistoryRepository;
import com.framasaas.be.service.FranchiseStatusHistoryService;
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
 * REST controller for managing {@link com.framasaas.be.domain.FranchiseStatusHistory}.
 */
@RestController
@RequestMapping("/api/franchise-status-histories")
public class FranchiseStatusHistoryResource {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseStatusHistoryResource.class);

    private static final String ENTITY_NAME = "franchiseStatusHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FranchiseStatusHistoryService franchiseStatusHistoryService;

    private final FranchiseStatusHistoryRepository franchiseStatusHistoryRepository;

    public FranchiseStatusHistoryResource(
        FranchiseStatusHistoryService franchiseStatusHistoryService,
        FranchiseStatusHistoryRepository franchiseStatusHistoryRepository
    ) {
        this.franchiseStatusHistoryService = franchiseStatusHistoryService;
        this.franchiseStatusHistoryRepository = franchiseStatusHistoryRepository;
    }

    /**
     * {@code POST  /franchise-status-histories} : Create a new franchiseStatusHistory.
     *
     * @param franchiseStatusHistory the franchiseStatusHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new franchiseStatusHistory, or with status {@code 400 (Bad Request)} if the franchiseStatusHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FranchiseStatusHistory> createFranchiseStatusHistory(
        @Valid @RequestBody FranchiseStatusHistory franchiseStatusHistory
    ) throws URISyntaxException {
        LOG.debug("REST request to save FranchiseStatusHistory : {}", franchiseStatusHistory);
        if (franchiseStatusHistory.getId() != null) {
            throw new BadRequestAlertException("A new franchiseStatusHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        franchiseStatusHistory = franchiseStatusHistoryService.save(franchiseStatusHistory);
        return ResponseEntity.created(new URI("/api/franchise-status-histories/" + franchiseStatusHistory.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, franchiseStatusHistory.getId().toString()))
            .body(franchiseStatusHistory);
    }

    /**
     * {@code PUT  /franchise-status-histories/:id} : Updates an existing franchiseStatusHistory.
     *
     * @param id the id of the franchiseStatusHistory to save.
     * @param franchiseStatusHistory the franchiseStatusHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseStatusHistory,
     * or with status {@code 400 (Bad Request)} if the franchiseStatusHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the franchiseStatusHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FranchiseStatusHistory> updateFranchiseStatusHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FranchiseStatusHistory franchiseStatusHistory
    ) throws URISyntaxException {
        LOG.debug("REST request to update FranchiseStatusHistory : {}, {}", id, franchiseStatusHistory);
        if (franchiseStatusHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseStatusHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseStatusHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        franchiseStatusHistory = franchiseStatusHistoryService.update(franchiseStatusHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseStatusHistory.getId().toString()))
            .body(franchiseStatusHistory);
    }

    /**
     * {@code PATCH  /franchise-status-histories/:id} : Partial updates given fields of an existing franchiseStatusHistory, field will ignore if it is null
     *
     * @param id the id of the franchiseStatusHistory to save.
     * @param franchiseStatusHistory the franchiseStatusHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseStatusHistory,
     * or with status {@code 400 (Bad Request)} if the franchiseStatusHistory is not valid,
     * or with status {@code 404 (Not Found)} if the franchiseStatusHistory is not found,
     * or with status {@code 500 (Internal Server Error)} if the franchiseStatusHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FranchiseStatusHistory> partialUpdateFranchiseStatusHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FranchiseStatusHistory franchiseStatusHistory
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FranchiseStatusHistory partially : {}, {}", id, franchiseStatusHistory);
        if (franchiseStatusHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseStatusHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseStatusHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FranchiseStatusHistory> result = franchiseStatusHistoryService.partialUpdate(franchiseStatusHistory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseStatusHistory.getId().toString())
        );
    }

    /**
     * {@code GET  /franchise-status-histories} : get all the franchiseStatusHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of franchiseStatusHistories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FranchiseStatusHistory>> getAllFranchiseStatusHistories(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of FranchiseStatusHistories");
        Page<FranchiseStatusHistory> page = franchiseStatusHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /franchise-status-histories/:id} : get the "id" franchiseStatusHistory.
     *
     * @param id the id of the franchiseStatusHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the franchiseStatusHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FranchiseStatusHistory> getFranchiseStatusHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FranchiseStatusHistory : {}", id);
        Optional<FranchiseStatusHistory> franchiseStatusHistory = franchiseStatusHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(franchiseStatusHistory);
    }

    /**
     * {@code DELETE  /franchise-status-histories/:id} : delete the "id" franchiseStatusHistory.
     *
     * @param id the id of the franchiseStatusHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFranchiseStatusHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FranchiseStatusHistory : {}", id);
        franchiseStatusHistoryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
