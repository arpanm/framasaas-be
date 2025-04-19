package com.framasaas.web.rest;

import com.framasaas.repository.FranchiseAllocationRuleRepository;
import com.framasaas.service.FranchiseAllocationRuleQueryService;
import com.framasaas.service.FranchiseAllocationRuleService;
import com.framasaas.service.criteria.FranchiseAllocationRuleCriteria;
import com.framasaas.service.dto.FranchiseAllocationRuleDTO;
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
 * REST controller for managing {@link com.framasaas.domain.FranchiseAllocationRule}.
 */
@RestController
@RequestMapping("/api/franchise-allocation-rules")
public class FranchiseAllocationRuleResource {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseAllocationRuleResource.class);

    private static final String ENTITY_NAME = "franchiseAllocationRule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FranchiseAllocationRuleService franchiseAllocationRuleService;

    private final FranchiseAllocationRuleRepository franchiseAllocationRuleRepository;

    private final FranchiseAllocationRuleQueryService franchiseAllocationRuleQueryService;

    public FranchiseAllocationRuleResource(
        FranchiseAllocationRuleService franchiseAllocationRuleService,
        FranchiseAllocationRuleRepository franchiseAllocationRuleRepository,
        FranchiseAllocationRuleQueryService franchiseAllocationRuleQueryService
    ) {
        this.franchiseAllocationRuleService = franchiseAllocationRuleService;
        this.franchiseAllocationRuleRepository = franchiseAllocationRuleRepository;
        this.franchiseAllocationRuleQueryService = franchiseAllocationRuleQueryService;
    }

    /**
     * {@code POST  /franchise-allocation-rules} : Create a new franchiseAllocationRule.
     *
     * @param franchiseAllocationRuleDTO the franchiseAllocationRuleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new franchiseAllocationRuleDTO, or with status {@code 400 (Bad Request)} if the franchiseAllocationRule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FranchiseAllocationRuleDTO> createFranchiseAllocationRule(
        @Valid @RequestBody FranchiseAllocationRuleDTO franchiseAllocationRuleDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save FranchiseAllocationRule : {}", franchiseAllocationRuleDTO);
        if (franchiseAllocationRuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new franchiseAllocationRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        franchiseAllocationRuleDTO = franchiseAllocationRuleService.save(franchiseAllocationRuleDTO);
        return ResponseEntity.created(new URI("/api/franchise-allocation-rules/" + franchiseAllocationRuleDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, franchiseAllocationRuleDTO.getId().toString())
            )
            .body(franchiseAllocationRuleDTO);
    }

    /**
     * {@code PUT  /franchise-allocation-rules/:id} : Updates an existing franchiseAllocationRule.
     *
     * @param id the id of the franchiseAllocationRuleDTO to save.
     * @param franchiseAllocationRuleDTO the franchiseAllocationRuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseAllocationRuleDTO,
     * or with status {@code 400 (Bad Request)} if the franchiseAllocationRuleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the franchiseAllocationRuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FranchiseAllocationRuleDTO> updateFranchiseAllocationRule(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FranchiseAllocationRuleDTO franchiseAllocationRuleDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update FranchiseAllocationRule : {}, {}", id, franchiseAllocationRuleDTO);
        if (franchiseAllocationRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseAllocationRuleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseAllocationRuleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        franchiseAllocationRuleDTO = franchiseAllocationRuleService.update(franchiseAllocationRuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseAllocationRuleDTO.getId().toString()))
            .body(franchiseAllocationRuleDTO);
    }

    /**
     * {@code PATCH  /franchise-allocation-rules/:id} : Partial updates given fields of an existing franchiseAllocationRule, field will ignore if it is null
     *
     * @param id the id of the franchiseAllocationRuleDTO to save.
     * @param franchiseAllocationRuleDTO the franchiseAllocationRuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseAllocationRuleDTO,
     * or with status {@code 400 (Bad Request)} if the franchiseAllocationRuleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the franchiseAllocationRuleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the franchiseAllocationRuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FranchiseAllocationRuleDTO> partialUpdateFranchiseAllocationRule(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FranchiseAllocationRuleDTO franchiseAllocationRuleDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FranchiseAllocationRule partially : {}, {}", id, franchiseAllocationRuleDTO);
        if (franchiseAllocationRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseAllocationRuleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseAllocationRuleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FranchiseAllocationRuleDTO> result = franchiseAllocationRuleService.partialUpdate(franchiseAllocationRuleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseAllocationRuleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /franchise-allocation-rules} : get all the franchiseAllocationRules.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of franchiseAllocationRules in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FranchiseAllocationRuleDTO>> getAllFranchiseAllocationRules(
        FranchiseAllocationRuleCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get FranchiseAllocationRules by criteria: {}", criteria);

        Page<FranchiseAllocationRuleDTO> page = franchiseAllocationRuleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /franchise-allocation-rules/count} : count all the franchiseAllocationRules.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFranchiseAllocationRules(FranchiseAllocationRuleCriteria criteria) {
        LOG.debug("REST request to count FranchiseAllocationRules by criteria: {}", criteria);
        return ResponseEntity.ok().body(franchiseAllocationRuleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /franchise-allocation-rules/:id} : get the "id" franchiseAllocationRule.
     *
     * @param id the id of the franchiseAllocationRuleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the franchiseAllocationRuleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FranchiseAllocationRuleDTO> getFranchiseAllocationRule(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FranchiseAllocationRule : {}", id);
        Optional<FranchiseAllocationRuleDTO> franchiseAllocationRuleDTO = franchiseAllocationRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(franchiseAllocationRuleDTO);
    }

    /**
     * {@code DELETE  /franchise-allocation-rules/:id} : delete the "id" franchiseAllocationRule.
     *
     * @param id the id of the franchiseAllocationRuleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFranchiseAllocationRule(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FranchiseAllocationRule : {}", id);
        franchiseAllocationRuleService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
