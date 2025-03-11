package com.framasaas.be.web.rest;

import com.framasaas.be.domain.ServiceOrderMaster;
import com.framasaas.be.repository.ServiceOrderMasterRepository;
import com.framasaas.be.service.ServiceOrderMasterService;
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
 * REST controller for managing {@link com.framasaas.be.domain.ServiceOrderMaster}.
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

    public ServiceOrderMasterResource(
        ServiceOrderMasterService serviceOrderMasterService,
        ServiceOrderMasterRepository serviceOrderMasterRepository
    ) {
        this.serviceOrderMasterService = serviceOrderMasterService;
        this.serviceOrderMasterRepository = serviceOrderMasterRepository;
    }

    /**
     * {@code POST  /service-order-masters} : Create a new serviceOrderMaster.
     *
     * @param serviceOrderMaster the serviceOrderMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceOrderMaster, or with status {@code 400 (Bad Request)} if the serviceOrderMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServiceOrderMaster> createServiceOrderMaster(@Valid @RequestBody ServiceOrderMaster serviceOrderMaster)
        throws URISyntaxException {
        LOG.debug("REST request to save ServiceOrderMaster : {}", serviceOrderMaster);
        if (serviceOrderMaster.getId() != null) {
            throw new BadRequestAlertException("A new serviceOrderMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        serviceOrderMaster = serviceOrderMasterService.save(serviceOrderMaster);
        return ResponseEntity.created(new URI("/api/service-order-masters/" + serviceOrderMaster.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, serviceOrderMaster.getId().toString()))
            .body(serviceOrderMaster);
    }

    /**
     * {@code PUT  /service-order-masters/:id} : Updates an existing serviceOrderMaster.
     *
     * @param id the id of the serviceOrderMaster to save.
     * @param serviceOrderMaster the serviceOrderMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderMaster,
     * or with status {@code 400 (Bad Request)} if the serviceOrderMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceOrderMaster> updateServiceOrderMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServiceOrderMaster serviceOrderMaster
    ) throws URISyntaxException {
        LOG.debug("REST request to update ServiceOrderMaster : {}, {}", id, serviceOrderMaster);
        if (serviceOrderMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        serviceOrderMaster = serviceOrderMasterService.update(serviceOrderMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrderMaster.getId().toString()))
            .body(serviceOrderMaster);
    }

    /**
     * {@code PATCH  /service-order-masters/:id} : Partial updates given fields of an existing serviceOrderMaster, field will ignore if it is null
     *
     * @param id the id of the serviceOrderMaster to save.
     * @param serviceOrderMaster the serviceOrderMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderMaster,
     * or with status {@code 400 (Bad Request)} if the serviceOrderMaster is not valid,
     * or with status {@code 404 (Not Found)} if the serviceOrderMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServiceOrderMaster> partialUpdateServiceOrderMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServiceOrderMaster serviceOrderMaster
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ServiceOrderMaster partially : {}, {}", id, serviceOrderMaster);
        if (serviceOrderMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceOrderMaster> result = serviceOrderMasterService.partialUpdate(serviceOrderMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrderMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /service-order-masters} : get all the serviceOrderMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceOrderMasters in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ServiceOrderMaster>> getAllServiceOrderMasters(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of ServiceOrderMasters");
        Page<ServiceOrderMaster> page = serviceOrderMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-order-masters/:id} : get the "id" serviceOrderMaster.
     *
     * @param id the id of the serviceOrderMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceOrderMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceOrderMaster> getServiceOrderMaster(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ServiceOrderMaster : {}", id);
        Optional<ServiceOrderMaster> serviceOrderMaster = serviceOrderMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceOrderMaster);
    }

    /**
     * {@code DELETE  /service-order-masters/:id} : delete the "id" serviceOrderMaster.
     *
     * @param id the id of the serviceOrderMaster to delete.
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
