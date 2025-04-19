package com.framasaas.web.rest;

import com.framasaas.repository.HsnRepository;
import com.framasaas.service.HsnQueryService;
import com.framasaas.service.HsnService;
import com.framasaas.service.criteria.HsnCriteria;
import com.framasaas.service.dto.HsnDTO;
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
 * REST controller for managing {@link com.framasaas.domain.Hsn}.
 */
@RestController
@RequestMapping("/api/hsns")
public class HsnResource {

    private static final Logger LOG = LoggerFactory.getLogger(HsnResource.class);

    private static final String ENTITY_NAME = "hsn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HsnService hsnService;

    private final HsnRepository hsnRepository;

    private final HsnQueryService hsnQueryService;

    public HsnResource(HsnService hsnService, HsnRepository hsnRepository, HsnQueryService hsnQueryService) {
        this.hsnService = hsnService;
        this.hsnRepository = hsnRepository;
        this.hsnQueryService = hsnQueryService;
    }

    /**
     * {@code POST  /hsns} : Create a new hsn.
     *
     * @param hsnDTO the hsnDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hsnDTO, or with status {@code 400 (Bad Request)} if the hsn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HsnDTO> createHsn(@Valid @RequestBody HsnDTO hsnDTO) throws URISyntaxException {
        LOG.debug("REST request to save Hsn : {}", hsnDTO);
        if (hsnDTO.getId() != null) {
            throw new BadRequestAlertException("A new hsn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        hsnDTO = hsnService.save(hsnDTO);
        return ResponseEntity.created(new URI("/api/hsns/" + hsnDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, hsnDTO.getId().toString()))
            .body(hsnDTO);
    }

    /**
     * {@code PUT  /hsns/:id} : Updates an existing hsn.
     *
     * @param id the id of the hsnDTO to save.
     * @param hsnDTO the hsnDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hsnDTO,
     * or with status {@code 400 (Bad Request)} if the hsnDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hsnDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HsnDTO> updateHsn(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody HsnDTO hsnDTO)
        throws URISyntaxException {
        LOG.debug("REST request to update Hsn : {}, {}", id, hsnDTO);
        if (hsnDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hsnDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hsnRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        hsnDTO = hsnService.update(hsnDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hsnDTO.getId().toString()))
            .body(hsnDTO);
    }

    /**
     * {@code PATCH  /hsns/:id} : Partial updates given fields of an existing hsn, field will ignore if it is null
     *
     * @param id the id of the hsnDTO to save.
     * @param hsnDTO the hsnDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hsnDTO,
     * or with status {@code 400 (Bad Request)} if the hsnDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hsnDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hsnDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HsnDTO> partialUpdateHsn(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HsnDTO hsnDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Hsn partially : {}, {}", id, hsnDTO);
        if (hsnDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hsnDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hsnRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HsnDTO> result = hsnService.partialUpdate(hsnDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hsnDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hsns} : get all the hsns.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hsns in body.
     */
    @GetMapping("")
    public ResponseEntity<List<HsnDTO>> getAllHsns(
        HsnCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Hsns by criteria: {}", criteria);

        Page<HsnDTO> page = hsnQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hsns/count} : count all the hsns.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countHsns(HsnCriteria criteria) {
        LOG.debug("REST request to count Hsns by criteria: {}", criteria);
        return ResponseEntity.ok().body(hsnQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /hsns/:id} : get the "id" hsn.
     *
     * @param id the id of the hsnDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hsnDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HsnDTO> getHsn(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Hsn : {}", id);
        Optional<HsnDTO> hsnDTO = hsnService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hsnDTO);
    }

    /**
     * {@code DELETE  /hsns/:id} : delete the "id" hsn.
     *
     * @param id the id of the hsnDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHsn(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Hsn : {}", id);
        hsnService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
