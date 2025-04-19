package com.framasaas.web.rest;

import com.framasaas.repository.AdditionalAttributeRepository;
import com.framasaas.service.AdditionalAttributeQueryService;
import com.framasaas.service.AdditionalAttributeService;
import com.framasaas.service.criteria.AdditionalAttributeCriteria;
import com.framasaas.service.dto.AdditionalAttributeDTO;
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
 * REST controller for managing {@link com.framasaas.domain.AdditionalAttribute}.
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

    private final AdditionalAttributeQueryService additionalAttributeQueryService;

    public AdditionalAttributeResource(
        AdditionalAttributeService additionalAttributeService,
        AdditionalAttributeRepository additionalAttributeRepository,
        AdditionalAttributeQueryService additionalAttributeQueryService
    ) {
        this.additionalAttributeService = additionalAttributeService;
        this.additionalAttributeRepository = additionalAttributeRepository;
        this.additionalAttributeQueryService = additionalAttributeQueryService;
    }

    /**
     * {@code POST  /additional-attributes} : Create a new additionalAttribute.
     *
     * @param additionalAttributeDTO the additionalAttributeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new additionalAttributeDTO, or with status {@code 400 (Bad Request)} if the additionalAttribute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AdditionalAttributeDTO> createAdditionalAttribute(
        @Valid @RequestBody AdditionalAttributeDTO additionalAttributeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save AdditionalAttribute : {}", additionalAttributeDTO);
        if (additionalAttributeDTO.getId() != null) {
            throw new BadRequestAlertException("A new additionalAttribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        additionalAttributeDTO = additionalAttributeService.save(additionalAttributeDTO);
        return ResponseEntity.created(new URI("/api/additional-attributes/" + additionalAttributeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, additionalAttributeDTO.getId().toString()))
            .body(additionalAttributeDTO);
    }

    /**
     * {@code PUT  /additional-attributes/:id} : Updates an existing additionalAttribute.
     *
     * @param id the id of the additionalAttributeDTO to save.
     * @param additionalAttributeDTO the additionalAttributeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated additionalAttributeDTO,
     * or with status {@code 400 (Bad Request)} if the additionalAttributeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the additionalAttributeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdditionalAttributeDTO> updateAdditionalAttribute(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdditionalAttributeDTO additionalAttributeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update AdditionalAttribute : {}, {}", id, additionalAttributeDTO);
        if (additionalAttributeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, additionalAttributeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!additionalAttributeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        additionalAttributeDTO = additionalAttributeService.update(additionalAttributeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, additionalAttributeDTO.getId().toString()))
            .body(additionalAttributeDTO);
    }

    /**
     * {@code PATCH  /additional-attributes/:id} : Partial updates given fields of an existing additionalAttribute, field will ignore if it is null
     *
     * @param id the id of the additionalAttributeDTO to save.
     * @param additionalAttributeDTO the additionalAttributeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated additionalAttributeDTO,
     * or with status {@code 400 (Bad Request)} if the additionalAttributeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the additionalAttributeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the additionalAttributeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdditionalAttributeDTO> partialUpdateAdditionalAttribute(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdditionalAttributeDTO additionalAttributeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update AdditionalAttribute partially : {}, {}", id, additionalAttributeDTO);
        if (additionalAttributeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, additionalAttributeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!additionalAttributeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdditionalAttributeDTO> result = additionalAttributeService.partialUpdate(additionalAttributeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, additionalAttributeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /additional-attributes} : get all the additionalAttributes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of additionalAttributes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AdditionalAttributeDTO>> getAllAdditionalAttributes(
        AdditionalAttributeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get AdditionalAttributes by criteria: {}", criteria);

        Page<AdditionalAttributeDTO> page = additionalAttributeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /additional-attributes/count} : count all the additionalAttributes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAdditionalAttributes(AdditionalAttributeCriteria criteria) {
        LOG.debug("REST request to count AdditionalAttributes by criteria: {}", criteria);
        return ResponseEntity.ok().body(additionalAttributeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /additional-attributes/:id} : get the "id" additionalAttribute.
     *
     * @param id the id of the additionalAttributeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the additionalAttributeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdditionalAttributeDTO> getAdditionalAttribute(@PathVariable("id") Long id) {
        LOG.debug("REST request to get AdditionalAttribute : {}", id);
        Optional<AdditionalAttributeDTO> additionalAttributeDTO = additionalAttributeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(additionalAttributeDTO);
    }

    /**
     * {@code DELETE  /additional-attributes/:id} : delete the "id" additionalAttribute.
     *
     * @param id the id of the additionalAttributeDTO to delete.
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
