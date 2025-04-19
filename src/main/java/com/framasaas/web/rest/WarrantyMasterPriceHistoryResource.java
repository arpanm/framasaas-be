package com.framasaas.web.rest;

import com.framasaas.repository.WarrantyMasterPriceHistoryRepository;
import com.framasaas.service.WarrantyMasterPriceHistoryQueryService;
import com.framasaas.service.WarrantyMasterPriceHistoryService;
import com.framasaas.service.criteria.WarrantyMasterPriceHistoryCriteria;
import com.framasaas.service.dto.WarrantyMasterPriceHistoryDTO;
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
 * REST controller for managing {@link com.framasaas.domain.WarrantyMasterPriceHistory}.
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

    private final WarrantyMasterPriceHistoryQueryService warrantyMasterPriceHistoryQueryService;

    public WarrantyMasterPriceHistoryResource(
        WarrantyMasterPriceHistoryService warrantyMasterPriceHistoryService,
        WarrantyMasterPriceHistoryRepository warrantyMasterPriceHistoryRepository,
        WarrantyMasterPriceHistoryQueryService warrantyMasterPriceHistoryQueryService
    ) {
        this.warrantyMasterPriceHistoryService = warrantyMasterPriceHistoryService;
        this.warrantyMasterPriceHistoryRepository = warrantyMasterPriceHistoryRepository;
        this.warrantyMasterPriceHistoryQueryService = warrantyMasterPriceHistoryQueryService;
    }

    /**
     * {@code POST  /warranty-master-price-histories} : Create a new warrantyMasterPriceHistory.
     *
     * @param warrantyMasterPriceHistoryDTO the warrantyMasterPriceHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new warrantyMasterPriceHistoryDTO, or with status {@code 400 (Bad Request)} if the warrantyMasterPriceHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<WarrantyMasterPriceHistoryDTO> createWarrantyMasterPriceHistory(
        @Valid @RequestBody WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save WarrantyMasterPriceHistory : {}", warrantyMasterPriceHistoryDTO);
        if (warrantyMasterPriceHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new warrantyMasterPriceHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        warrantyMasterPriceHistoryDTO = warrantyMasterPriceHistoryService.save(warrantyMasterPriceHistoryDTO);
        return ResponseEntity.created(new URI("/api/warranty-master-price-histories/" + warrantyMasterPriceHistoryDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, warrantyMasterPriceHistoryDTO.getId().toString())
            )
            .body(warrantyMasterPriceHistoryDTO);
    }

    /**
     * {@code PUT  /warranty-master-price-histories/:id} : Updates an existing warrantyMasterPriceHistory.
     *
     * @param id the id of the warrantyMasterPriceHistoryDTO to save.
     * @param warrantyMasterPriceHistoryDTO the warrantyMasterPriceHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated warrantyMasterPriceHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the warrantyMasterPriceHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the warrantyMasterPriceHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<WarrantyMasterPriceHistoryDTO> updateWarrantyMasterPriceHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update WarrantyMasterPriceHistory : {}, {}", id, warrantyMasterPriceHistoryDTO);
        if (warrantyMasterPriceHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, warrantyMasterPriceHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!warrantyMasterPriceHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        warrantyMasterPriceHistoryDTO = warrantyMasterPriceHistoryService.update(warrantyMasterPriceHistoryDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, warrantyMasterPriceHistoryDTO.getId().toString())
            )
            .body(warrantyMasterPriceHistoryDTO);
    }

    /**
     * {@code PATCH  /warranty-master-price-histories/:id} : Partial updates given fields of an existing warrantyMasterPriceHistory, field will ignore if it is null
     *
     * @param id the id of the warrantyMasterPriceHistoryDTO to save.
     * @param warrantyMasterPriceHistoryDTO the warrantyMasterPriceHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated warrantyMasterPriceHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the warrantyMasterPriceHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the warrantyMasterPriceHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the warrantyMasterPriceHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WarrantyMasterPriceHistoryDTO> partialUpdateWarrantyMasterPriceHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update WarrantyMasterPriceHistory partially : {}, {}", id, warrantyMasterPriceHistoryDTO);
        if (warrantyMasterPriceHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, warrantyMasterPriceHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!warrantyMasterPriceHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WarrantyMasterPriceHistoryDTO> result = warrantyMasterPriceHistoryService.partialUpdate(warrantyMasterPriceHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, warrantyMasterPriceHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /warranty-master-price-histories} : get all the warrantyMasterPriceHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of warrantyMasterPriceHistories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<WarrantyMasterPriceHistoryDTO>> getAllWarrantyMasterPriceHistories(
        WarrantyMasterPriceHistoryCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get WarrantyMasterPriceHistories by criteria: {}", criteria);

        Page<WarrantyMasterPriceHistoryDTO> page = warrantyMasterPriceHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /warranty-master-price-histories/count} : count all the warrantyMasterPriceHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countWarrantyMasterPriceHistories(WarrantyMasterPriceHistoryCriteria criteria) {
        LOG.debug("REST request to count WarrantyMasterPriceHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(warrantyMasterPriceHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /warranty-master-price-histories/:id} : get the "id" warrantyMasterPriceHistory.
     *
     * @param id the id of the warrantyMasterPriceHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the warrantyMasterPriceHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<WarrantyMasterPriceHistoryDTO> getWarrantyMasterPriceHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to get WarrantyMasterPriceHistory : {}", id);
        Optional<WarrantyMasterPriceHistoryDTO> warrantyMasterPriceHistoryDTO = warrantyMasterPriceHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(warrantyMasterPriceHistoryDTO);
    }

    /**
     * {@code DELETE  /warranty-master-price-histories/:id} : delete the "id" warrantyMasterPriceHistory.
     *
     * @param id the id of the warrantyMasterPriceHistoryDTO to delete.
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
