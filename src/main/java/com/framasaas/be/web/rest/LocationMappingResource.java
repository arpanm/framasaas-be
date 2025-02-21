package com.framasaas.be.web.rest;

import com.framasaas.be.domain.LocationMapping;
import com.framasaas.be.repository.LocationMappingRepository;
import com.framasaas.be.service.LocationMappingService;
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
 * REST controller for managing {@link com.framasaas.be.domain.LocationMapping}.
 */
@RestController
@RequestMapping("/api/location-mappings")
public class LocationMappingResource {

    private static final Logger LOG = LoggerFactory.getLogger(LocationMappingResource.class);

    private static final String ENTITY_NAME = "locationMapping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocationMappingService locationMappingService;

    private final LocationMappingRepository locationMappingRepository;

    public LocationMappingResource(LocationMappingService locationMappingService, LocationMappingRepository locationMappingRepository) {
        this.locationMappingService = locationMappingService;
        this.locationMappingRepository = locationMappingRepository;
    }

    /**
     * {@code POST  /location-mappings} : Create a new locationMapping.
     *
     * @param locationMapping the locationMapping to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locationMapping, or with status {@code 400 (Bad Request)} if the locationMapping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LocationMapping> createLocationMapping(@Valid @RequestBody LocationMapping locationMapping)
        throws URISyntaxException {
        LOG.debug("REST request to save LocationMapping : {}", locationMapping);
        if (locationMapping.getId() != null) {
            throw new BadRequestAlertException("A new locationMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        locationMapping = locationMappingService.save(locationMapping);
        return ResponseEntity.created(new URI("/api/location-mappings/" + locationMapping.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, locationMapping.getId().toString()))
            .body(locationMapping);
    }

    /**
     * {@code PUT  /location-mappings/:id} : Updates an existing locationMapping.
     *
     * @param id the id of the locationMapping to save.
     * @param locationMapping the locationMapping to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationMapping,
     * or with status {@code 400 (Bad Request)} if the locationMapping is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locationMapping couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LocationMapping> updateLocationMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LocationMapping locationMapping
    ) throws URISyntaxException {
        LOG.debug("REST request to update LocationMapping : {}, {}", id, locationMapping);
        if (locationMapping.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locationMapping.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locationMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        locationMapping = locationMappingService.update(locationMapping);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, locationMapping.getId().toString()))
            .body(locationMapping);
    }

    /**
     * {@code PATCH  /location-mappings/:id} : Partial updates given fields of an existing locationMapping, field will ignore if it is null
     *
     * @param id the id of the locationMapping to save.
     * @param locationMapping the locationMapping to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationMapping,
     * or with status {@code 400 (Bad Request)} if the locationMapping is not valid,
     * or with status {@code 404 (Not Found)} if the locationMapping is not found,
     * or with status {@code 500 (Internal Server Error)} if the locationMapping couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LocationMapping> partialUpdateLocationMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LocationMapping locationMapping
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update LocationMapping partially : {}, {}", id, locationMapping);
        if (locationMapping.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locationMapping.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locationMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LocationMapping> result = locationMappingService.partialUpdate(locationMapping);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, locationMapping.getId().toString())
        );
    }

    /**
     * {@code GET  /location-mappings} : get all the locationMappings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locationMappings in body.
     */
    @GetMapping("")
    public ResponseEntity<List<LocationMapping>> getAllLocationMappings(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of LocationMappings");
        Page<LocationMapping> page = locationMappingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /location-mappings/:id} : get the "id" locationMapping.
     *
     * @param id the id of the locationMapping to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locationMapping, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LocationMapping> getLocationMapping(@PathVariable("id") Long id) {
        LOG.debug("REST request to get LocationMapping : {}", id);
        Optional<LocationMapping> locationMapping = locationMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(locationMapping);
    }

    /**
     * {@code DELETE  /location-mappings/:id} : delete the "id" locationMapping.
     *
     * @param id the id of the locationMapping to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocationMapping(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete LocationMapping : {}", id);
        locationMappingService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
