package com.framasaas.web.rest;

import com.framasaas.repository.LocationMappingRepository;
import com.framasaas.service.LocationMappingQueryService;
import com.framasaas.service.LocationMappingService;
import com.framasaas.service.criteria.LocationMappingCriteria;
import com.framasaas.service.dto.LocationMappingDTO;
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
 * REST controller for managing {@link com.framasaas.domain.LocationMapping}.
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

    private final LocationMappingQueryService locationMappingQueryService;

    public LocationMappingResource(
        LocationMappingService locationMappingService,
        LocationMappingRepository locationMappingRepository,
        LocationMappingQueryService locationMappingQueryService
    ) {
        this.locationMappingService = locationMappingService;
        this.locationMappingRepository = locationMappingRepository;
        this.locationMappingQueryService = locationMappingQueryService;
    }

    /**
     * {@code POST  /location-mappings} : Create a new locationMapping.
     *
     * @param locationMappingDTO the locationMappingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locationMappingDTO, or with status {@code 400 (Bad Request)} if the locationMapping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LocationMappingDTO> createLocationMapping(@Valid @RequestBody LocationMappingDTO locationMappingDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save LocationMapping : {}", locationMappingDTO);
        if (locationMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new locationMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        locationMappingDTO = locationMappingService.save(locationMappingDTO);
        return ResponseEntity.created(new URI("/api/location-mappings/" + locationMappingDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, locationMappingDTO.getId().toString()))
            .body(locationMappingDTO);
    }

    /**
     * {@code PUT  /location-mappings/:id} : Updates an existing locationMapping.
     *
     * @param id the id of the locationMappingDTO to save.
     * @param locationMappingDTO the locationMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationMappingDTO,
     * or with status {@code 400 (Bad Request)} if the locationMappingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locationMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LocationMappingDTO> updateLocationMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LocationMappingDTO locationMappingDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update LocationMapping : {}, {}", id, locationMappingDTO);
        if (locationMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locationMappingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locationMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        locationMappingDTO = locationMappingService.update(locationMappingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, locationMappingDTO.getId().toString()))
            .body(locationMappingDTO);
    }

    /**
     * {@code PATCH  /location-mappings/:id} : Partial updates given fields of an existing locationMapping, field will ignore if it is null
     *
     * @param id the id of the locationMappingDTO to save.
     * @param locationMappingDTO the locationMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationMappingDTO,
     * or with status {@code 400 (Bad Request)} if the locationMappingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the locationMappingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the locationMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LocationMappingDTO> partialUpdateLocationMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LocationMappingDTO locationMappingDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update LocationMapping partially : {}, {}", id, locationMappingDTO);
        if (locationMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locationMappingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locationMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LocationMappingDTO> result = locationMappingService.partialUpdate(locationMappingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, locationMappingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /location-mappings} : get all the locationMappings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locationMappings in body.
     */
    @GetMapping("")
    public ResponseEntity<List<LocationMappingDTO>> getAllLocationMappings(
        LocationMappingCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get LocationMappings by criteria: {}", criteria);

        Page<LocationMappingDTO> page = locationMappingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /location-mappings/count} : count all the locationMappings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countLocationMappings(LocationMappingCriteria criteria) {
        LOG.debug("REST request to count LocationMappings by criteria: {}", criteria);
        return ResponseEntity.ok().body(locationMappingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /location-mappings/:id} : get the "id" locationMapping.
     *
     * @param id the id of the locationMappingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locationMappingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LocationMappingDTO> getLocationMapping(@PathVariable("id") Long id) {
        LOG.debug("REST request to get LocationMapping : {}", id);
        Optional<LocationMappingDTO> locationMappingDTO = locationMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(locationMappingDTO);
    }

    /**
     * {@code DELETE  /location-mappings/:id} : delete the "id" locationMapping.
     *
     * @param id the id of the locationMappingDTO to delete.
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
