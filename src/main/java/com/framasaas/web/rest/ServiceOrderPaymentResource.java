package com.framasaas.web.rest;

import com.framasaas.repository.ServiceOrderPaymentRepository;
import com.framasaas.service.ServiceOrderPaymentQueryService;
import com.framasaas.service.ServiceOrderPaymentService;
import com.framasaas.service.criteria.ServiceOrderPaymentCriteria;
import com.framasaas.service.dto.ServiceOrderPaymentDTO;
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
 * REST controller for managing {@link com.framasaas.domain.ServiceOrderPayment}.
 */
@RestController
@RequestMapping("/api/service-order-payments")
public class ServiceOrderPaymentResource {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderPaymentResource.class);

    private static final String ENTITY_NAME = "serviceOrderPayment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceOrderPaymentService serviceOrderPaymentService;

    private final ServiceOrderPaymentRepository serviceOrderPaymentRepository;

    private final ServiceOrderPaymentQueryService serviceOrderPaymentQueryService;

    public ServiceOrderPaymentResource(
        ServiceOrderPaymentService serviceOrderPaymentService,
        ServiceOrderPaymentRepository serviceOrderPaymentRepository,
        ServiceOrderPaymentQueryService serviceOrderPaymentQueryService
    ) {
        this.serviceOrderPaymentService = serviceOrderPaymentService;
        this.serviceOrderPaymentRepository = serviceOrderPaymentRepository;
        this.serviceOrderPaymentQueryService = serviceOrderPaymentQueryService;
    }

    /**
     * {@code POST  /service-order-payments} : Create a new serviceOrderPayment.
     *
     * @param serviceOrderPaymentDTO the serviceOrderPaymentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceOrderPaymentDTO, or with status {@code 400 (Bad Request)} if the serviceOrderPayment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServiceOrderPaymentDTO> createServiceOrderPayment(
        @Valid @RequestBody ServiceOrderPaymentDTO serviceOrderPaymentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save ServiceOrderPayment : {}", serviceOrderPaymentDTO);
        if (serviceOrderPaymentDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceOrderPayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        serviceOrderPaymentDTO = serviceOrderPaymentService.save(serviceOrderPaymentDTO);
        return ResponseEntity.created(new URI("/api/service-order-payments/" + serviceOrderPaymentDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, serviceOrderPaymentDTO.getId().toString()))
            .body(serviceOrderPaymentDTO);
    }

    /**
     * {@code PUT  /service-order-payments/:id} : Updates an existing serviceOrderPayment.
     *
     * @param id the id of the serviceOrderPaymentDTO to save.
     * @param serviceOrderPaymentDTO the serviceOrderPaymentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderPaymentDTO,
     * or with status {@code 400 (Bad Request)} if the serviceOrderPaymentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderPaymentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceOrderPaymentDTO> updateServiceOrderPayment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServiceOrderPaymentDTO serviceOrderPaymentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ServiceOrderPayment : {}, {}", id, serviceOrderPaymentDTO);
        if (serviceOrderPaymentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderPaymentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderPaymentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        serviceOrderPaymentDTO = serviceOrderPaymentService.update(serviceOrderPaymentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrderPaymentDTO.getId().toString()))
            .body(serviceOrderPaymentDTO);
    }

    /**
     * {@code PATCH  /service-order-payments/:id} : Partial updates given fields of an existing serviceOrderPayment, field will ignore if it is null
     *
     * @param id the id of the serviceOrderPaymentDTO to save.
     * @param serviceOrderPaymentDTO the serviceOrderPaymentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderPaymentDTO,
     * or with status {@code 400 (Bad Request)} if the serviceOrderPaymentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the serviceOrderPaymentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderPaymentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServiceOrderPaymentDTO> partialUpdateServiceOrderPayment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServiceOrderPaymentDTO serviceOrderPaymentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ServiceOrderPayment partially : {}, {}", id, serviceOrderPaymentDTO);
        if (serviceOrderPaymentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderPaymentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderPaymentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceOrderPaymentDTO> result = serviceOrderPaymentService.partialUpdate(serviceOrderPaymentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrderPaymentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /service-order-payments} : get all the serviceOrderPayments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceOrderPayments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ServiceOrderPaymentDTO>> getAllServiceOrderPayments(
        ServiceOrderPaymentCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get ServiceOrderPayments by criteria: {}", criteria);

        Page<ServiceOrderPaymentDTO> page = serviceOrderPaymentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-order-payments/count} : count all the serviceOrderPayments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countServiceOrderPayments(ServiceOrderPaymentCriteria criteria) {
        LOG.debug("REST request to count ServiceOrderPayments by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceOrderPaymentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-order-payments/:id} : get the "id" serviceOrderPayment.
     *
     * @param id the id of the serviceOrderPaymentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceOrderPaymentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceOrderPaymentDTO> getServiceOrderPayment(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ServiceOrderPayment : {}", id);
        Optional<ServiceOrderPaymentDTO> serviceOrderPaymentDTO = serviceOrderPaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceOrderPaymentDTO);
    }

    /**
     * {@code DELETE  /service-order-payments/:id} : delete the "id" serviceOrderPayment.
     *
     * @param id the id of the serviceOrderPaymentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceOrderPayment(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ServiceOrderPayment : {}", id);
        serviceOrderPaymentService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
