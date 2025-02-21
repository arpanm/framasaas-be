package com.framasaas.be.web.rest;

import com.framasaas.be.domain.FranchiseUser;
import com.framasaas.be.repository.FranchiseUserRepository;
import com.framasaas.be.service.FranchiseUserService;
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
 * REST controller for managing {@link com.framasaas.be.domain.FranchiseUser}.
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

    public FranchiseUserResource(FranchiseUserService franchiseUserService, FranchiseUserRepository franchiseUserRepository) {
        this.franchiseUserService = franchiseUserService;
        this.franchiseUserRepository = franchiseUserRepository;
    }

    /**
     * {@code POST  /franchise-users} : Create a new franchiseUser.
     *
     * @param franchiseUser the franchiseUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new franchiseUser, or with status {@code 400 (Bad Request)} if the franchiseUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FranchiseUser> createFranchiseUser(@Valid @RequestBody FranchiseUser franchiseUser) throws URISyntaxException {
        LOG.debug("REST request to save FranchiseUser : {}", franchiseUser);
        if (franchiseUser.getId() != null) {
            throw new BadRequestAlertException("A new franchiseUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        franchiseUser = franchiseUserService.save(franchiseUser);
        return ResponseEntity.created(new URI("/api/franchise-users/" + franchiseUser.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, franchiseUser.getId().toString()))
            .body(franchiseUser);
    }

    /**
     * {@code PUT  /franchise-users/:id} : Updates an existing franchiseUser.
     *
     * @param id the id of the franchiseUser to save.
     * @param franchiseUser the franchiseUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseUser,
     * or with status {@code 400 (Bad Request)} if the franchiseUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the franchiseUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FranchiseUser> updateFranchiseUser(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FranchiseUser franchiseUser
    ) throws URISyntaxException {
        LOG.debug("REST request to update FranchiseUser : {}, {}", id, franchiseUser);
        if (franchiseUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        franchiseUser = franchiseUserService.update(franchiseUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseUser.getId().toString()))
            .body(franchiseUser);
    }

    /**
     * {@code PATCH  /franchise-users/:id} : Partial updates given fields of an existing franchiseUser, field will ignore if it is null
     *
     * @param id the id of the franchiseUser to save.
     * @param franchiseUser the franchiseUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated franchiseUser,
     * or with status {@code 400 (Bad Request)} if the franchiseUser is not valid,
     * or with status {@code 404 (Not Found)} if the franchiseUser is not found,
     * or with status {@code 500 (Internal Server Error)} if the franchiseUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FranchiseUser> partialUpdateFranchiseUser(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FranchiseUser franchiseUser
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FranchiseUser partially : {}, {}", id, franchiseUser);
        if (franchiseUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, franchiseUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!franchiseUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FranchiseUser> result = franchiseUserService.partialUpdate(franchiseUser);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, franchiseUser.getId().toString())
        );
    }

    /**
     * {@code GET  /franchise-users} : get all the franchiseUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of franchiseUsers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FranchiseUser>> getAllFranchiseUsers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of FranchiseUsers");
        Page<FranchiseUser> page = franchiseUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /franchise-users/:id} : get the "id" franchiseUser.
     *
     * @param id the id of the franchiseUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the franchiseUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FranchiseUser> getFranchiseUser(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FranchiseUser : {}", id);
        Optional<FranchiseUser> franchiseUser = franchiseUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(franchiseUser);
    }

    /**
     * {@code DELETE  /franchise-users/:id} : delete the "id" franchiseUser.
     *
     * @param id the id of the franchiseUser to delete.
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
