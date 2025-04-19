package com.framasaas.web.rest;

import com.framasaas.repository.FranchiseStatusHistoryRepository;
import com.framasaas.service.FranchiseStatusHistoryQueryService;
import com.framasaas.service.FranchiseStatusHistoryService;
import com.framasaas.service.criteria.FranchiseStatusHistoryCriteria;
import com.framasaas.service.dto.FranchiseStatusHistoryDTO;
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
 * REST controller for managing {@link com.framasaas.domain.FranchiseStatusHistory}.
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

    private final FranchiseStatusHistoryQueryService franchiseStatusHistoryQueryService;

    public FranchiseStatusHistoryResource(
        FranchiseStatusHistoryService franchiseStatusHistoryService,
        FranchiseStatusHistoryRepository franchiseStatusHistoryRepository,
        FranchiseStatusHistoryQueryService franchiseStatusHistoryQueryService
    ) {
        this.franchiseStatusHistoryService = franchiseStatusHistoryService;
        this.franchiseStatusHistoryRepository = franchiseStatusHistoryRepository;
        this.franchiseStatusHistoryQueryService = franchiseStatusHistoryQueryService;
    }

    /**
     * {@code POST  /franchise-status-histories} : Create a new franchiseStatusHistory.
     *
     * @param franchiseStatusHistoryDTO the franchiseStatusHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new franchiseStatusHistoryDTO, or with status {@code 400 (Bad Request)} if the franchiseStatusHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FranchiseStatusHistoryDTO> createFranchiseStatusHistory(
        @Valid @RequestBody FranchiseStatusHistoryDTO franchiseStatusHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save FranchiseStatusHistory : {}", franchiseStatusHistoryDTO);
        if (franchiseStatusHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new franchiseStatusHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        franchiseStatusHistoryDTO = franchiseStatusHistoryService.save(franchiseStatusHistoryDTO);
        return ResponseEntity.created(new URI("/api/franchise-status-histories/" + franchiseStatusHistoryDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, franchiseStatusHistoryDTO.getId().toString()))
            .body(franchiseStatusHistoryDTO);
    }

    /**
     * {@code PUT  /franchise-status-histories/:id} : Updates an existing franchiseStatusHistory.
     *
     * @param id the id of the franchiseStatusHistoryDTO to save.
     * @param franchiseStatusHistoryDTO the franchiseStatusHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseStatusHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the franchiseStatusHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the franchiseStatusHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FranchiseStatusHistoryDTO> updateFranchiseStatusHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FranchiseStatusHistoryDTO franchiseStatusHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update FranchiseStatusHistory : {}, {}", id, franchiseStatusHistoryDTO);
        if (franchiseStatusHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseStatusHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseStatusHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        franchiseStatusHistoryDTO = franchiseStatusHistoryService.update(franchiseStatusHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseStatusHistoryDTO.getId().toString()))
            .body(franchiseStatusHistoryDTO);
    }

    /**
     * {@code PATCH  /franchise-status-histories/:id} : Partial updates given fields of an existing franchiseStatusHistory, field will ignore if it is null
     *
     * @param id the id of the franchiseStatusHistoryDTO to save.
     * @param franchiseStatusHistoryDTO the franchiseStatusHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseStatusHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the franchiseStatusHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the franchiseStatusHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the franchiseStatusHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FranchiseStatusHistoryDTO> partialUpdateFranchiseStatusHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FranchiseStatusHistoryDTO franchiseStatusHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FranchiseStatusHistory partially : {}, {}", id, franchiseStatusHistoryDTO);
        if (franchiseStatusHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseStatusHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseStatusHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FranchiseStatusHistoryDTO> result = franchiseStatusHistoryService.partialUpdate(franchiseStatusHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseStatusHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /franchise-status-histories} : get all the franchiseStatusHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of franchiseStatusHistories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FranchiseStatusHistoryDTO>> getAllFranchiseStatusHistories(
        FranchiseStatusHistoryCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get FranchiseStatusHistories by criteria: {}", criteria);

        Page<FranchiseStatusHistoryDTO> page = franchiseStatusHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /franchise-status-histories/count} : count all the franchiseStatusHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFranchiseStatusHistories(FranchiseStatusHistoryCriteria criteria) {
        LOG.debug("REST request to count FranchiseStatusHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(franchiseStatusHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /franchise-status-histories/:id} : get the "id" franchiseStatusHistory.
     *
     * @param id the id of the franchiseStatusHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the franchiseStatusHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FranchiseStatusHistoryDTO> getFranchiseStatusHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FranchiseStatusHistory : {}", id);
        Optional<FranchiseStatusHistoryDTO> franchiseStatusHistoryDTO = franchiseStatusHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(franchiseStatusHistoryDTO);
    }

    /**
     * {@code DELETE  /franchise-status-histories/:id} : delete the "id" franchiseStatusHistory.
     *
     * @param id the id of the franchiseStatusHistoryDTO to delete.
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
