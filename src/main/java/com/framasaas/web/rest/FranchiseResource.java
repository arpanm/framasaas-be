package com.framasaas.web.rest;

import com.framasaas.repository.FranchiseRepository;
import com.framasaas.service.FranchiseQueryService;
import com.framasaas.service.FranchiseService;
import com.framasaas.service.criteria.FranchiseCriteria;
import com.framasaas.service.dto.FranchiseDTO;
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
 * REST controller for managing {@link com.framasaas.domain.Franchise}.
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

    private final FranchiseQueryService franchiseQueryService;

    public FranchiseResource(
        FranchiseService franchiseService,
        FranchiseRepository franchiseRepository,
        FranchiseQueryService franchiseQueryService
    ) {
        this.franchiseService = franchiseService;
        this.franchiseRepository = franchiseRepository;
        this.franchiseQueryService = franchiseQueryService;
    }

    /**
     * {@code POST  /franchises} : Create a new franchise.
     *
     * @param franchiseDTO the franchiseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new franchiseDTO, or with status {@code 400 (Bad Request)} if the franchise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FranchiseDTO> createFranchise(@Valid @RequestBody FranchiseDTO franchiseDTO) throws URISyntaxException {
        LOG.debug("REST request to save Franchise : {}", franchiseDTO);
        if (franchiseDTO.getId() != null) {
            throw new BadRequestAlertException("A new franchise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        franchiseDTO = franchiseService.save(franchiseDTO);
        return ResponseEntity.created(new URI("/api/franchises/" + franchiseDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, franchiseDTO.getId().toString()))
            .body(franchiseDTO);
    }

    /**
     * {@code PUT  /franchises/:id} : Updates an existing franchise.
     *
     * @param id the id of the franchiseDTO to save.
     * @param franchiseDTO the franchiseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseDTO,
     * or with status {@code 400 (Bad Request)} if the franchiseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the franchiseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FranchiseDTO> updateFranchise(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FranchiseDTO franchiseDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Franchise : {}, {}", id, franchiseDTO);
        if (franchiseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        franchiseDTO = franchiseService.update(franchiseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseDTO.getId().toString()))
            .body(franchiseDTO);
    }

    /**
     * {@code PATCH  /franchises/:id} : Partial updates given fields of an existing franchise, field will ignore if it is null
     *
     * @param id the id of the franchiseDTO to save.
     * @param franchiseDTO the franchiseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseDTO,
     * or with status {@code 400 (Bad Request)} if the franchiseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the franchiseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the franchiseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FranchiseDTO> partialUpdateFranchise(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FranchiseDTO franchiseDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Franchise partially : {}, {}", id, franchiseDTO);
        if (franchiseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FranchiseDTO> result = franchiseService.partialUpdate(franchiseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /franchises} : get all the franchises.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of franchises in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FranchiseDTO>> getAllFranchises(
        FranchiseCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Franchises by criteria: {}", criteria);

        Page<FranchiseDTO> page = franchiseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /franchises/count} : count all the franchises.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFranchises(FranchiseCriteria criteria) {
        LOG.debug("REST request to count Franchises by criteria: {}", criteria);
        return ResponseEntity.ok().body(franchiseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /franchises/:id} : get the "id" franchise.
     *
     * @param id the id of the franchiseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the franchiseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FranchiseDTO> getFranchise(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Franchise : {}", id);
        Optional<FranchiseDTO> franchiseDTO = franchiseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(franchiseDTO);
    }

    /**
     * {@code DELETE  /franchises/:id} : delete the "id" franchise.
     *
     * @param id the id of the franchiseDTO to delete.
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
