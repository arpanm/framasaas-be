package com.framasaas.web.rest;

import com.framasaas.repository.FranchisePerformanceHistoryRepository;
import com.framasaas.service.FranchisePerformanceHistoryQueryService;
import com.framasaas.service.FranchisePerformanceHistoryService;
import com.framasaas.service.criteria.FranchisePerformanceHistoryCriteria;
import com.framasaas.service.dto.FranchisePerformanceHistoryDTO;
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
 * REST controller for managing {@link com.framasaas.domain.FranchisePerformanceHistory}.
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

    private final FranchisePerformanceHistoryQueryService franchisePerformanceHistoryQueryService;

    public FranchisePerformanceHistoryResource(
        FranchisePerformanceHistoryService franchisePerformanceHistoryService,
        FranchisePerformanceHistoryRepository franchisePerformanceHistoryRepository,
        FranchisePerformanceHistoryQueryService franchisePerformanceHistoryQueryService
    ) {
        this.franchisePerformanceHistoryService = franchisePerformanceHistoryService;
        this.franchisePerformanceHistoryRepository = franchisePerformanceHistoryRepository;
        this.franchisePerformanceHistoryQueryService = franchisePerformanceHistoryQueryService;
    }

    /**
     * {@code POST  /franchise-performance-histories} : Create a new franchisePerformanceHistory.
     *
     * @param franchisePerformanceHistoryDTO the franchisePerformanceHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new franchisePerformanceHistoryDTO, or with status {@code 400 (Bad Request)} if the franchisePerformanceHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FranchisePerformanceHistoryDTO> createFranchisePerformanceHistory(
        @Valid @RequestBody FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save FranchisePerformanceHistory : {}", franchisePerformanceHistoryDTO);
        if (franchisePerformanceHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new franchisePerformanceHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        franchisePerformanceHistoryDTO = franchisePerformanceHistoryService.save(franchisePerformanceHistoryDTO);
        return ResponseEntity.created(new URI("/api/franchise-performance-histories/" + franchisePerformanceHistoryDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, franchisePerformanceHistoryDTO.getId().toString())
            )
            .body(franchisePerformanceHistoryDTO);
    }

    /**
     * {@code PUT  /franchise-performance-histories/:id} : Updates an existing franchisePerformanceHistory.
     *
     * @param id the id of the franchisePerformanceHistoryDTO to save.
     * @param franchisePerformanceHistoryDTO the franchisePerformanceHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchisePerformanceHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the franchisePerformanceHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the franchisePerformanceHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FranchisePerformanceHistoryDTO> updateFranchisePerformanceHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update FranchisePerformanceHistory : {}, {}", id, franchisePerformanceHistoryDTO);
        if (franchisePerformanceHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchisePerformanceHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchisePerformanceHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        franchisePerformanceHistoryDTO = franchisePerformanceHistoryService.update(franchisePerformanceHistoryDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchisePerformanceHistoryDTO.getId().toString())
            )
            .body(franchisePerformanceHistoryDTO);
    }

    /**
     * {@code PATCH  /franchise-performance-histories/:id} : Partial updates given fields of an existing franchisePerformanceHistory, field will ignore if it is null
     *
     * @param id the id of the franchisePerformanceHistoryDTO to save.
     * @param franchisePerformanceHistoryDTO the franchisePerformanceHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchisePerformanceHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the franchisePerformanceHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the franchisePerformanceHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the franchisePerformanceHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FranchisePerformanceHistoryDTO> partialUpdateFranchisePerformanceHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FranchisePerformanceHistory partially : {}, {}", id, franchisePerformanceHistoryDTO);
        if (franchisePerformanceHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchisePerformanceHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchisePerformanceHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FranchisePerformanceHistoryDTO> result = franchisePerformanceHistoryService.partialUpdate(franchisePerformanceHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchisePerformanceHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /franchise-performance-histories} : get all the franchisePerformanceHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of franchisePerformanceHistories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FranchisePerformanceHistoryDTO>> getAllFranchisePerformanceHistories(
        FranchisePerformanceHistoryCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get FranchisePerformanceHistories by criteria: {}", criteria);

        Page<FranchisePerformanceHistoryDTO> page = franchisePerformanceHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /franchise-performance-histories/count} : count all the franchisePerformanceHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFranchisePerformanceHistories(FranchisePerformanceHistoryCriteria criteria) {
        LOG.debug("REST request to count FranchisePerformanceHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(franchisePerformanceHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /franchise-performance-histories/:id} : get the "id" franchisePerformanceHistory.
     *
     * @param id the id of the franchisePerformanceHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the franchisePerformanceHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FranchisePerformanceHistoryDTO> getFranchisePerformanceHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FranchisePerformanceHistory : {}", id);
        Optional<FranchisePerformanceHistoryDTO> franchisePerformanceHistoryDTO = franchisePerformanceHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(franchisePerformanceHistoryDTO);
    }

    /**
     * {@code DELETE  /franchise-performance-histories/:id} : delete the "id" franchisePerformanceHistory.
     *
     * @param id the id of the franchisePerformanceHistoryDTO to delete.
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
