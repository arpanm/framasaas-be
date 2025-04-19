package com.framasaas.web.rest;

import com.framasaas.repository.AdditionalAttributePossibleValueRepository;
import com.framasaas.service.AdditionalAttributePossibleValueQueryService;
import com.framasaas.service.AdditionalAttributePossibleValueService;
import com.framasaas.service.criteria.AdditionalAttributePossibleValueCriteria;
import com.framasaas.service.dto.AdditionalAttributePossibleValueDTO;
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
 * REST controller for managing {@link com.framasaas.domain.AdditionalAttributePossibleValue}.
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

    private final AdditionalAttributePossibleValueQueryService additionalAttributePossibleValueQueryService;

    public AdditionalAttributePossibleValueResource(
        AdditionalAttributePossibleValueService additionalAttributePossibleValueService,
        AdditionalAttributePossibleValueRepository additionalAttributePossibleValueRepository,
        AdditionalAttributePossibleValueQueryService additionalAttributePossibleValueQueryService
    ) {
        this.additionalAttributePossibleValueService = additionalAttributePossibleValueService;
        this.additionalAttributePossibleValueRepository = additionalAttributePossibleValueRepository;
        this.additionalAttributePossibleValueQueryService = additionalAttributePossibleValueQueryService;
    }

    /**
     * {@code POST  /additional-attribute-possible-values} : Create a new additionalAttributePossibleValue.
     *
     * @param additionalAttributePossibleValueDTO the additionalAttributePossibleValueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new additionalAttributePossibleValueDTO, or with status {@code 400 (Bad Request)} if the additionalAttributePossibleValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AdditionalAttributePossibleValueDTO> createAdditionalAttributePossibleValue(
        @Valid @RequestBody AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save AdditionalAttributePossibleValue : {}", additionalAttributePossibleValueDTO);
        if (additionalAttributePossibleValueDTO.getId() != null) {
            throw new BadRequestAlertException("A new additionalAttributePossibleValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        additionalAttributePossibleValueDTO = additionalAttributePossibleValueService.save(additionalAttributePossibleValueDTO);
        return ResponseEntity.created(new URI("/api/additional-attribute-possible-values/" + additionalAttributePossibleValueDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    additionalAttributePossibleValueDTO.getId().toString()
                )
            )
            .body(additionalAttributePossibleValueDTO);
    }

    /**
     * {@code PUT  /additional-attribute-possible-values/:id} : Updates an existing additionalAttributePossibleValue.
     *
     * @param id the id of the additionalAttributePossibleValueDTO to save.
     * @param additionalAttributePossibleValueDTO the additionalAttributePossibleValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated additionalAttributePossibleValueDTO,
     * or with status {@code 400 (Bad Request)} if the additionalAttributePossibleValueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the additionalAttributePossibleValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdditionalAttributePossibleValueDTO> updateAdditionalAttributePossibleValue(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update AdditionalAttributePossibleValue : {}, {}", id, additionalAttributePossibleValueDTO);
        if (additionalAttributePossibleValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, additionalAttributePossibleValueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!additionalAttributePossibleValueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        additionalAttributePossibleValueDTO = additionalAttributePossibleValueService.update(additionalAttributePossibleValueDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    additionalAttributePossibleValueDTO.getId().toString()
                )
            )
            .body(additionalAttributePossibleValueDTO);
    }

    /**
     * {@code PATCH  /additional-attribute-possible-values/:id} : Partial updates given fields of an existing additionalAttributePossibleValue, field will ignore if it is null
     *
     * @param id the id of the additionalAttributePossibleValueDTO to save.
     * @param additionalAttributePossibleValueDTO the additionalAttributePossibleValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated additionalAttributePossibleValueDTO,
     * or with status {@code 400 (Bad Request)} if the additionalAttributePossibleValueDTO is not valid,
     * or with status {@code 404 (Not Found)} if the additionalAttributePossibleValueDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the additionalAttributePossibleValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdditionalAttributePossibleValueDTO> partialUpdateAdditionalAttributePossibleValue(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO
    ) throws URISyntaxException {
        LOG.debug(
            "REST request to partial update AdditionalAttributePossibleValue partially : {}, {}",
            id,
            additionalAttributePossibleValueDTO
        );
        if (additionalAttributePossibleValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, additionalAttributePossibleValueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!additionalAttributePossibleValueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdditionalAttributePossibleValueDTO> result = additionalAttributePossibleValueService.partialUpdate(
            additionalAttributePossibleValueDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, additionalAttributePossibleValueDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /additional-attribute-possible-values} : get all the additionalAttributePossibleValues.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of additionalAttributePossibleValues in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AdditionalAttributePossibleValueDTO>> getAllAdditionalAttributePossibleValues(
        AdditionalAttributePossibleValueCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get AdditionalAttributePossibleValues by criteria: {}", criteria);

        Page<AdditionalAttributePossibleValueDTO> page = additionalAttributePossibleValueQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /additional-attribute-possible-values/count} : count all the additionalAttributePossibleValues.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAdditionalAttributePossibleValues(AdditionalAttributePossibleValueCriteria criteria) {
        LOG.debug("REST request to count AdditionalAttributePossibleValues by criteria: {}", criteria);
        return ResponseEntity.ok().body(additionalAttributePossibleValueQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /additional-attribute-possible-values/:id} : get the "id" additionalAttributePossibleValue.
     *
     * @param id the id of the additionalAttributePossibleValueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the additionalAttributePossibleValueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdditionalAttributePossibleValueDTO> getAdditionalAttributePossibleValue(@PathVariable("id") Long id) {
        LOG.debug("REST request to get AdditionalAttributePossibleValue : {}", id);
        Optional<AdditionalAttributePossibleValueDTO> additionalAttributePossibleValueDTO = additionalAttributePossibleValueService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(additionalAttributePossibleValueDTO);
    }

    /**
     * {@code DELETE  /additional-attribute-possible-values/:id} : delete the "id" additionalAttributePossibleValue.
     *
     * @param id the id of the additionalAttributePossibleValueDTO to delete.
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
