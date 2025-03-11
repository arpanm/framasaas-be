package com.framasaas.be.web.rest;

import com.framasaas.be.domain.ServiceOrder;
import com.framasaas.be.repository.ServiceOrderRepository;
import com.framasaas.be.service.ServiceOrderService;
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
 * REST controller for managing {@link com.framasaas.be.domain.ServiceOrder}.
 */
@RestController
@RequestMapping("/api/service-orders")
public class ServiceOrderResource {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderResource.class);

    private static final String ENTITY_NAME = "serviceOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceOrderService serviceOrderService;

    private final ServiceOrderRepository serviceOrderRepository;

    public ServiceOrderResource(ServiceOrderService serviceOrderService, ServiceOrderRepository serviceOrderRepository) {
        this.serviceOrderService = serviceOrderService;
        this.serviceOrderRepository = serviceOrderRepository;
    }

    /**
     * {@code POST  /service-orders} : Create a new serviceOrder.
     *
     * @param serviceOrder the serviceOrder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceOrder, or with status {@code 400 (Bad Request)} if the serviceOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServiceOrder> createServiceOrder(@Valid @RequestBody ServiceOrder serviceOrder) throws URISyntaxException {
        LOG.debug("REST request to save ServiceOrder : {}", serviceOrder);
        if (serviceOrder.getId() != null) {
            throw new BadRequestAlertException("A new serviceOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        serviceOrder = serviceOrderService.save(serviceOrder);
        return ResponseEntity.created(new URI("/api/service-orders/" + serviceOrder.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, serviceOrder.getId().toString()))
            .body(serviceOrder);
    }

    /**
     * {@code PUT  /service-orders/:id} : Updates an existing serviceOrder.
     *
     * @param id the id of the serviceOrder to save.
     * @param serviceOrder the serviceOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrder,
     * or with status {@code 400 (Bad Request)} if the serviceOrder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceOrder> updateServiceOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServiceOrder serviceOrder
    ) throws URISyntaxException {
        LOG.debug("REST request to update ServiceOrder : {}, {}", id, serviceOrder);
        if (serviceOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        serviceOrder = serviceOrderService.update(serviceOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrder.getId().toString()))
            .body(serviceOrder);
    }

    /**
     * {@code PATCH  /service-orders/:id} : Partial updates given fields of an existing serviceOrder, field will ignore if it is null
     *
     * @param id the id of the serviceOrder to save.
     * @param serviceOrder the serviceOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrder,
     * or with status {@code 400 (Bad Request)} if the serviceOrder is not valid,
     * or with status {@code 404 (Not Found)} if the serviceOrder is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServiceOrder> partialUpdateServiceOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServiceOrder serviceOrder
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ServiceOrder partially : {}, {}", id, serviceOrder);
        if (serviceOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceOrder> result = serviceOrderService.partialUpdate(serviceOrder);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrder.getId().toString())
        );
    }

    /**
     * {@code GET  /service-orders} : get all the serviceOrders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceOrders in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ServiceOrder>> getAllServiceOrders(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of ServiceOrders");
        Page<ServiceOrder> page = serviceOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-orders/:id} : get the "id" serviceOrder.
     *
     * @param id the id of the serviceOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceOrder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceOrder> getServiceOrder(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ServiceOrder : {}", id);
        Optional<ServiceOrder> serviceOrder = serviceOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceOrder);
    }

    /**
     * {@code DELETE  /service-orders/:id} : delete the "id" serviceOrder.
     *
     * @param id the id of the serviceOrder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceOrder(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ServiceOrder : {}", id);
        serviceOrderService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
