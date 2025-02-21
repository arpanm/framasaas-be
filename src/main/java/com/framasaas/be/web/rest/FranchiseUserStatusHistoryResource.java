package com.framasaas.be.web.rest;

import com.framasaas.be.domain.FranchiseUserStatusHistory;
import com.framasaas.be.repository.FranchiseUserStatusHistoryRepository;
import com.framasaas.be.service.FranchiseUserStatusHistoryService;
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
 * REST controller for managing {@link com.framasaas.be.domain.FranchiseUserStatusHistory}.
 */
@RestController
@RequestMapping("/api/franchise-user-status-histories")
public class FranchiseUserStatusHistoryResource {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseUserStatusHistoryResource.class);

    private static final String ENTITY_NAME = "franchiseUserStatusHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FranchiseUserStatusHistoryService franchiseUserStatusHistoryService;

    private final FranchiseUserStatusHistoryRepository franchiseUserStatusHistoryRepository;

    public FranchiseUserStatusHistoryResource(
        FranchiseUserStatusHistoryService franchiseUserStatusHistoryService,
        FranchiseUserStatusHistoryRepository franchiseUserStatusHistoryRepository
    ) {
        this.franchiseUserStatusHistoryService = franchiseUserStatusHistoryService;
        this.franchiseUserStatusHistoryRepository = franchiseUserStatusHistoryRepository;
    }

    /**
     * {@code POST  /franchise-user-status-histories} : Create a new franchiseUserStatusHistory.
     *
     * @param franchiseUserStatusHistory the franchiseUserStatusHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new franchiseUserStatusHistory, or with status {@code 400 (Bad Request)} if the franchiseUserStatusHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FranchiseUserStatusHistory> createFranchiseUserStatusHistory(
        @Valid @RequestBody FranchiseUserStatusHistory franchiseUserStatusHistory
    ) throws URISyntaxException {
        LOG.debug("REST request to save FranchiseUserStatusHistory : {}", franchiseUserStatusHistory);
        if (franchiseUserStatusHistory.getId() != null) {
            throw new BadRequestAlertException("A new franchiseUserStatusHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        franchiseUserStatusHistory = franchiseUserStatusHistoryService.save(franchiseUserStatusHistory);
        return ResponseEntity.created(new URI("/api/franchise-user-status-histories/" + franchiseUserStatusHistory.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, franchiseUserStatusHistory.getId().toString())
            )
            .body(franchiseUserStatusHistory);
    }

    /**
     * {@code PUT  /franchise-user-status-histories/:id} : Updates an existing franchiseUserStatusHistory.
     *
     * @param id the id of the franchiseUserStatusHistory to save.
     * @param franchiseUserStatusHistory the franchiseUserStatusHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseUserStatusHistory,
     * or with status {@code 400 (Bad Request)} if the franchiseUserStatusHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the franchiseUserStatusHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FranchiseUserStatusHistory> updateFranchiseUserStatusHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FranchiseUserStatusHistory franchiseUserStatusHistory
    ) throws URISyntaxException {
        LOG.debug("REST request to update FranchiseUserStatusHistory : {}, {}", id, franchiseUserStatusHistory);
        if (franchiseUserStatusHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseUserStatusHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseUserStatusHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        franchiseUserStatusHistory = franchiseUserStatusHistoryService.update(franchiseUserStatusHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseUserStatusHistory.getId().toString()))
            .body(franchiseUserStatusHistory);
    }

    /**
     * {@code PATCH  /franchise-user-status-histories/:id} : Partial updates given fields of an existing franchiseUserStatusHistory, field will ignore if it is null
     *
     * @param id the id of the franchiseUserStatusHistory to save.
     * @param franchiseUserStatusHistory the franchiseUserStatusHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseUserStatusHistory,
     * or with status {@code 400 (Bad Request)} if the franchiseUserStatusHistory is not valid,
     * or with status {@code 404 (Not Found)} if the franchiseUserStatusHistory is not found,
     * or with status {@code 500 (Internal Server Error)} if the franchiseUserStatusHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FranchiseUserStatusHistory> partialUpdateFranchiseUserStatusHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FranchiseUserStatusHistory franchiseUserStatusHistory
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FranchiseUserStatusHistory partially : {}, {}", id, franchiseUserStatusHistory);
        if (franchiseUserStatusHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseUserStatusHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseUserStatusHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FranchiseUserStatusHistory> result = franchiseUserStatusHistoryService.partialUpdate(franchiseUserStatusHistory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseUserStatusHistory.getId().toString())
        );
    }

    /**
     * {@code GET  /franchise-user-status-histories} : get all the franchiseUserStatusHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of franchiseUserStatusHistories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FranchiseUserStatusHistory>> getAllFranchiseUserStatusHistories(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of FranchiseUserStatusHistories");
        Page<FranchiseUserStatusHistory> page = franchiseUserStatusHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /franchise-user-status-histories/:id} : get the "id" franchiseUserStatusHistory.
     *
     * @param id the id of the franchiseUserStatusHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the franchiseUserStatusHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FranchiseUserStatusHistory> getFranchiseUserStatusHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FranchiseUserStatusHistory : {}", id);
        Optional<FranchiseUserStatusHistory> franchiseUserStatusHistory = franchiseUserStatusHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(franchiseUserStatusHistory);
    }

    /**
     * {@code DELETE  /franchise-user-status-histories/:id} : delete the "id" franchiseUserStatusHistory.
     *
     * @param id the id of the franchiseUserStatusHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFranchiseUserStatusHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FranchiseUserStatusHistory : {}", id);
        franchiseUserStatusHistoryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
