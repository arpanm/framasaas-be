package com.framasaas.be.web.rest;

import com.framasaas.be.domain.Franchise;
import com.framasaas.be.repository.FranchiseRepository;
import com.framasaas.be.service.FranchiseService;
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
 * REST controller for managing {@link com.framasaas.be.domain.Franchise}.
 */
@RestController
@RequestMapping("/api/franchises")
public class FranchiseResource {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseResource.class);

    private static final String ENTITY_NAME = "franchise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FranchiseService franchiseService;

    private final FranchiseRepository franchiseRepository;

    public FranchiseResource(FranchiseService franchiseService, FranchiseRepository franchiseRepository) {
        this.franchiseService = franchiseService;
        this.franchiseRepository = franchiseRepository;
    }

    /**
     * {@code POST  /franchises} : Create a new franchise.
     *
     * @param franchise the franchise to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new franchise, or with status {@code 400 (Bad Request)} if the franchise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Franchise> createFranchise(@Valid @RequestBody Franchise franchise) throws URISyntaxException {
        LOG.debug("REST request to save Franchise : {}", franchise);
        if (franchise.getId() != null) {
            throw new BadRequestAlertException("A new franchise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        franchise = franchiseService.save(franchise);
        return ResponseEntity.created(new URI("/api/franchises/" + franchise.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, franchise.getId().toString()))
            .body(franchise);
    }

    /**
     * {@code PUT  /franchises/:id} : Updates an existing franchise.
     *
     * @param id the id of the franchise to save.
     * @param franchise the franchise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchise,
     * or with status {@code 400 (Bad Request)} if the franchise is not valid,
     * or with status {@code 500 (Internal Server Error)} if the franchise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Franchise> updateFranchise(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Franchise franchise
    ) throws URISyntaxException {
        LOG.debug("REST request to update Franchise : {}, {}", id, franchise);
        if (franchise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchise.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        franchise = franchiseService.update(franchise);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchise.getId().toString()))
            .body(franchise);
    }

    /**
     * {@code PATCH  /franchises/:id} : Partial updates given fields of an existing franchise, field will ignore if it is null
     *
     * @param id the id of the franchise to save.
     * @param franchise the franchise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchise,
     * or with status {@code 400 (Bad Request)} if the franchise is not valid,
     * or with status {@code 404 (Not Found)} if the franchise is not found,
     * or with status {@code 500 (Internal Server Error)} if the franchise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Franchise> partialUpdateFranchise(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Franchise franchise
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Franchise partially : {}, {}", id, franchise);
        if (franchise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchise.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Franchise> result = franchiseService.partialUpdate(franchise);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchise.getId().toString())
        );
    }

    /**
     * {@code GET  /franchises} : get all the franchises.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of franchises in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Franchise>> getAllFranchises(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Franchises");
        Page<Franchise> page = franchiseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /franchises/:id} : get the "id" franchise.
     *
     * @param id the id of the franchise to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the franchise, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Franchise> getFranchise(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Franchise : {}", id);
        Optional<Franchise> franchise = franchiseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(franchise);
    }

    /**
     * {@code DELETE  /franchises/:id} : delete the "id" franchise.
     *
     * @param id the id of the franchise to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFranchise(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Franchise : {}", id);
        franchiseService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
