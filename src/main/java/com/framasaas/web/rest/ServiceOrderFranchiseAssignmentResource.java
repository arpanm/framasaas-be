package com.framasaas.web.rest;

import com.framasaas.repository.ServiceOrderFranchiseAssignmentRepository;
import com.framasaas.service.ServiceOrderFranchiseAssignmentQueryService;
import com.framasaas.service.ServiceOrderFranchiseAssignmentService;
import com.framasaas.service.criteria.ServiceOrderFranchiseAssignmentCriteria;
import com.framasaas.service.dto.ServiceOrderFranchiseAssignmentDTO;
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
 * REST controller for managing {@link com.framasaas.domain.ServiceOrderFranchiseAssignment}.
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

    private final ServiceOrderFranchiseAssignmentQueryService serviceOrderFranchiseAssignmentQueryService;

    public ServiceOrderFranchiseAssignmentResource(
        ServiceOrderFranchiseAssignmentService serviceOrderFranchiseAssignmentService,
        ServiceOrderFranchiseAssignmentRepository serviceOrderFranchiseAssignmentRepository,
        ServiceOrderFranchiseAssignmentQueryService serviceOrderFranchiseAssignmentQueryService
    ) {
        this.serviceOrderFranchiseAssignmentService = serviceOrderFranchiseAssignmentService;
        this.serviceOrderFranchiseAssignmentRepository = serviceOrderFranchiseAssignmentRepository;
        this.serviceOrderFranchiseAssignmentQueryService = serviceOrderFranchiseAssignmentQueryService;
    }

    /**
     * {@code POST  /service-order-franchise-assignments} : Create a new serviceOrderFranchiseAssignment.
     *
     * @param serviceOrderFranchiseAssignmentDTO the serviceOrderFranchiseAssignmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceOrderFranchiseAssignmentDTO, or with status {@code 400 (Bad Request)} if the serviceOrderFranchiseAssignment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServiceOrderFranchiseAssignmentDTO> createServiceOrderFranchiseAssignment(
        @Valid @RequestBody ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save ServiceOrderFranchiseAssignment : {}", serviceOrderFranchiseAssignmentDTO);
        if (serviceOrderFranchiseAssignmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceOrderFranchiseAssignment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        serviceOrderFranchiseAssignmentDTO = serviceOrderFranchiseAssignmentService.save(serviceOrderFranchiseAssignmentDTO);
        return ResponseEntity.created(new URI("/api/service-order-franchise-assignments/" + serviceOrderFranchiseAssignmentDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    serviceOrderFranchiseAssignmentDTO.getId().toString()
                )
            )
            .body(serviceOrderFranchiseAssignmentDTO);
    }

    /**
     * {@code PUT  /service-order-franchise-assignments/:id} : Updates an existing serviceOrderFranchiseAssignment.
     *
     * @param id the id of the serviceOrderFranchiseAssignmentDTO to save.
     * @param serviceOrderFranchiseAssignmentDTO the serviceOrderFranchiseAssignmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderFranchiseAssignmentDTO,
     * or with status {@code 400 (Bad Request)} if the serviceOrderFranchiseAssignmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderFranchiseAssignmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceOrderFranchiseAssignmentDTO> updateServiceOrderFranchiseAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ServiceOrderFranchiseAssignment : {}, {}", id, serviceOrderFranchiseAssignmentDTO);
        if (serviceOrderFranchiseAssignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderFranchiseAssignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderFranchiseAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        serviceOrderFranchiseAssignmentDTO = serviceOrderFranchiseAssignmentService.update(serviceOrderFranchiseAssignmentDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    serviceOrderFranchiseAssignmentDTO.getId().toString()
                )
            )
            .body(serviceOrderFranchiseAssignmentDTO);
    }

    /**
     * {@code PATCH  /service-order-franchise-assignments/:id} : Partial updates given fields of an existing serviceOrderFranchiseAssignment, field will ignore if it is null
     *
     * @param id the id of the serviceOrderFranchiseAssignmentDTO to save.
     * @param serviceOrderFranchiseAssignmentDTO the serviceOrderFranchiseAssignmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderFranchiseAssignmentDTO,
     * or with status {@code 400 (Bad Request)} if the serviceOrderFranchiseAssignmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the serviceOrderFranchiseAssignmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderFranchiseAssignmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServiceOrderFranchiseAssignmentDTO> partialUpdateServiceOrderFranchiseAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO
    ) throws URISyntaxException {
        LOG.debug(
            "REST request to partial update ServiceOrderFranchiseAssignment partially : {}, {}",
            id,
            serviceOrderFranchiseAssignmentDTO
        );
        if (serviceOrderFranchiseAssignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderFranchiseAssignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderFranchiseAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceOrderFranchiseAssignmentDTO> result = serviceOrderFranchiseAssignmentService.partialUpdate(
            serviceOrderFranchiseAssignmentDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrderFranchiseAssignmentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /service-order-franchise-assignments} : get all the serviceOrderFranchiseAssignments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceOrderFranchiseAssignments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ServiceOrderFranchiseAssignmentDTO>> getAllServiceOrderFranchiseAssignments(
        ServiceOrderFranchiseAssignmentCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get ServiceOrderFranchiseAssignments by criteria: {}", criteria);

        Page<ServiceOrderFranchiseAssignmentDTO> page = serviceOrderFranchiseAssignmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-order-franchise-assignments/count} : count all the serviceOrderFranchiseAssignments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countServiceOrderFranchiseAssignments(ServiceOrderFranchiseAssignmentCriteria criteria) {
        LOG.debug("REST request to count ServiceOrderFranchiseAssignments by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceOrderFranchiseAssignmentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-order-franchise-assignments/:id} : get the "id" serviceOrderFranchiseAssignment.
     *
     * @param id the id of the serviceOrderFranchiseAssignmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceOrderFranchiseAssignmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceOrderFranchiseAssignmentDTO> getServiceOrderFranchiseAssignment(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ServiceOrderFranchiseAssignment : {}", id);
        Optional<ServiceOrderFranchiseAssignmentDTO> serviceOrderFranchiseAssignmentDTO = serviceOrderFranchiseAssignmentService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(serviceOrderFranchiseAssignmentDTO);
    }

    /**
     * {@code DELETE  /service-order-franchise-assignments/:id} : delete the "id" serviceOrderFranchiseAssignment.
     *
     * @param id the id of the serviceOrderFranchiseAssignmentDTO to delete.
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
