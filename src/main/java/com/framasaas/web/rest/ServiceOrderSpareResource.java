package com.framasaas.web.rest;

import com.framasaas.repository.ServiceOrderSpareRepository;
import com.framasaas.service.ServiceOrderSpareQueryService;
import com.framasaas.service.ServiceOrderSpareService;
import com.framasaas.service.criteria.ServiceOrderSpareCriteria;
import com.framasaas.service.dto.ServiceOrderSpareDTO;
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
 * REST controller for managing {@link com.framasaas.domain.ServiceOrderSpare}.
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

    private final ServiceOrderSpareQueryService serviceOrderSpareQueryService;

    public ServiceOrderSpareResource(
        ServiceOrderSpareService serviceOrderSpareService,
        ServiceOrderSpareRepository serviceOrderSpareRepository,
        ServiceOrderSpareQueryService serviceOrderSpareQueryService
    ) {
        this.serviceOrderSpareService = serviceOrderSpareService;
        this.serviceOrderSpareRepository = serviceOrderSpareRepository;
        this.serviceOrderSpareQueryService = serviceOrderSpareQueryService;
    }

    /**
     * {@code POST  /service-order-spares} : Create a new serviceOrderSpare.
     *
     * @param serviceOrderSpareDTO the serviceOrderSpareDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceOrderSpareDTO, or with status {@code 400 (Bad Request)} if the serviceOrderSpare has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServiceOrderSpareDTO> createServiceOrderSpare(@Valid @RequestBody ServiceOrderSpareDTO serviceOrderSpareDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save ServiceOrderSpare : {}", serviceOrderSpareDTO);
        if (serviceOrderSpareDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceOrderSpare cannot already have an ID", ENTITY_NAME, "idexists");
        }
        serviceOrderSpareDTO = serviceOrderSpareService.save(serviceOrderSpareDTO);
        return ResponseEntity.created(new URI("/api/service-order-spares/" + serviceOrderSpareDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, serviceOrderSpareDTO.getId().toString()))
            .body(serviceOrderSpareDTO);
    }

    /**
     * {@code PUT  /service-order-spares/:id} : Updates an existing serviceOrderSpare.
     *
     * @param id the id of the serviceOrderSpareDTO to save.
     * @param serviceOrderSpareDTO the serviceOrderSpareDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderSpareDTO,
     * or with status {@code 400 (Bad Request)} if the serviceOrderSpareDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderSpareDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceOrderSpareDTO> updateServiceOrderSpare(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServiceOrderSpareDTO serviceOrderSpareDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ServiceOrderSpare : {}, {}", id, serviceOrderSpareDTO);
        if (serviceOrderSpareDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderSpareDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderSpareRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        serviceOrderSpareDTO = serviceOrderSpareService.update(serviceOrderSpareDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrderSpareDTO.getId().toString()))
            .body(serviceOrderSpareDTO);
    }

    /**
     * {@code PATCH  /service-order-spares/:id} : Partial updates given fields of an existing serviceOrderSpare, field will ignore if it is null
     *
     * @param id the id of the serviceOrderSpareDTO to save.
     * @param serviceOrderSpareDTO the serviceOrderSpareDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrderSpareDTO,
     * or with status {@code 400 (Bad Request)} if the serviceOrderSpareDTO is not valid,
     * or with status {@code 404 (Not Found)} if the serviceOrderSpareDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrderSpareDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServiceOrderSpareDTO> partialUpdateServiceOrderSpare(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServiceOrderSpareDTO serviceOrderSpareDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ServiceOrderSpare partially : {}, {}", id, serviceOrderSpareDTO);
        if (serviceOrderSpareDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceOrderSpareDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceOrderSpareRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceOrderSpareDTO> result = serviceOrderSpareService.partialUpdate(serviceOrderSpareDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOrderSpareDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /service-order-spares} : get all the serviceOrderSpares.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceOrderSpares in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ServiceOrderSpareDTO>> getAllServiceOrderSpares(
        ServiceOrderSpareCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get ServiceOrderSpares by criteria: {}", criteria);

        Page<ServiceOrderSpareDTO> page = serviceOrderSpareQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-order-spares/count} : count all the serviceOrderSpares.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countServiceOrderSpares(ServiceOrderSpareCriteria criteria) {
        LOG.debug("REST request to count ServiceOrderSpares by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceOrderSpareQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-order-spares/:id} : get the "id" serviceOrderSpare.
     *
     * @param id the id of the serviceOrderSpareDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceOrderSpareDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceOrderSpareDTO> getServiceOrderSpare(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ServiceOrderSpare : {}", id);
        Optional<ServiceOrderSpareDTO> serviceOrderSpareDTO = serviceOrderSpareService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceOrderSpareDTO);
    }

    /**
     * {@code DELETE  /service-order-spares/:id} : delete the "id" serviceOrderSpare.
     *
     * @param id the id of the serviceOrderSpareDTO to delete.
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
