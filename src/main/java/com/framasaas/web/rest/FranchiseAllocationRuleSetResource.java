package com.framasaas.web.rest;

import com.framasaas.repository.FranchiseAllocationRuleSetRepository;
import com.framasaas.service.FranchiseAllocationRuleSetQueryService;
import com.framasaas.service.FranchiseAllocationRuleSetService;
import com.framasaas.service.criteria.FranchiseAllocationRuleSetCriteria;
import com.framasaas.service.dto.FranchiseAllocationRuleSetDTO;
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
 * REST controller for managing {@link com.framasaas.domain.FranchiseAllocationRuleSet}.
 */
@RestController
@RequestMapping("/api/franchise-allocation-rule-sets")
public class FranchiseAllocationRuleSetResource {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseAllocationRuleSetResource.class);

    private static final String ENTITY_NAME = "franchiseAllocationRuleSet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FranchiseAllocationRuleSetService franchiseAllocationRuleSetService;

    private final FranchiseAllocationRuleSetRepository franchiseAllocationRuleSetRepository;

    private final FranchiseAllocationRuleSetQueryService franchiseAllocationRuleSetQueryService;

    public FranchiseAllocationRuleSetResource(
        FranchiseAllocationRuleSetService franchiseAllocationRuleSetService,
        FranchiseAllocationRuleSetRepository franchiseAllocationRuleSetRepository,
        FranchiseAllocationRuleSetQueryService franchiseAllocationRuleSetQueryService
    ) {
        this.franchiseAllocationRuleSetService = franchiseAllocationRuleSetService;
        this.franchiseAllocationRuleSetRepository = franchiseAllocationRuleSetRepository;
        this.franchiseAllocationRuleSetQueryService = franchiseAllocationRuleSetQueryService;
    }

    /**
     * {@code POST  /franchise-allocation-rule-sets} : Create a new franchiseAllocationRuleSet.
     *
     * @param franchiseAllocationRuleSetDTO the franchiseAllocationRuleSetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new franchiseAllocationRuleSetDTO, or with status {@code 400 (Bad Request)} if the franchiseAllocationRuleSet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FranchiseAllocationRuleSetDTO> createFranchiseAllocationRuleSet(
        @Valid @RequestBody FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save FranchiseAllocationRuleSet : {}", franchiseAllocationRuleSetDTO);
        if (franchiseAllocationRuleSetDTO.getId() != null) {
            throw new BadRequestAlertException("A new franchiseAllocationRuleSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        franchiseAllocationRuleSetDTO = franchiseAllocationRuleSetService.save(franchiseAllocationRuleSetDTO);
        return ResponseEntity.created(new URI("/api/franchise-allocation-rule-sets/" + franchiseAllocationRuleSetDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, franchiseAllocationRuleSetDTO.getId().toString())
            )
            .body(franchiseAllocationRuleSetDTO);
    }

    /**
     * {@code PUT  /franchise-allocation-rule-sets/:id} : Updates an existing franchiseAllocationRuleSet.
     *
     * @param id the id of the franchiseAllocationRuleSetDTO to save.
     * @param franchiseAllocationRuleSetDTO the franchiseAllocationRuleSetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseAllocationRuleSetDTO,
     * or with status {@code 400 (Bad Request)} if the franchiseAllocationRuleSetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the franchiseAllocationRuleSetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FranchiseAllocationRuleSetDTO> updateFranchiseAllocationRuleSet(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update FranchiseAllocationRuleSet : {}, {}", id, franchiseAllocationRuleSetDTO);
        if (franchiseAllocationRuleSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseAllocationRuleSetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseAllocationRuleSetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        franchiseAllocationRuleSetDTO = franchiseAllocationRuleSetService.update(franchiseAllocationRuleSetDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseAllocationRuleSetDTO.getId().toString())
            )
            .body(franchiseAllocationRuleSetDTO);
    }

    /**
     * {@code PATCH  /franchise-allocation-rule-sets/:id} : Partial updates given fields of an existing franchiseAllocationRuleSet, field will ignore if it is null
     *
     * @param id the id of the franchiseAllocationRuleSetDTO to save.
     * @param franchiseAllocationRuleSetDTO the franchiseAllocationRuleSetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseAllocationRuleSetDTO,
     * or with status {@code 400 (Bad Request)} if the franchiseAllocationRuleSetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the franchiseAllocationRuleSetDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the franchiseAllocationRuleSetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FranchiseAllocationRuleSetDTO> partialUpdateFranchiseAllocationRuleSet(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FranchiseAllocationRuleSet partially : {}, {}", id, franchiseAllocationRuleSetDTO);
        if (franchiseAllocationRuleSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseAllocationRuleSetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseAllocationRuleSetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FranchiseAllocationRuleSetDTO> result = franchiseAllocationRuleSetService.partialUpdate(franchiseAllocationRuleSetDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseAllocationRuleSetDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /franchise-allocation-rule-sets} : get all the franchiseAllocationRuleSets.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of franchiseAllocationRuleSets in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FranchiseAllocationRuleSetDTO>> getAllFranchiseAllocationRuleSets(
        FranchiseAllocationRuleSetCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get FranchiseAllocationRuleSets by criteria: {}", criteria);

        Page<FranchiseAllocationRuleSetDTO> page = franchiseAllocationRuleSetQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /franchise-allocation-rule-sets/count} : count all the franchiseAllocationRuleSets.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFranchiseAllocationRuleSets(FranchiseAllocationRuleSetCriteria criteria) {
        LOG.debug("REST request to count FranchiseAllocationRuleSets by criteria: {}", criteria);
        return ResponseEntity.ok().body(franchiseAllocationRuleSetQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /franchise-allocation-rule-sets/:id} : get the "id" franchiseAllocationRuleSet.
     *
     * @param id the id of the franchiseAllocationRuleSetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the franchiseAllocationRuleSetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FranchiseAllocationRuleSetDTO> getFranchiseAllocationRuleSet(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FranchiseAllocationRuleSet : {}", id);
        Optional<FranchiseAllocationRuleSetDTO> franchiseAllocationRuleSetDTO = franchiseAllocationRuleSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(franchiseAllocationRuleSetDTO);
    }

    /**
     * {@code DELETE  /franchise-allocation-rule-sets/:id} : delete the "id" franchiseAllocationRuleSet.
     *
     * @param id the id of the franchiseAllocationRuleSetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFranchiseAllocationRuleSet(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FranchiseAllocationRuleSet : {}", id);
        franchiseAllocationRuleSetService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
