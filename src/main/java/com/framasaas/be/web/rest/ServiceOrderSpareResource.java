package com.framasaas.be.web.rest;

import com.framasaas.be.domain.ServiceOrderSpare;
import com.framasaas.be.repository.ServiceOrderSpareRepository;
import com.framasaas.be.service.ServiceOrderSpareService;
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
 * REST controller for managing {@link com.framasaas.be.domain.ServiceOrderSpare}.
 */
@RestController
@RequestMapping("/api/service-order-spares")
public class ServiceOrderSpareResource {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderSpareResource.class);

    private static final String ENTITY_NAME = "serviceOrderSpare";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceOrderSpareService serviceOrderSpareService;

    private final ServiceOrderSpareRepository serviceOrderSpareRepository;

    public ServiceOrderSpareResource(
        ServiceOrderSpareService serviceOrderSpareService,
        ServiceOrderSpareRepository serviceOrderSpareRepository
    ) {
        this.serviceOrderSpareService = serviceOrderSpareService;
        this.serviceOrderSpareRepository = serviceOrderSpareRepository;
    }

    /**
     * {@code POST  /service-order-spares} : Create a new serviceOrderSpare.
     *
     * @param serviceOrderSpare the serviceOrderSpare to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceOrderSpare, or with status {@code 400 (Bad Request)} if the serviceOrderSpare has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServiceOrderSpare> createServiceOrderSpare(@Valid @RequestBody ServiceOrderSpare serviceOrderSpare)
        throws URISyntaxException {
        LOG.debug("REST request to save ServiceOrderSpare : {}", serviceOrderSpare);
        if (serviceOrderSpare.getId() != null) {
            throw new BadRequestAlertException("A new serviceOrderSpare cannot already have an ID", ENTITY_NAME, "idexists");
        }
        serviceOrderSpare = serviceOrderSpareService.save(serviceOrderSpare);
        return ResponseEntity.created(new URI("/api/service-order-spares/" + serviceOrderSpare.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, serviceOrderSpare.getId().toString()))
            .body(serviceOrderSpare);
    }

    /**
     * {@code PUT  /service-order-spares/:id} : Updates an existing serviceOrderSpare.
     *
     * @param id the id of the serviceOrderSpare to save.
     * @param serviceOrderSpare the serviceOrderSpare to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderSpare,
     * or with status {@code 400 (Bad Request)} if the serviceOrderSpare is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderSpare couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceOrderSpare> updateServiceOrderSpare(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServiceOrderSpare serviceOrderSpare
    ) throws URISyntaxException {
        LOG.debug("REST request to update ServiceOrderSpare : {}, {}", id, serviceOrderSpare);
        if (serviceOrderSpare.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderSpare.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderSpareRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        serviceOrderSpare = serviceOrderSpareService.update(serviceOrderSpare);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrderSpare.getId().toString()))
            .body(serviceOrderSpare);
    }

    /**
     * {@code PATCH  /service-order-spares/:id} : Partial updates given fields of an existing serviceOrderSpare, field will ignore if it is null
     *
     * @param id the id of the serviceOrderSpare to save.
     * @param serviceOrderSpare the serviceOrderSpare to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderSpare,
     * or with status {@code 400 (Bad Request)} if the serviceOrderSpare is not valid,
     * or with status {@code 404 (Not Found)} if the serviceOrderSpare is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderSpare couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServiceOrderSpare> partialUpdateServiceOrderSpare(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServiceOrderSpare serviceOrderSpare
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ServiceOrderSpare partially : {}, {}", id, serviceOrderSpare);
        if (serviceOrderSpare.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderSpare.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderSpareRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceOrderSpare> result = serviceOrderSpareService.partialUpdate(serviceOrderSpare);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrderSpare.getId().toString())
        );
    }

    /**
     * {@code GET  /service-order-spares} : get all the serviceOrderSpares.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceOrderSpares in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ServiceOrderSpare>> getAllServiceOrderSpares(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of ServiceOrderSpares");
        Page<ServiceOrderSpare> page = serviceOrderSpareService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-order-spares/:id} : get the "id" serviceOrderSpare.
     *
     * @param id the id of the serviceOrderSpare to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceOrderSpare, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceOrderSpare> getServiceOrderSpare(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ServiceOrderSpare : {}", id);
        Optional<ServiceOrderSpare> serviceOrderSpare = serviceOrderSpareService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceOrderSpare);
    }

    /**
     * {@code DELETE  /service-order-spares/:id} : delete the "id" serviceOrderSpare.
     *
     * @param id the id of the serviceOrderSpare to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceOrderSpare(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ServiceOrderSpare : {}", id);
        serviceOrderSpareService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
