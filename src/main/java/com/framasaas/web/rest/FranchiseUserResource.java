package com.framasaas.web.rest;

import com.framasaas.repository.FranchiseUserRepository;
import com.framasaas.service.FranchiseUserQueryService;
import com.framasaas.service.FranchiseUserService;
import com.framasaas.service.criteria.FranchiseUserCriteria;
import com.framasaas.service.dto.FranchiseUserDTO;
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
 * REST controller for managing {@link com.framasaas.domain.FranchiseUser}.
 */
@RestController
@RequestMapping("/api/franchise-users")
public class FranchiseUserResource {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseUserResource.class);

    private static final String ENTITY_NAME = "franchiseUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FranchiseUserService franchiseUserService;

    private final FranchiseUserRepository franchiseUserRepository;

    private final FranchiseUserQueryService franchiseUserQueryService;

    public FranchiseUserResource(
        FranchiseUserService franchiseUserService,
        FranchiseUserRepository franchiseUserRepository,
        FranchiseUserQueryService franchiseUserQueryService
    ) {
        this.franchiseUserService = franchiseUserService;
        this.franchiseUserRepository = franchiseUserRepository;
        this.franchiseUserQueryService = franchiseUserQueryService;
    }

    /**
     * {@code POST  /franchise-users} : Create a new franchiseUser.
     *
     * @param franchiseUserDTO the franchiseUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new franchiseUserDTO, or with status {@code 400 (Bad Request)} if the franchiseUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FranchiseUserDTO> createFranchiseUser(@Valid @RequestBody FranchiseUserDTO franchiseUserDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save FranchiseUser : {}", franchiseUserDTO);
        if (franchiseUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new franchiseUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        franchiseUserDTO = franchiseUserService.save(franchiseUserDTO);
        return ResponseEntity.created(new URI("/api/franchise-users/" + franchiseUserDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, franchiseUserDTO.getId().toString()))
            .body(franchiseUserDTO);
    }

    /**
     * {@code PUT  /franchise-users/:id} : Updates an existing franchiseUser.
     *
     * @param id the id of the franchiseUserDTO to save.
     * @param franchiseUserDTO the franchiseUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseUserDTO,
     * or with status {@code 400 (Bad Request)} if the franchiseUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the franchiseUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FranchiseUserDTO> updateFranchiseUser(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FranchiseUserDTO franchiseUserDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update FranchiseUser : {}, {}", id, franchiseUserDTO);
        if (franchiseUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        franchiseUserDTO = franchiseUserService.update(franchiseUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseUserDTO.getId().toString()))
            .body(franchiseUserDTO);
    }

    /**
     * {@code PATCH  /franchise-users/:id} : Partial updates given fields of an existing franchiseUser, field will ignore if it is null
     *
     * @param id the id of the franchiseUserDTO to save.
     * @param franchiseUserDTO the franchiseUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseUserDTO,
     * or with status {@code 400 (Bad Request)} if the franchiseUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the franchiseUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the franchiseUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FranchiseUserDTO> partialUpdateFranchiseUser(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FranchiseUserDTO franchiseUserDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FranchiseUser partially : {}, {}", id, franchiseUserDTO);
        if (franchiseUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FranchiseUserDTO> result = franchiseUserService.partialUpdate(franchiseUserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseUserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /franchise-users} : get all the franchiseUsers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of franchiseUsers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FranchiseUserDTO>> getAllFranchiseUsers(
        FranchiseUserCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get FranchiseUsers by criteria: {}", criteria);

        Page<FranchiseUserDTO> page = franchiseUserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /franchise-users/count} : count all the franchiseUsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFranchiseUsers(FranchiseUserCriteria criteria) {
        LOG.debug("REST request to count FranchiseUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(franchiseUserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /franchise-users/:id} : get the "id" franchiseUser.
     *
     * @param id the id of the franchiseUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the franchiseUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FranchiseUserDTO> getFranchiseUser(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FranchiseUser : {}", id);
        Optional<FranchiseUserDTO> franchiseUserDTO = franchiseUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(franchiseUserDTO);
    }

    /**
     * {@code DELETE  /franchise-users/:id} : delete the "id" franchiseUser.
     *
     * @param id the id of the franchiseUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFranchiseUser(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FranchiseUser : {}", id);
        franchiseUserService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
