package com.framasaas.be.web.rest;

import com.framasaas.be.domain.AdditionalAttributePossibleValue;
import com.framasaas.be.repository.AdditionalAttributePossibleValueRepository;
import com.framasaas.be.service.AdditionalAttributePossibleValueService;
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
 * REST controller for managing {@link com.framasaas.be.domain.AdditionalAttributePossibleValue}.
 */
@RestController
@RequestMapping("/api/additional-attribute-possible-values")
public class AdditionalAttributePossibleValueResource {

    private static final Logger LOG = LoggerFactory.getLogger(AdditionalAttributePossibleValueResource.class);

    private static final String ENTITY_NAME = "additionalAttributePossibleValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdditionalAttributePossibleValueService additionalAttributePossibleValueService;

    private final AdditionalAttributePossibleValueRepository additionalAttributePossibleValueRepository;

    public AdditionalAttributePossibleValueResource(
        AdditionalAttributePossibleValueService additionalAttributePossibleValueService,
        AdditionalAttributePossibleValueRepository additionalAttributePossibleValueRepository
    ) {
        this.additionalAttributePossibleValueService = additionalAttributePossibleValueService;
        this.additionalAttributePossibleValueRepository = additionalAttributePossibleValueRepository;
    }

    /**
     * {@code POST  /additional-attribute-possible-values} : Create a new additionalAttributePossibleValue.
     *
     * @param additionalAttributePossibleValue the additionalAttributePossibleValue to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new additionalAttributePossibleValue, or with status {@code 400 (Bad Request)} if the additionalAttributePossibleValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AdditionalAttributePossibleValue> createAdditionalAttributePossibleValue(
        @Valid @RequestBody AdditionalAttributePossibleValue additionalAttributePossibleValue
    ) throws URISyntaxException {
        LOG.debug("REST request to save AdditionalAttributePossibleValue : {}", additionalAttributePossibleValue);
        if (additionalAttributePossibleValue.getId() != null) {
            throw new BadRequestAlertException("A new additionalAttributePossibleValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        additionalAttributePossibleValue = additionalAttributePossibleValueService.save(additionalAttributePossibleValue);
        return ResponseEntity.created(new URI("/api/additional-attribute-possible-values/" + additionalAttributePossibleValue.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    additionalAttributePossibleValue.getId().toString()
                )
            )
            .body(additionalAttributePossibleValue);
    }

    /**
     * {@code PUT  /additional-attribute-possible-values/:id} : Updates an existing additionalAttributePossibleValue.
     *
     * @param id the id of the additionalAttributePossibleValue to save.
     * @param additionalAttributePossibleValue the additionalAttributePossibleValue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated additionalAttributePossibleValue,
     * or with status {@code 400 (Bad Request)} if the additionalAttributePossibleValue is not valid,
     * or with status {@code 500 (Internal Server Error)} if the additionalAttributePossibleValue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdditionalAttributePossibleValue> updateAdditionalAttributePossibleValue(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdditionalAttributePossibleValue additionalAttributePossibleValue
    ) throws URISyntaxException {
        LOG.debug("REST request to update AdditionalAttributePossibleValue : {}, {}", id, additionalAttributePossibleValue);
        if (additionalAttributePossibleValue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, additionalAttributePossibleValue.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!additionalAttributePossibleValueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        additionalAttributePossibleValue = additionalAttributePossibleValueService.update(additionalAttributePossibleValue);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, additionalAttributePossibleValue.getId().toString())
            )
            .body(additionalAttributePossibleValue);
    }

    /**
     * {@code PATCH  /additional-attribute-possible-values/:id} : Partial updates given fields of an existing additionalAttributePossibleValue, field will ignore if it is null
     *
     * @param id the id of the additionalAttributePossibleValue to save.
     * @param additionalAttributePossibleValue the additionalAttributePossibleValue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated additionalAttributePossibleValue,
     * or with status {@code 400 (Bad Request)} if the additionalAttributePossibleValue is not valid,
     * or with status {@code 404 (Not Found)} if the additionalAttributePossibleValue is not found,
     * or with status {@code 500 (Internal Server Error)} if the additionalAttributePossibleValue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdditionalAttributePossibleValue> partialUpdateAdditionalAttributePossibleValue(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdditionalAttributePossibleValue additionalAttributePossibleValue
    ) throws URISyntaxException {
        LOG.debug(
            "REST request to partial update AdditionalAttributePossibleValue partially : {}, {}",
            id,
            additionalAttributePossibleValue
        );
        if (additionalAttributePossibleValue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, additionalAttributePossibleValue.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!additionalAttributePossibleValueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdditionalAttributePossibleValue> result = additionalAttributePossibleValueService.partialUpdate(
            additionalAttributePossibleValue
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, additionalAttributePossibleValue.getId().toString())
        );
    }

    /**
     * {@code GET  /additional-attribute-possible-values} : get all the additionalAttributePossibleValues.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of additionalAttributePossibleValues in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AdditionalAttributePossibleValue>> getAllAdditionalAttributePossibleValues(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of AdditionalAttributePossibleValues");
        Page<AdditionalAttributePossibleValue> page = additionalAttributePossibleValueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /additional-attribute-possible-values/:id} : get the "id" additionalAttributePossibleValue.
     *
     * @param id the id of the additionalAttributePossibleValue to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the additionalAttributePossibleValue, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdditionalAttributePossibleValue> getAdditionalAttributePossibleValue(@PathVariable("id") Long id) {
        LOG.debug("REST request to get AdditionalAttributePossibleValue : {}", id);
        Optional<AdditionalAttributePossibleValue> additionalAttributePossibleValue = additionalAttributePossibleValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(additionalAttributePossibleValue);
    }

    /**
     * {@code DELETE  /additional-attribute-possible-values/:id} : delete the "id" additionalAttributePossibleValue.
     *
     * @param id the id of the additionalAttributePossibleValue to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdditionalAttributePossibleValue(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete AdditionalAttributePossibleValue : {}", id);
        additionalAttributePossibleValueService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
