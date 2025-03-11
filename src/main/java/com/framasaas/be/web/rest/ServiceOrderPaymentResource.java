package com.framasaas.be.web.rest;

import com.framasaas.be.domain.ServiceOrderPayment;
import com.framasaas.be.repository.ServiceOrderPaymentRepository;
import com.framasaas.be.service.ServiceOrderPaymentService;
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
 * REST controller for managing {@link com.framasaas.be.domain.ServiceOrderPayment}.
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

    public ServiceOrderPaymentResource(
        ServiceOrderPaymentService serviceOrderPaymentService,
        ServiceOrderPaymentRepository serviceOrderPaymentRepository
    ) {
        this.serviceOrderPaymentService = serviceOrderPaymentService;
        this.serviceOrderPaymentRepository = serviceOrderPaymentRepository;
    }

    /**
     * {@code POST  /service-order-payments} : Create a new serviceOrderPayment.
     *
     * @param serviceOrderPayment the serviceOrderPayment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceOrderPayment, or with status {@code 400 (Bad Request)} if the serviceOrderPayment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServiceOrderPayment> createServiceOrderPayment(@Valid @RequestBody ServiceOrderPayment serviceOrderPayment)
        throws URISyntaxException {
        LOG.debug("REST request to save ServiceOrderPayment : {}", serviceOrderPayment);
        if (serviceOrderPayment.getId() != null) {
            throw new BadRequestAlertException("A new serviceOrderPayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        serviceOrderPayment = serviceOrderPaymentService.save(serviceOrderPayment);
        return ResponseEntity.created(new URI("/api/service-order-payments/" + serviceOrderPayment.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, serviceOrderPayment.getId().toString()))
            .body(serviceOrderPayment);
    }

    /**
     * {@code PUT  /service-order-payments/:id} : Updates an existing serviceOrderPayment.
     *
     * @param id the id of the serviceOrderPayment to save.
     * @param serviceOrderPayment the serviceOrderPayment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderPayment,
     * or with status {@code 400 (Bad Request)} if the serviceOrderPayment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderPayment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceOrderPayment> updateServiceOrderPayment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServiceOrderPayment serviceOrderPayment
    ) throws URISyntaxException {
        LOG.debug("REST request to update ServiceOrderPayment : {}, {}", id, serviceOrderPayment);
        if (serviceOrderPayment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderPayment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderPaymentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        serviceOrderPayment = serviceOrderPaymentService.update(serviceOrderPayment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrderPayment.getId().toString()))
            .body(serviceOrderPayment);
    }

    /**
     * {@code PATCH  /service-order-payments/:id} : Partial updates given fields of an existing serviceOrderPayment, field will ignore if it is null
     *
     * @param id the id of the serviceOrderPayment to save.
     * @param serviceOrderPayment the serviceOrderPayment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderPayment,
     * or with status {@code 400 (Bad Request)} if the serviceOrderPayment is not valid,
     * or with status {@code 404 (Not Found)} if the serviceOrderPayment is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderPayment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServiceOrderPayment> partialUpdateServiceOrderPayment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServiceOrderPayment serviceOrderPayment
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ServiceOrderPayment partially : {}, {}", id, serviceOrderPayment);
        if (serviceOrderPayment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderPayment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderPaymentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceOrderPayment> result = serviceOrderPaymentService.partialUpdate(serviceOrderPayment);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrderPayment.getId().toString())
        );
    }

    /**
     * {@code GET  /service-order-payments} : get all the serviceOrderPayments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceOrderPayments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ServiceOrderPayment>> getAllServiceOrderPayments(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of ServiceOrderPayments");
        Page<ServiceOrderPayment> page = serviceOrderPaymentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-order-payments/:id} : get the "id" serviceOrderPayment.
     *
     * @param id the id of the serviceOrderPayment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceOrderPayment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceOrderPayment> getServiceOrderPayment(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ServiceOrderPayment : {}", id);
        Optional<ServiceOrderPayment> serviceOrderPayment = serviceOrderPaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceOrderPayment);
    }

    /**
     * {@code DELETE  /service-order-payments/:id} : delete the "id" serviceOrderPayment.
     *
     * @param id the id of the serviceOrderPayment to delete.
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
