package com.framasaas.web.rest;

import com.framasaas.repository.ServiceOrderMasterRepository;
import com.framasaas.service.ServiceOrderMasterQueryService;
import com.framasaas.service.ServiceOrderMasterService;
import com.framasaas.service.criteria.ServiceOrderMasterCriteria;
import com.framasaas.service.dto.ServiceOrderMasterDTO;
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
 * REST controller for managing {@link com.framasaas.domain.ServiceOrderMaster}.
 */
@RestController
@RequestMapping("/api/service-order-masters")
public class ServiceOrderMasterResource {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderMasterResource.class);

    private static final String ENTITY_NAME = "serviceOrderMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceOrderMasterService serviceOrderMasterService;

    private final ServiceOrderMasterRepository serviceOrderMasterRepository;

    private final ServiceOrderMasterQueryService serviceOrderMasterQueryService;

    public ServiceOrderMasterResource(
        ServiceOrderMasterService serviceOrderMasterService,
        ServiceOrderMasterRepository serviceOrderMasterRepository,
        ServiceOrderMasterQueryService serviceOrderMasterQueryService
    ) {
        this.serviceOrderMasterService = serviceOrderMasterService;
        this.serviceOrderMasterRepository = serviceOrderMasterRepository;
        this.serviceOrderMasterQueryService = serviceOrderMasterQueryService;
    }

    /**
     * {@code POST  /service-order-masters} : Create a new serviceOrderMaster.
     *
     * @param serviceOrderMasterDTO the serviceOrderMasterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceOrderMasterDTO, or with status {@code 400 (Bad Request)} if the serviceOrderMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServiceOrderMasterDTO> createServiceOrderMaster(@Valid @RequestBody ServiceOrderMasterDTO serviceOrderMasterDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save ServiceOrderMaster : {}", serviceOrderMasterDTO);
        if (serviceOrderMasterDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceOrderMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        serviceOrderMasterDTO = serviceOrderMasterService.save(serviceOrderMasterDTO);
        return ResponseEntity.created(new URI("/api/service-order-masters/" + serviceOrderMasterDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, serviceOrderMasterDTO.getId().toString()))
            .body(serviceOrderMasterDTO);
    }

    /**
     * {@code PUT  /service-order-masters/:id} : Updates an existing serviceOrderMaster.
     *
     * @param id the id of the serviceOrderMasterDTO to save.
     * @param serviceOrderMasterDTO the serviceOrderMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderMasterDTO,
     * or with status {@code 400 (Bad Request)} if the serviceOrderMasterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderMasterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceOrderMasterDTO> updateServiceOrderMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServiceOrderMasterDTO serviceOrderMasterDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ServiceOrderMaster : {}, {}", id, serviceOrderMasterDTO);
        if (serviceOrderMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        serviceOrderMasterDTO = serviceOrderMasterService.update(serviceOrderMasterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrderMasterDTO.getId().toString()))
            .body(serviceOrderMasterDTO);
    }

    /**
     * {@code PATCH  /service-order-masters/:id} : Partial updates given fields of an existing serviceOrderMaster, field will ignore if it is null
     *
     * @param id the id of the serviceOrderMasterDTO to save.
     * @param serviceOrderMasterDTO the serviceOrderMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderMasterDTO,
     * or with status {@code 400 (Bad Request)} if the serviceOrderMasterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the serviceOrderMasterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderMasterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServiceOrderMasterDTO> partialUpdateServiceOrderMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServiceOrderMasterDTO serviceOrderMasterDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ServiceOrderMaster partially : {}, {}", id, serviceOrderMasterDTO);
        if (serviceOrderMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceOrderMasterDTO> result = serviceOrderMasterService.partialUpdate(serviceOrderMasterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrderMasterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /service-order-masters} : get all the serviceOrderMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceOrderMasters in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ServiceOrderMasterDTO>> getAllServiceOrderMasters(
        ServiceOrderMasterCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get ServiceOrderMasters by criteria: {}", criteria);

        Page<ServiceOrderMasterDTO> page = serviceOrderMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-order-masters/count} : count all the serviceOrderMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countServiceOrderMasters(ServiceOrderMasterCriteria criteria) {
        LOG.debug("REST request to count ServiceOrderMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceOrderMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-order-masters/:id} : get the "id" serviceOrderMaster.
     *
     * @param id the id of the serviceOrderMasterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceOrderMasterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceOrderMasterDTO> getServiceOrderMaster(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ServiceOrderMaster : {}", id);
        Optional<ServiceOrderMasterDTO> serviceOrderMasterDTO = serviceOrderMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceOrderMasterDTO);
    }

    /**
     * {@code DELETE  /service-order-masters/:id} : delete the "id" serviceOrderMaster.
     *
     * @param id the id of the serviceOrderMasterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceOrderMaster(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ServiceOrderMaster : {}", id);
        serviceOrderMasterService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
