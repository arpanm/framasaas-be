package com.framasaas.web.rest;

import com.framasaas.repository.FranchiseUserStatusHistoryRepository;
import com.framasaas.service.FranchiseUserStatusHistoryQueryService;
import com.framasaas.service.FranchiseUserStatusHistoryService;
import com.framasaas.service.criteria.FranchiseUserStatusHistoryCriteria;
import com.framasaas.service.dto.FranchiseUserStatusHistoryDTO;
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
 * REST controller for managing {@link com.framasaas.domain.FranchiseUserStatusHistory}.
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

    private final FranchiseUserStatusHistoryQueryService franchiseUserStatusHistoryQueryService;

    public FranchiseUserStatusHistoryResource(
        FranchiseUserStatusHistoryService franchiseUserStatusHistoryService,
        FranchiseUserStatusHistoryRepository franchiseUserStatusHistoryRepository,
        FranchiseUserStatusHistoryQueryService franchiseUserStatusHistoryQueryService
    ) {
        this.franchiseUserStatusHistoryService = franchiseUserStatusHistoryService;
        this.franchiseUserStatusHistoryRepository = franchiseUserStatusHistoryRepository;
        this.franchiseUserStatusHistoryQueryService = franchiseUserStatusHistoryQueryService;
    }

    /**
     * {@code POST  /franchise-user-status-histories} : Create a new franchiseUserStatusHistory.
     *
     * @param franchiseUserStatusHistoryDTO the franchiseUserStatusHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new franchiseUserStatusHistoryDTO, or with status {@code 400 (Bad Request)} if the franchiseUserStatusHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FranchiseUserStatusHistoryDTO> createFranchiseUserStatusHistory(
        @Valid @RequestBody FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save FranchiseUserStatusHistory : {}", franchiseUserStatusHistoryDTO);
        if (franchiseUserStatusHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new franchiseUserStatusHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        franchiseUserStatusHistoryDTO = franchiseUserStatusHistoryService.save(franchiseUserStatusHistoryDTO);
        return ResponseEntity.created(new URI("/api/franchise-user-status-histories/" + franchiseUserStatusHistoryDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, franchiseUserStatusHistoryDTO.getId().toString())
            )
            .body(franchiseUserStatusHistoryDTO);
    }

    /**
     * {@code PUT  /franchise-user-status-histories/:id} : Updates an existing franchiseUserStatusHistory.
     *
     * @param id the id of the franchiseUserStatusHistoryDTO to save.
     * @param franchiseUserStatusHistoryDTO the franchiseUserStatusHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseUserStatusHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the franchiseUserStatusHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the franchiseUserStatusHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FranchiseUserStatusHistoryDTO> updateFranchiseUserStatusHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update FranchiseUserStatusHistory : {}, {}", id, franchiseUserStatusHistoryDTO);
        if (franchiseUserStatusHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseUserStatusHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseUserStatusHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        franchiseUserStatusHistoryDTO = franchiseUserStatusHistoryService.update(franchiseUserStatusHistoryDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseUserStatusHistoryDTO.getId().toString())
            )
            .body(franchiseUserStatusHistoryDTO);
    }

    /**
     * {@code PATCH  /franchise-user-status-histories/:id} : Partial updates given fields of an existing franchiseUserStatusHistory, field will ignore if it is null
     *
     * @param id the id of the franchiseUserStatusHistoryDTO to save.
     * @param franchiseUserStatusHistoryDTO the franchiseUserStatusHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseUserStatusHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the franchiseUserStatusHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the franchiseUserStatusHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the franchiseUserStatusHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FranchiseUserStatusHistoryDTO> partialUpdateFranchiseUserStatusHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FranchiseUserStatusHistory partially : {}, {}", id, franchiseUserStatusHistoryDTO);
        if (franchiseUserStatusHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseUserStatusHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseUserStatusHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FranchiseUserStatusHistoryDTO> result = franchiseUserStatusHistoryService.partialUpdate(franchiseUserStatusHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseUserStatusHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /franchise-user-status-histories} : get all the franchiseUserStatusHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of franchiseUserStatusHistories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FranchiseUserStatusHistoryDTO>> getAllFranchiseUserStatusHistories(
        FranchiseUserStatusHistoryCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get FranchiseUserStatusHistories by criteria: {}", criteria);

        Page<FranchiseUserStatusHistoryDTO> page = franchiseUserStatusHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /franchise-user-status-histories/count} : count all the franchiseUserStatusHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFranchiseUserStatusHistories(FranchiseUserStatusHistoryCriteria criteria) {
        LOG.debug("REST request to count FranchiseUserStatusHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(franchiseUserStatusHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /franchise-user-status-histories/:id} : get the "id" franchiseUserStatusHistory.
     *
     * @param id the id of the franchiseUserStatusHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the franchiseUserStatusHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FranchiseUserStatusHistoryDTO> getFranchiseUserStatusHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FranchiseUserStatusHistory : {}", id);
        Optional<FranchiseUserStatusHistoryDTO> franchiseUserStatusHistoryDTO = franchiseUserStatusHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(franchiseUserStatusHistoryDTO);
    }

    /**
     * {@code DELETE  /franchise-user-status-histories/:id} : delete the "id" franchiseUserStatusHistory.
     *
     * @param id the id of the franchiseUserStatusHistoryDTO to delete.
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
