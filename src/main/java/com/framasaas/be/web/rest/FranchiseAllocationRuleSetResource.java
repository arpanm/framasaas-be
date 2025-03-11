package com.framasaas.be.web.rest;

import com.framasaas.be.domain.FranchiseAllocationRuleSet;
import com.framasaas.be.repository.FranchiseAllocationRuleSetRepository;
import com.framasaas.be.service.FranchiseAllocationRuleSetService;
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
 * REST controller for managing {@link com.framasaas.be.domain.FranchiseAllocationRuleSet}.
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

    public FranchiseAllocationRuleSetResource(
        FranchiseAllocationRuleSetService franchiseAllocationRuleSetService,
        FranchiseAllocationRuleSetRepository franchiseAllocationRuleSetRepository
    ) {
        this.franchiseAllocationRuleSetService = franchiseAllocationRuleSetService;
        this.franchiseAllocationRuleSetRepository = franchiseAllocationRuleSetRepository;
    }

    /**
     * {@code POST  /franchise-allocation-rule-sets} : Create a new franchiseAllocationRuleSet.
     *
     * @param franchiseAllocationRuleSet the franchiseAllocationRuleSet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new franchiseAllocationRuleSet, or with status {@code 400 (Bad Request)} if the franchiseAllocationRuleSet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FranchiseAllocationRuleSet> createFranchiseAllocationRuleSet(
        @Valid @RequestBody FranchiseAllocationRuleSet franchiseAllocationRuleSet
    ) throws URISyntaxException {
        LOG.debug("REST request to save FranchiseAllocationRuleSet : {}", franchiseAllocationRuleSet);
        if (franchiseAllocationRuleSet.getId() != null) {
            throw new BadRequestAlertException("A new franchiseAllocationRuleSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        franchiseAllocationRuleSet = franchiseAllocationRuleSetService.save(franchiseAllocationRuleSet);
        return ResponseEntity.created(new URI("/api/franchise-allocation-rule-sets/" + franchiseAllocationRuleSet.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, franchiseAllocationRuleSet.getId().toString())
            )
            .body(franchiseAllocationRuleSet);
    }

    /**
     * {@code PUT  /franchise-allocation-rule-sets/:id} : Updates an existing franchiseAllocationRuleSet.
     *
     * @param id the id of the franchiseAllocationRuleSet to save.
     * @param franchiseAllocationRuleSet the franchiseAllocationRuleSet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseAllocationRuleSet,
     * or with status {@code 400 (Bad Request)} if the franchiseAllocationRuleSet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the franchiseAllocationRuleSet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FranchiseAllocationRuleSet> updateFranchiseAllocationRuleSet(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FranchiseAllocationRuleSet franchiseAllocationRuleSet
    ) throws URISyntaxException {
        LOG.debug("REST request to update FranchiseAllocationRuleSet : {}, {}", id, franchiseAllocationRuleSet);
        if (franchiseAllocationRuleSet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseAllocationRuleSet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseAllocationRuleSetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        franchiseAllocationRuleSet = franchiseAllocationRuleSetService.update(franchiseAllocationRuleSet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseAllocationRuleSet.getId().toString()))
            .body(franchiseAllocationRuleSet);
    }

    /**
     * {@code PATCH  /franchise-allocation-rule-sets/:id} : Partial updates given fields of an existing franchiseAllocationRuleSet, field will ignore if it is null
     *
     * @param id the id of the franchiseAllocationRuleSet to save.
     * @param franchiseAllocationRuleSet the franchiseAllocationRuleSet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseAllocationRuleSet,
     * or with status {@code 400 (Bad Request)} if the franchiseAllocationRuleSet is not valid,
     * or with status {@code 404 (Not Found)} if the franchiseAllocationRuleSet is not found,
     * or with status {@code 500 (Internal Server Error)} if the franchiseAllocationRuleSet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FranchiseAllocationRuleSet> partialUpdateFranchiseAllocationRuleSet(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FranchiseAllocationRuleSet franchiseAllocationRuleSet
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FranchiseAllocationRuleSet partially : {}, {}", id, franchiseAllocationRuleSet);
        if (franchiseAllocationRuleSet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseAllocationRuleSet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseAllocationRuleSetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FranchiseAllocationRuleSet> result = franchiseAllocationRuleSetService.partialUpdate(franchiseAllocationRuleSet);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseAllocationRuleSet.getId().toString())
        );
    }

    /**
     * {@code GET  /franchise-allocation-rule-sets} : get all the franchiseAllocationRuleSets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of franchiseAllocationRuleSets in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FranchiseAllocationRuleSet>> getAllFranchiseAllocationRuleSets(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of FranchiseAllocationRuleSets");
        Page<FranchiseAllocationRuleSet> page = franchiseAllocationRuleSetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /franchise-allocation-rule-sets/:id} : get the "id" franchiseAllocationRuleSet.
     *
     * @param id the id of the franchiseAllocationRuleSet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the franchiseAllocationRuleSet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FranchiseAllocationRuleSet> getFranchiseAllocationRuleSet(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FranchiseAllocationRuleSet : {}", id);
        Optional<FranchiseAllocationRuleSet> franchiseAllocationRuleSet = franchiseAllocationRuleSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(franchiseAllocationRuleSet);
    }

    /**
     * {@code DELETE  /franchise-allocation-rule-sets/:id} : delete the "id" franchiseAllocationRuleSet.
     *
     * @param id the id of the franchiseAllocationRuleSet to delete.
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
