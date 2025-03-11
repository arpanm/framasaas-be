package com.framasaas.be.web.rest;

import com.framasaas.be.domain.AdditionalAttribute;
import com.framasaas.be.repository.AdditionalAttributeRepository;
import com.framasaas.be.service.AdditionalAttributeService;
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
 * REST controller for managing {@link com.framasaas.be.domain.AdditionalAttribute}.
 */
@RestController
@RequestMapping("/api/additional-attributes")
public class AdditionalAttributeResource {

    private static final Logger LOG = LoggerFactory.getLogger(AdditionalAttributeResource.class);

    private static final String ENTITY_NAME = "additionalAttribute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdditionalAttributeService additionalAttributeService;

    private final AdditionalAttributeRepository additionalAttributeRepository;

    public AdditionalAttributeResource(
        AdditionalAttributeService additionalAttributeService,
        AdditionalAttributeRepository additionalAttributeRepository
    ) {
        this.additionalAttributeService = additionalAttributeService;
        this.additionalAttributeRepository = additionalAttributeRepository;
    }

    /**
     * {@code POST  /additional-attributes} : Create a new additionalAttribute.
     *
     * @param additionalAttribute the additionalAttribute to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new additionalAttribute, or with status {@code 400 (Bad Request)} if the additionalAttribute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AdditionalAttribute> createAdditionalAttribute(@Valid @RequestBody AdditionalAttribute additionalAttribute)
        throws URISyntaxException {
        LOG.debug("REST request to save AdditionalAttribute : {}", additionalAttribute);
        if (additionalAttribute.getId() != null) {
            throw new BadRequestAlertException("A new additionalAttribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        additionalAttribute = additionalAttributeService.save(additionalAttribute);
        return ResponseEntity.created(new URI("/api/additional-attributes/" + additionalAttribute.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, additionalAttribute.getId().toString()))
            .body(additionalAttribute);
    }

    /**
     * {@code PUT  /additional-attributes/:id} : Updates an existing additionalAttribute.
     *
     * @param id the id of the additionalAttribute to save.
     * @param additionalAttribute the additionalAttribute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated additionalAttribute,
     * or with status {@code 400 (Bad Request)} if the additionalAttribute is not valid,
     * or with status {@code 500 (Internal Server Error)} if the additionalAttribute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdditionalAttribute> updateAdditionalAttribute(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdditionalAttribute additionalAttribute
    ) throws URISyntaxException {
        LOG.debug("REST request to update AdditionalAttribute : {}, {}", id, additionalAttribute);
        if (additionalAttribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, additionalAttribute.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!additionalAttributeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        additionalAttribute = additionalAttributeService.update(additionalAttribute);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, additionalAttribute.getId().toString()))
            .body(additionalAttribute);
    }

    /**
     * {@code PATCH  /additional-attributes/:id} : Partial updates given fields of an existing additionalAttribute, field will ignore if it is null
     *
     * @param id the id of the additionalAttribute to save.
     * @param additionalAttribute the additionalAttribute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated additionalAttribute,
     * or with status {@code 400 (Bad Request)} if the additionalAttribute is not valid,
     * or with status {@code 404 (Not Found)} if the additionalAttribute is not found,
     * or with status {@code 500 (Internal Server Error)} if the additionalAttribute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdditionalAttribute> partialUpdateAdditionalAttribute(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdditionalAttribute additionalAttribute
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update AdditionalAttribute partially : {}, {}", id, additionalAttribute);
        if (additionalAttribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, additionalAttribute.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!additionalAttributeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdditionalAttribute> result = additionalAttributeService.partialUpdate(additionalAttribute);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, additionalAttribute.getId().toString())
        );
    }

    /**
     * {@code GET  /additional-attributes} : get all the additionalAttributes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of additionalAttributes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AdditionalAttribute>> getAllAdditionalAttributes(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of AdditionalAttributes");
        Page<AdditionalAttribute> page = additionalAttributeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /additional-attributes/:id} : get the "id" additionalAttribute.
     *
     * @param id the id of the additionalAttribute to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the additionalAttribute, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdditionalAttribute> getAdditionalAttribute(@PathVariable("id") Long id) {
        LOG.debug("REST request to get AdditionalAttribute : {}", id);
        Optional<AdditionalAttribute> additionalAttribute = additionalAttributeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(additionalAttribute);
    }

    /**
     * {@code DELETE  /additional-attributes/:id} : delete the "id" additionalAttribute.
     *
     * @param id the id of the additionalAttribute to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdditionalAttribute(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete AdditionalAttribute : {}", id);
        additionalAttributeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
