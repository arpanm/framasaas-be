package com.framasaas.be.web.rest;

import com.framasaas.be.domain.ServiceOrderFieldAgentAssignment;
import com.framasaas.be.repository.ServiceOrderFieldAgentAssignmentRepository;
import com.framasaas.be.service.ServiceOrderFieldAgentAssignmentService;
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
 * REST controller for managing {@link com.framasaas.be.domain.ServiceOrderFieldAgentAssignment}.
 */
@RestController
@RequestMapping("/api/service-order-field-agent-assignments")
public class ServiceOrderFieldAgentAssignmentResource {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderFieldAgentAssignmentResource.class);

    private static final String ENTITY_NAME = "serviceOrderFieldAgentAssignment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceOrderFieldAgentAssignmentService serviceOrderFieldAgentAssignmentService;

    private final ServiceOrderFieldAgentAssignmentRepository serviceOrderFieldAgentAssignmentRepository;

    public ServiceOrderFieldAgentAssignmentResource(
        ServiceOrderFieldAgentAssignmentService serviceOrderFieldAgentAssignmentService,
        ServiceOrderFieldAgentAssignmentRepository serviceOrderFieldAgentAssignmentRepository
    ) {
        this.serviceOrderFieldAgentAssignmentService = serviceOrderFieldAgentAssignmentService;
        this.serviceOrderFieldAgentAssignmentRepository = serviceOrderFieldAgentAssignmentRepository;
    }

    /**
     * {@code POST  /service-order-field-agent-assignments} : Create a new serviceOrderFieldAgentAssignment.
     *
     * @param serviceOrderFieldAgentAssignment the serviceOrderFieldAgentAssignment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceOrderFieldAgentAssignment, or with status {@code 400 (Bad Request)} if the serviceOrderFieldAgentAssignment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServiceOrderFieldAgentAssignment> createServiceOrderFieldAgentAssignment(
        @Valid @RequestBody ServiceOrderFieldAgentAssignment serviceOrderFieldAgentAssignment
    ) throws URISyntaxException {
        LOG.debug("REST request to save ServiceOrderFieldAgentAssignment : {}", serviceOrderFieldAgentAssignment);
        if (serviceOrderFieldAgentAssignment.getId() != null) {
            throw new BadRequestAlertException("A new serviceOrderFieldAgentAssignment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        serviceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentService.save(serviceOrderFieldAgentAssignment);
        return ResponseEntity.created(new URI("/api/service-order-field-agent-assignments/" + serviceOrderFieldAgentAssignment.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    serviceOrderFieldAgentAssignment.getId().toString()
                )
            )
            .body(serviceOrderFieldAgentAssignment);
    }

    /**
     * {@code PUT  /service-order-field-agent-assignments/:id} : Updates an existing serviceOrderFieldAgentAssignment.
     *
     * @param id the id of the serviceOrderFieldAgentAssignment to save.
     * @param serviceOrderFieldAgentAssignment the serviceOrderFieldAgentAssignment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderFieldAgentAssignment,
     * or with status {@code 400 (Bad Request)} if the serviceOrderFieldAgentAssignment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderFieldAgentAssignment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceOrderFieldAgentAssignment> updateServiceOrderFieldAgentAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServiceOrderFieldAgentAssignment serviceOrderFieldAgentAssignment
    ) throws URISyntaxException {
        LOG.debug("REST request to update ServiceOrderFieldAgentAssignment : {}, {}", id, serviceOrderFieldAgentAssignment);
        if (serviceOrderFieldAgentAssignment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderFieldAgentAssignment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderFieldAgentAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        serviceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentService.update(serviceOrderFieldAgentAssignment);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrderFieldAgentAssignment.getId().toString())
            )
            .body(serviceOrderFieldAgentAssignment);
    }

    /**
     * {@code PATCH  /service-order-field-agent-assignments/:id} : Partial updates given fields of an existing serviceOrderFieldAgentAssignment, field will ignore if it is null
     *
     * @param id the id of the serviceOrderFieldAgentAssignment to save.
     * @param serviceOrderFieldAgentAssignment the serviceOrderFieldAgentAssignment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderFieldAgentAssignment,
     * or with status {@code 400 (Bad Request)} if the serviceOrderFieldAgentAssignment is not valid,
     * or with status {@code 404 (Not Found)} if the serviceOrderFieldAgentAssignment is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderFieldAgentAssignment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServiceOrderFieldAgentAssignment> partialUpdateServiceOrderFieldAgentAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServiceOrderFieldAgentAssignment serviceOrderFieldAgentAssignment
    ) throws URISyntaxException {
        LOG.debug(
            "REST request to partial update ServiceOrderFieldAgentAssignment partially : {}, {}",
            id,
            serviceOrderFieldAgentAssignment
        );
        if (serviceOrderFieldAgentAssignment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderFieldAgentAssignment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderFieldAgentAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceOrderFieldAgentAssignment> result = serviceOrderFieldAgentAssignmentService.partialUpdate(
            serviceOrderFieldAgentAssignment
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrderFieldAgentAssignment.getId().toString())
        );
    }

    /**
     * {@code GET  /service-order-field-agent-assignments} : get all the serviceOrderFieldAgentAssignments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceOrderFieldAgentAssignments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ServiceOrderFieldAgentAssignment>> getAllServiceOrderFieldAgentAssignments(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of ServiceOrderFieldAgentAssignments");
        Page<ServiceOrderFieldAgentAssignment> page = serviceOrderFieldAgentAssignmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-order-field-agent-assignments/:id} : get the "id" serviceOrderFieldAgentAssignment.
     *
     * @param id the id of the serviceOrderFieldAgentAssignment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceOrderFieldAgentAssignment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceOrderFieldAgentAssignment> getServiceOrderFieldAgentAssignment(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ServiceOrderFieldAgentAssignment : {}", id);
        Optional<ServiceOrderFieldAgentAssignment> serviceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceOrderFieldAgentAssignment);
    }

    /**
     * {@code DELETE  /service-order-field-agent-assignments/:id} : delete the "id" serviceOrderFieldAgentAssignment.
     *
     * @param id the id of the serviceOrderFieldAgentAssignment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceOrderFieldAgentAssignment(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ServiceOrderFieldAgentAssignment : {}", id);
        serviceOrderFieldAgentAssignmentService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
