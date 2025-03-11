package com.framasaas.be.web.rest;

import com.framasaas.be.domain.Pincode;
import com.framasaas.be.repository.PincodeRepository;
import com.framasaas.be.service.PincodeService;
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
 * REST controller for managing {@link com.framasaas.be.domain.Pincode}.
 */
@RestController
@RequestMapping("/api/pincodes")
public class PincodeResource {

    private static final Logger LOG = LoggerFactory.getLogger(PincodeResource.class);

    private static final String ENTITY_NAME = "pincode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PincodeService pincodeService;

    private final PincodeRepository pincodeRepository;

    public PincodeResource(PincodeService pincodeService, PincodeRepository pincodeRepository) {
        this.pincodeService = pincodeService;
        this.pincodeRepository = pincodeRepository;
    }

    /**
     * {@code POST  /pincodes} : Create a new pincode.
     *
     * @param pincode the pincode to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pincode, or with status {@code 400 (Bad Request)} if the pincode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Pincode> createPincode(@Valid @RequestBody Pincode pincode) throws URISyntaxException {
        LOG.debug("REST request to save Pincode : {}", pincode);
        if (pincode.getId() != null) {
            throw new BadRequestAlertException("A new pincode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pincode = pincodeService.save(pincode);
        return ResponseEntity.created(new URI("/api/pincodes/" + pincode.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, pincode.getId().toString()))
            .body(pincode);
    }

    /**
     * {@code PUT  /pincodes/:id} : Updates an existing pincode.
     *
     * @param id the id of the pincode to save.
     * @param pincode the pincode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pincode,
     * or with status {@code 400 (Bad Request)} if the pincode is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pincode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Pincode> updatePincode(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Pincode pincode
    ) throws URISyntaxException {
        LOG.debug("REST request to update Pincode : {}, {}", id, pincode);
        if (pincode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pincode.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pincodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pincode = pincodeService.update(pincode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pincode.getId().toString()))
            .body(pincode);
    }

    /**
     * {@code PATCH  /pincodes/:id} : Partial updates given fields of an existing pincode, field will ignore if it is null
     *
     * @param id the id of the pincode to save.
     * @param pincode the pincode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pincode,
     * or with status {@code 400 (Bad Request)} if the pincode is not valid,
     * or with status {@code 404 (Not Found)} if the pincode is not found,
     * or with status {@code 500 (Internal Server Error)} if the pincode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Pincode> partialUpdatePincode(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Pincode pincode
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Pincode partially : {}, {}", id, pincode);
        if (pincode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pincode.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pincodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Pincode> result = pincodeService.partialUpdate(pincode);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pincode.getId().toString())
        );
    }

    /**
     * {@code GET  /pincodes} : get all the pincodes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pincodes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Pincode>> getAllPincodes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Pincodes");
        Page<Pincode> page = pincodeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pincodes/:id} : get the "id" pincode.
     *
     * @param id the id of the pincode to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pincode, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pincode> getPincode(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Pincode : {}", id);
        Optional<Pincode> pincode = pincodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pincode);
    }

    /**
     * {@code DELETE  /pincodes/:id} : delete the "id" pincode.
     *
     * @param id the id of the pincode to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePincode(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Pincode : {}", id);
        pincodeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
