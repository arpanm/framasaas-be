package com.framasaas.web.rest;

import com.framasaas.repository.PincodeRepository;
import com.framasaas.service.PincodeQueryService;
import com.framasaas.service.PincodeService;
import com.framasaas.service.criteria.PincodeCriteria;
import com.framasaas.service.dto.PincodeDTO;
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
 * REST controller for managing {@link com.framasaas.domain.Pincode}.
 */
@RestController
@RequestMapping("/api/pincodes")
public class PincodeResource {

    private static final Logger LOG = LoggerFactory.getLogger(PincodeResource.class);

    private static final String ENTITY_NAME = "pincode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PincodeService pincodeService;

    private final PincodeRepository pincodeRepository;

    private final PincodeQueryService pincodeQueryService;

    public PincodeResource(PincodeService pincodeService, PincodeRepository pincodeRepository, PincodeQueryService pincodeQueryService) {
        this.pincodeService = pincodeService;
        this.pincodeRepository = pincodeRepository;
        this.pincodeQueryService = pincodeQueryService;
    }

    /**
     * {@code POST  /pincodes} : Create a new pincode.
     *
     * @param pincodeDTO the pincodeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pincodeDTO, or with status {@code 400 (Bad Request)} if the pincode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PincodeDTO> createPincode(@Valid @RequestBody PincodeDTO pincodeDTO) throws URISyntaxException {
        LOG.debug("REST request to save Pincode : {}", pincodeDTO);
        if (pincodeDTO.getId() != null) {
            throw new BadRequestAlertException("A new pincode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pincodeDTO = pincodeService.save(pincodeDTO);
        return ResponseEntity.created(new URI("/api/pincodes/" + pincodeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, pincodeDTO.getId().toString()))
            .body(pincodeDTO);
    }

    /**
     * {@code PUT  /pincodes/:id} : Updates an existing pincode.
     *
     * @param id the id of the pincodeDTO to save.
     * @param pincodeDTO the pincodeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pincodeDTO,
     * or with status {@code 400 (Bad Request)} if the pincodeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pincodeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PincodeDTO> updatePincode(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PincodeDTO pincodeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Pincode : {}, {}", id, pincodeDTO);
        if (pincodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pincodeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pincodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pincodeDTO = pincodeService.update(pincodeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pincodeDTO.getId().toString()))
            .body(pincodeDTO);
    }

    /**
     * {@code PATCH  /pincodes/:id} : Partial updates given fields of an existing pincode, field will ignore if it is null
     *
     * @param id the id of the pincodeDTO to save.
     * @param pincodeDTO the pincodeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pincodeDTO,
     * or with status {@code 400 (Bad Request)} if the pincodeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pincodeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pincodeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PincodeDTO> partialUpdatePincode(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PincodeDTO pincodeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Pincode partially : {}, {}", id, pincodeDTO);
        if (pincodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pincodeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pincodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PincodeDTO> result = pincodeService.partialUpdate(pincodeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pincodeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pincodes} : get all the pincodes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pincodes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PincodeDTO>> getAllPincodes(
        PincodeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Pincodes by criteria: {}", criteria);

        Page<PincodeDTO> page = pincodeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pincodes/count} : count all the pincodes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPincodes(PincodeCriteria criteria) {
        LOG.debug("REST request to count Pincodes by criteria: {}", criteria);
        return ResponseEntity.ok().body(pincodeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /pincodes/:id} : get the "id" pincode.
     *
     * @param id the id of the pincodeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pincodeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PincodeDTO> getPincode(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Pincode : {}", id);
        Optional<PincodeDTO> pincodeDTO = pincodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pincodeDTO);
    }

    /**
     * {@code DELETE  /pincodes/:id} : delete the "id" pincode.
     *
     * @param id the id of the pincodeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePincode(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Pincode : {}", id);
        pincodeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
