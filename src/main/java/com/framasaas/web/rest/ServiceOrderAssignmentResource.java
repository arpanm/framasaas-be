package com.framasaas.web.rest;

import com.framasaas.domain.ServiceOrderAssignment;
import com.framasaas.repository.ServiceOrderAssignmentRepository;
import com.framasaas.service.ServiceOrderAssignmentService;
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
 * REST controller for managing {@link com.framasaas.domain.ServiceOrderAssignment}.
 */
@RestController
@RequestMapping("/api/service-order-assignments")
public class ServiceOrderAssignmentResource {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderAssignmentResource.class);

    private static final String ENTITY_NAME = "serviceOrderAssignment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceOrderAssignmentService serviceOrderAssignmentService;

    private final ServiceOrderAssignmentRepository serviceOrderAssignmentRepository;

    public ServiceOrderAssignmentResource(
        ServiceOrderAssignmentService serviceOrderAssignmentService,
        ServiceOrderAssignmentRepository serviceOrderAssignmentRepository
    ) {
        this.serviceOrderAssignmentService = serviceOrderAssignmentService;
        this.serviceOrderAssignmentRepository = serviceOrderAssignmentRepository;
    }

    /**
     * {@code POST  /service-order-assignments} : Create a new serviceOrderAssignment.
     *
     * @param serviceOrderAssignment the serviceOrderAssignment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceOrderAssignment, or with status {@code 400 (Bad Request)} if the serviceOrderAssignment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServiceOrderAssignment> createServiceOrderAssignment(
        @Valid @RequestBody ServiceOrderAssignment serviceOrderAssignment
    ) throws URISyntaxException {
        LOG.debug("REST request to save ServiceOrderAssignment : {}", serviceOrderAssignment);
        if (serviceOrderAssignment.getId() != null) {
            throw new BadRequestAlertException("A new serviceOrderAssignment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        serviceOrderAssignment = serviceOrderAssignmentService.save(serviceOrderAssignment);
        return ResponseEntity.created(new URI("/api/service-order-assignments/" + serviceOrderAssignment.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, serviceOrderAssignment.getId().toString()))
            .body(serviceOrderAssignment);
    }

    /**
     * {@code PUT  /service-order-assignments/:id} : Updates an existing serviceOrderAssignment.
     *
     * @param id the id of the serviceOrderAssignment to save.
     * @param serviceOrderAssignment the serviceOrderAssignment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderAssignment,
     * or with status {@code 400 (Bad Request)} if the serviceOrderAssignment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderAssignment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceOrderAssignment> updateServiceOrderAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServiceOrderAssignment serviceOrderAssignment
    ) throws URISyntaxException {
        LOG.debug("REST request to update ServiceOrderAssignment : {}, {}", id, serviceOrderAssignment);
        if (serviceOrderAssignment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderAssignment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        serviceOrderAssignment = serviceOrderAssignmentService.update(serviceOrderAssignment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrderAssignment.getId().toString()))
            .body(serviceOrderAssignment);
    }

    /**
     * {@code PATCH  /service-order-assignments/:id} : Partial updates given fields of an existing serviceOrderAssignment, field will ignore if it is null
     *
     * @param id the id of the serviceOrderAssignment to save.
     * @param serviceOrderAssignment the serviceOrderAssignment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderAssignment,
     * or with status {@code 400 (Bad Request)} if the serviceOrderAssignment is not valid,
     * or with status {@code 404 (Not Found)} if the serviceOrderAssignment is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderAssignment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServiceOrderAssignment> partialUpdateServiceOrderAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServiceOrderAssignment serviceOrderAssignment
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ServiceOrderAssignment partially : {}, {}", id, serviceOrderAssignment);
        if (serviceOrderAssignment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderAssignment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceOrderAssignment> result = serviceOrderAssignmentService.partialUpdate(serviceOrderAssignment);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrderAssignment.getId().toString())
        );
    }

    /**
     * {@code GET  /service-order-assignments} : get all the serviceOrderAssignments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceOrderAssignments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ServiceOrderAssignment>> getAllServiceOrderAssignments(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of ServiceOrderAssignments");
        Page<ServiceOrderAssignment> page = serviceOrderAssignmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-order-assignments/:id} : get the "id" serviceOrderAssignment.
     *
     * @param id the id of the serviceOrderAssignment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceOrderAssignment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceOrderAssignment> getServiceOrderAssignment(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ServiceOrderAssignment : {}", id);
        Optional<ServiceOrderAssignment> serviceOrderAssignment = serviceOrderAssignmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceOrderAssignment);
    }

    /**
     * {@code DELETE  /service-order-assignments/:id} : delete the "id" serviceOrderAssignment.
     *
     * @param id the id of the serviceOrderAssignment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceOrderAssignment(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ServiceOrderAssignment : {}", id);
        serviceOrderAssignmentService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
