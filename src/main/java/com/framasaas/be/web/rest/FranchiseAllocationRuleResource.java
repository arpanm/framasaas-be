package com.framasaas.be.web.rest;

import com.framasaas.be.domain.FranchiseAllocationRule;
import com.framasaas.be.repository.FranchiseAllocationRuleRepository;
import com.framasaas.be.service.FranchiseAllocationRuleService;
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
 * REST controller for managing {@link com.framasaas.be.domain.FranchiseAllocationRule}.
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

    public FranchiseAllocationRuleResource(
        FranchiseAllocationRuleService franchiseAllocationRuleService,
        FranchiseAllocationRuleRepository franchiseAllocationRuleRepository
    ) {
        this.franchiseAllocationRuleService = franchiseAllocationRuleService;
        this.franchiseAllocationRuleRepository = franchiseAllocationRuleRepository;
    }

    /**
     * {@code POST  /franchise-allocation-rules} : Create a new franchiseAllocationRule.
     *
     * @param franchiseAllocationRule the franchiseAllocationRule to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new franchiseAllocationRule, or with status {@code 400 (Bad Request)} if the franchiseAllocationRule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FranchiseAllocationRule> createFranchiseAllocationRule(
        @Valid @RequestBody FranchiseAllocationRule franchiseAllocationRule
    ) throws URISyntaxException {
        LOG.debug("REST request to save FranchiseAllocationRule : {}", franchiseAllocationRule);
        if (franchiseAllocationRule.getId() != null) {
            throw new BadRequestAlertException("A new franchiseAllocationRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        franchiseAllocationRule = franchiseAllocationRuleService.save(franchiseAllocationRule);
        return ResponseEntity.created(new URI("/api/franchise-allocation-rules/" + franchiseAllocationRule.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, franchiseAllocationRule.getId().toString()))
            .body(franchiseAllocationRule);
    }

    /**
     * {@code PUT  /franchise-allocation-rules/:id} : Updates an existing franchiseAllocationRule.
     *
     * @param id the id of the franchiseAllocationRule to save.
     * @param franchiseAllocationRule the franchiseAllocationRule to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseAllocationRule,
     * or with status {@code 400 (Bad Request)} if the franchiseAllocationRule is not valid,
     * or with status {@code 500 (Internal Server Error)} if the franchiseAllocationRule couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FranchiseAllocationRule> updateFranchiseAllocationRule(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FranchiseAllocationRule franchiseAllocationRule
    ) throws URISyntaxException {
        LOG.debug("REST request to update FranchiseAllocationRule : {}, {}", id, franchiseAllocationRule);
        if (franchiseAllocationRule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseAllocationRule.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseAllocationRuleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        franchiseAllocationRule = franchiseAllocationRuleService.update(franchiseAllocationRule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseAllocationRule.getId().toString()))
            .body(franchiseAllocationRule);
    }

    /**
     * {@code PATCH  /franchise-allocation-rules/:id} : Partial updates given fields of an existing franchiseAllocationRule, field will ignore if it is null
     *
     * @param id the id of the franchiseAllocationRule to save.
     * @param franchiseAllocationRule the franchiseAllocationRule to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseAllocationRule,
     * or with status {@code 400 (Bad Request)} if the franchiseAllocationRule is not valid,
     * or with status {@code 404 (Not Found)} if the franchiseAllocationRule is not found,
     * or with status {@code 500 (Internal Server Error)} if the franchiseAllocationRule couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FranchiseAllocationRule> partialUpdateFranchiseAllocationRule(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FranchiseAllocationRule franchiseAllocationRule
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FranchiseAllocationRule partially : {}, {}", id, franchiseAllocationRule);
        if (franchiseAllocationRule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseAllocationRule.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseAllocationRuleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FranchiseAllocationRule> result = franchiseAllocationRuleService.partialUpdate(franchiseAllocationRule);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseAllocationRule.getId().toString())
        );
    }

    /**
     * {@code GET  /franchise-allocation-rules} : get all the franchiseAllocationRules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of franchiseAllocationRules in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FranchiseAllocationRule>> getAllFranchiseAllocationRules(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of FranchiseAllocationRules");
        Page<FranchiseAllocationRule> page = franchiseAllocationRuleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /franchise-allocation-rules/:id} : get the "id" franchiseAllocationRule.
     *
     * @param id the id of the franchiseAllocationRule to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the franchiseAllocationRule, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FranchiseAllocationRule> getFranchiseAllocationRule(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FranchiseAllocationRule : {}", id);
        Optional<FranchiseAllocationRule> franchiseAllocationRule = franchiseAllocationRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(franchiseAllocationRule);
    }

    /**
     * {@code DELETE  /franchise-allocation-rules/:id} : delete the "id" franchiseAllocationRule.
     *
     * @param id the id of the franchiseAllocationRule to delete.
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
