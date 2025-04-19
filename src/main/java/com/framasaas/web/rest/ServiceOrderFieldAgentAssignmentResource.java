package com.framasaas.web.rest;

import com.framasaas.repository.ServiceOrderFieldAgentAssignmentRepository;
import com.framasaas.service.ServiceOrderFieldAgentAssignmentQueryService;
import com.framasaas.service.ServiceOrderFieldAgentAssignmentService;
import com.framasaas.service.criteria.ServiceOrderFieldAgentAssignmentCriteria;
import com.framasaas.service.dto.ServiceOrderFieldAgentAssignmentDTO;
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
 * REST controller for managing {@link com.framasaas.domain.ServiceOrderFieldAgentAssignment}.
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

    private final ServiceOrderFieldAgentAssignmentQueryService serviceOrderFieldAgentAssignmentQueryService;

    public ServiceOrderFieldAgentAssignmentResource(
        ServiceOrderFieldAgentAssignmentService serviceOrderFieldAgentAssignmentService,
        ServiceOrderFieldAgentAssignmentRepository serviceOrderFieldAgentAssignmentRepository,
        ServiceOrderFieldAgentAssignmentQueryService serviceOrderFieldAgentAssignmentQueryService
    ) {
        this.serviceOrderFieldAgentAssignmentService = serviceOrderFieldAgentAssignmentService;
        this.serviceOrderFieldAgentAssignmentRepository = serviceOrderFieldAgentAssignmentRepository;
        this.serviceOrderFieldAgentAssignmentQueryService = serviceOrderFieldAgentAssignmentQueryService;
    }

    /**
     * {@code POST  /service-order-field-agent-assignments} : Create a new serviceOrderFieldAgentAssignment.
     *
     * @param serviceOrderFieldAgentAssignmentDTO the serviceOrderFieldAgentAssignmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceOrderFieldAgentAssignmentDTO, or with status {@code 400 (Bad Request)} if the serviceOrderFieldAgentAssignment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServiceOrderFieldAgentAssignmentDTO> createServiceOrderFieldAgentAssignment(
        @Valid @RequestBody ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save ServiceOrderFieldAgentAssignment : {}", serviceOrderFieldAgentAssignmentDTO);
        if (serviceOrderFieldAgentAssignmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceOrderFieldAgentAssignment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        serviceOrderFieldAgentAssignmentDTO = serviceOrderFieldAgentAssignmentService.save(serviceOrderFieldAgentAssignmentDTO);
        return ResponseEntity.created(new URI("/api/service-order-field-agent-assignments/" + serviceOrderFieldAgentAssignmentDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    serviceOrderFieldAgentAssignmentDTO.getId().toString()
                )
            )
            .body(serviceOrderFieldAgentAssignmentDTO);
    }

    /**
     * {@code PUT  /service-order-field-agent-assignments/:id} : Updates an existing serviceOrderFieldAgentAssignment.
     *
     * @param id the id of the serviceOrderFieldAgentAssignmentDTO to save.
     * @param serviceOrderFieldAgentAssignmentDTO the serviceOrderFieldAgentAssignmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderFieldAgentAssignmentDTO,
     * or with status {@code 400 (Bad Request)} if the serviceOrderFieldAgentAssignmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderFieldAgentAssignmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceOrderFieldAgentAssignmentDTO> updateServiceOrderFieldAgentAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ServiceOrderFieldAgentAssignment : {}, {}", id, serviceOrderFieldAgentAssignmentDTO);
        if (serviceOrderFieldAgentAssignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderFieldAgentAssignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderFieldAgentAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        serviceOrderFieldAgentAssignmentDTO = serviceOrderFieldAgentAssignmentService.update(serviceOrderFieldAgentAssignmentDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    serviceOrderFieldAgentAssignmentDTO.getId().toString()
                )
            )
            .body(serviceOrderFieldAgentAssignmentDTO);
    }

    /**
     * {@code PATCH  /service-order-field-agent-assignments/:id} : Partial updates given fields of an existing serviceOrderFieldAgentAssignment, field will ignore if it is null
     *
     * @param id the id of the serviceOrderFieldAgentAssignmentDTO to save.
     * @param serviceOrderFieldAgentAssignmentDTO the serviceOrderFieldAgentAssignmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderFieldAgentAssignmentDTO,
     * or with status {@code 400 (Bad Request)} if the serviceOrderFieldAgentAssignmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the serviceOrderFieldAgentAssignmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderFieldAgentAssignmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServiceOrderFieldAgentAssignmentDTO> partialUpdateServiceOrderFieldAgentAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO
    ) throws URISyntaxException {
        LOG.debug(
            "REST request to partial update ServiceOrderFieldAgentAssignment partially : {}, {}",
            id,
            serviceOrderFieldAgentAssignmentDTO
        );
        if (serviceOrderFieldAgentAssignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderFieldAgentAssignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderFieldAgentAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceOrderFieldAgentAssignmentDTO> result = serviceOrderFieldAgentAssignmentService.partialUpdate(
            serviceOrderFieldAgentAssignmentDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrderFieldAgentAssignmentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /service-order-field-agent-assignments} : get all the serviceOrderFieldAgentAssignments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceOrderFieldAgentAssignments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ServiceOrderFieldAgentAssignmentDTO>> getAllServiceOrderFieldAgentAssignments(
        ServiceOrderFieldAgentAssignmentCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get ServiceOrderFieldAgentAssignments by criteria: {}", criteria);

        Page<ServiceOrderFieldAgentAssignmentDTO> page = serviceOrderFieldAgentAssignmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-order-field-agent-assignments/count} : count all the serviceOrderFieldAgentAssignments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countServiceOrderFieldAgentAssignments(ServiceOrderFieldAgentAssignmentCriteria criteria) {
        LOG.debug("REST request to count ServiceOrderFieldAgentAssignments by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceOrderFieldAgentAssignmentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-order-field-agent-assignments/:id} : get the "id" serviceOrderFieldAgentAssignment.
     *
     * @param id the id of the serviceOrderFieldAgentAssignmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceOrderFieldAgentAssignmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceOrderFieldAgentAssignmentDTO> getServiceOrderFieldAgentAssignment(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ServiceOrderFieldAgentAssignment : {}", id);
        Optional<ServiceOrderFieldAgentAssignmentDTO> serviceOrderFieldAgentAssignmentDTO = serviceOrderFieldAgentAssignmentService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(serviceOrderFieldAgentAssignmentDTO);
    }

    /**
     * {@code DELETE  /service-order-field-agent-assignments/:id} : delete the "id" serviceOrderFieldAgentAssignment.
     *
     * @param id the id of the serviceOrderFieldAgentAssignmentDTO to delete.
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
