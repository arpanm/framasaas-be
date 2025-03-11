package com.framasaas.be.web.rest;

import com.framasaas.be.domain.ServiceOrderFranchiseAssignment;
import com.framasaas.be.repository.ServiceOrderFranchiseAssignmentRepository;
import com.framasaas.be.service.ServiceOrderFranchiseAssignmentService;
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
 * REST controller for managing {@link com.framasaas.be.domain.ServiceOrderFranchiseAssignment}.
 */
@RestController
@RequestMapping("/api/service-order-franchise-assignments")
public class ServiceOrderFranchiseAssignmentResource {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderFranchiseAssignmentResource.class);

    private static final String ENTITY_NAME = "serviceOrderFranchiseAssignment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceOrderFranchiseAssignmentService serviceOrderFranchiseAssignmentService;

    private final ServiceOrderFranchiseAssignmentRepository serviceOrderFranchiseAssignmentRepository;

    public ServiceOrderFranchiseAssignmentResource(
        ServiceOrderFranchiseAssignmentService serviceOrderFranchiseAssignmentService,
        ServiceOrderFranchiseAssignmentRepository serviceOrderFranchiseAssignmentRepository
    ) {
        this.serviceOrderFranchiseAssignmentService = serviceOrderFranchiseAssignmentService;
        this.serviceOrderFranchiseAssignmentRepository = serviceOrderFranchiseAssignmentRepository;
    }

    /**
     * {@code POST  /service-order-franchise-assignments} : Create a new serviceOrderFranchiseAssignment.
     *
     * @param serviceOrderFranchiseAssignment the serviceOrderFranchiseAssignment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceOrderFranchiseAssignment, or with status {@code 400 (Bad Request)} if the serviceOrderFranchiseAssignment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServiceOrderFranchiseAssignment> createServiceOrderFranchiseAssignment(
        @Valid @RequestBody ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment
    ) throws URISyntaxException {
        LOG.debug("REST request to save ServiceOrderFranchiseAssignment : {}", serviceOrderFranchiseAssignment);
        if (serviceOrderFranchiseAssignment.getId() != null) {
            throw new BadRequestAlertException("A new serviceOrderFranchiseAssignment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        serviceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentService.save(serviceOrderFranchiseAssignment);
        return ResponseEntity.created(new URI("/api/service-order-franchise-assignments/" + serviceOrderFranchiseAssignment.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, serviceOrderFranchiseAssignment.getId().toString())
            )
            .body(serviceOrderFranchiseAssignment);
    }

    /**
     * {@code PUT  /service-order-franchise-assignments/:id} : Updates an existing serviceOrderFranchiseAssignment.
     *
     * @param id the id of the serviceOrderFranchiseAssignment to save.
     * @param serviceOrderFranchiseAssignment the serviceOrderFranchiseAssignment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderFranchiseAssignment,
     * or with status {@code 400 (Bad Request)} if the serviceOrderFranchiseAssignment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderFranchiseAssignment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceOrderFranchiseAssignment> updateServiceOrderFranchiseAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment
    ) throws URISyntaxException {
        LOG.debug("REST request to update ServiceOrderFranchiseAssignment : {}, {}", id, serviceOrderFranchiseAssignment);
        if (serviceOrderFranchiseAssignment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderFranchiseAssignment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderFranchiseAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        serviceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentService.update(serviceOrderFranchiseAssignment);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrderFranchiseAssignment.getId().toString())
            )
            .body(serviceOrderFranchiseAssignment);
    }

    /**
     * {@code PATCH  /service-order-franchise-assignments/:id} : Partial updates given fields of an existing serviceOrderFranchiseAssignment, field will ignore if it is null
     *
     * @param id the id of the serviceOrderFranchiseAssignment to save.
     * @param serviceOrderFranchiseAssignment the serviceOrderFranchiseAssignment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderFranchiseAssignment,
     * or with status {@code 400 (Bad Request)} if the serviceOrderFranchiseAssignment is not valid,
     * or with status {@code 404 (Not Found)} if the serviceOrderFranchiseAssignment is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderFranchiseAssignment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServiceOrderFranchiseAssignment> partialUpdateServiceOrderFranchiseAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ServiceOrderFranchiseAssignment partially : {}, {}", id, serviceOrderFranchiseAssignment);
        if (serviceOrderFranchiseAssignment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderFranchiseAssignment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderFranchiseAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceOrderFranchiseAssignment> result = serviceOrderFranchiseAssignmentService.partialUpdate(
            serviceOrderFranchiseAssignment
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrderFranchiseAssignment.getId().toString())
        );
    }

    /**
     * {@code GET  /service-order-franchise-assignments} : get all the serviceOrderFranchiseAssignments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceOrderFranchiseAssignments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ServiceOrderFranchiseAssignment>> getAllServiceOrderFranchiseAssignments(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of ServiceOrderFranchiseAssignments");
        Page<ServiceOrderFranchiseAssignment> page = serviceOrderFranchiseAssignmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-order-franchise-assignments/:id} : get the "id" serviceOrderFranchiseAssignment.
     *
     * @param id the id of the serviceOrderFranchiseAssignment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceOrderFranchiseAssignment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceOrderFranchiseAssignment> getServiceOrderFranchiseAssignment(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ServiceOrderFranchiseAssignment : {}", id);
        Optional<ServiceOrderFranchiseAssignment> serviceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceOrderFranchiseAssignment);
    }

    /**
     * {@code DELETE  /service-order-franchise-assignments/:id} : delete the "id" serviceOrderFranchiseAssignment.
     *
     * @param id the id of the serviceOrderFranchiseAssignment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceOrderFranchiseAssignment(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ServiceOrderFranchiseAssignment : {}", id);
        serviceOrderFranchiseAssignmentService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
