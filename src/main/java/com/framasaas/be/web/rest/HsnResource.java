package com.framasaas.be.web.rest;

import com.framasaas.be.domain.Hsn;
import com.framasaas.be.repository.HsnRepository;
import com.framasaas.be.service.HsnService;
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
 * REST controller for managing {@link com.framasaas.be.domain.Hsn}.
 */
@RestController
@RequestMapping("/api/hsns")
public class HsnResource {

    private static final Logger LOG = LoggerFactory.getLogger(HsnResource.class);

    private static final String ENTITY_NAME = "hsn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HsnService hsnService;

    private final HsnRepository hsnRepository;

    public HsnResource(HsnService hsnService, HsnRepository hsnRepository) {
        this.hsnService = hsnService;
        this.hsnRepository = hsnRepository;
    }

    /**
     * {@code POST  /hsns} : Create a new hsn.
     *
     * @param hsn the hsn to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hsn, or with status {@code 400 (Bad Request)} if the hsn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Hsn> createHsn(@Valid @RequestBody Hsn hsn) throws URISyntaxException {
        LOG.debug("REST request to save Hsn : {}", hsn);
        if (hsn.getId() != null) {
            throw new BadRequestAlertException("A new hsn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        hsn = hsnService.save(hsn);
        return ResponseEntity.created(new URI("/api/hsns/" + hsn.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, hsn.getId().toString()))
            .body(hsn);
    }

    /**
     * {@code PUT  /hsns/:id} : Updates an existing hsn.
     *
     * @param id the id of the hsn to save.
     * @param hsn the hsn to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hsn,
     * or with status {@code 400 (Bad Request)} if the hsn is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hsn couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Hsn> updateHsn(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Hsn hsn)
        throws URISyntaxException {
        LOG.debug("REST request to update Hsn : {}, {}", id, hsn);
        if (hsn.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hsn.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hsnRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        hsn = hsnService.update(hsn);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hsn.getId().toString()))
            .body(hsn);
    }

    /**
     * {@code PATCH  /hsns/:id} : Partial updates given fields of an existing hsn, field will ignore if it is null
     *
     * @param id the id of the hsn to save.
     * @param hsn the hsn to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hsn,
     * or with status {@code 400 (Bad Request)} if the hsn is not valid,
     * or with status {@code 404 (Not Found)} if the hsn is not found,
     * or with status {@code 500 (Internal Server Error)} if the hsn couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Hsn> partialUpdateHsn(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody Hsn hsn)
        throws URISyntaxException {
        LOG.debug("REST request to partial update Hsn partially : {}, {}", id, hsn);
        if (hsn.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hsn.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hsnRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Hsn> result = hsnService.partialUpdate(hsn);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hsn.getId().toString())
        );
    }

    /**
     * {@code GET  /hsns} : get all the hsns.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hsns in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Hsn>> getAllHsns(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Hsns");
        Page<Hsn> page = hsnService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hsns/:id} : get the "id" hsn.
     *
     * @param id the id of the hsn to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hsn, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Hsn> getHsn(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Hsn : {}", id);
        Optional<Hsn> hsn = hsnService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hsn);
    }

    /**
     * {@code DELETE  /hsns/:id} : delete the "id" hsn.
     *
     * @param id the id of the hsn to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHsn(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Hsn : {}", id);
        hsnService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
