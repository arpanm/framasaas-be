package com.framasaas.web.rest;

import com.framasaas.repository.FieldAgentSkillRuleSetRepository;
import com.framasaas.service.FieldAgentSkillRuleSetQueryService;
import com.framasaas.service.FieldAgentSkillRuleSetService;
import com.framasaas.service.criteria.FieldAgentSkillRuleSetCriteria;
import com.framasaas.service.dto.FieldAgentSkillRuleSetDTO;
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
 * REST controller for managing {@link com.framasaas.domain.FieldAgentSkillRuleSet}.
 */
@RestController
@RequestMapping("/api/field-agent-skill-rule-sets")
public class FieldAgentSkillRuleSetResource {

    private static final Logger LOG = LoggerFactory.getLogger(FieldAgentSkillRuleSetResource.class);

    private static final String ENTITY_NAME = "fieldAgentSkillRuleSet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldAgentSkillRuleSetService fieldAgentSkillRuleSetService;

    private final FieldAgentSkillRuleSetRepository fieldAgentSkillRuleSetRepository;

    private final FieldAgentSkillRuleSetQueryService fieldAgentSkillRuleSetQueryService;

    public FieldAgentSkillRuleSetResource(
        FieldAgentSkillRuleSetService fieldAgentSkillRuleSetService,
        FieldAgentSkillRuleSetRepository fieldAgentSkillRuleSetRepository,
        FieldAgentSkillRuleSetQueryService fieldAgentSkillRuleSetQueryService
    ) {
        this.fieldAgentSkillRuleSetService = fieldAgentSkillRuleSetService;
        this.fieldAgentSkillRuleSetRepository = fieldAgentSkillRuleSetRepository;
        this.fieldAgentSkillRuleSetQueryService = fieldAgentSkillRuleSetQueryService;
    }

    /**
     * {@code POST  /field-agent-skill-rule-sets} : Create a new fieldAgentSkillRuleSet.
     *
     * @param fieldAgentSkillRuleSetDTO the fieldAgentSkillRuleSetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldAgentSkillRuleSetDTO, or with status {@code 400 (Bad Request)} if the fieldAgentSkillRuleSet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FieldAgentSkillRuleSetDTO> createFieldAgentSkillRuleSet(
        @Valid @RequestBody FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save FieldAgentSkillRuleSet : {}", fieldAgentSkillRuleSetDTO);
        if (fieldAgentSkillRuleSetDTO.getId() != null) {
            throw new BadRequestAlertException("A new fieldAgentSkillRuleSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fieldAgentSkillRuleSetDTO = fieldAgentSkillRuleSetService.save(fieldAgentSkillRuleSetDTO);
        return ResponseEntity.created(new URI("/api/field-agent-skill-rule-sets/" + fieldAgentSkillRuleSetDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, fieldAgentSkillRuleSetDTO.getId().toString()))
            .body(fieldAgentSkillRuleSetDTO);
    }

    /**
     * {@code PUT  /field-agent-skill-rule-sets/:id} : Updates an existing fieldAgentSkillRuleSet.
     *
     * @param id the id of the fieldAgentSkillRuleSetDTO to save.
     * @param fieldAgentSkillRuleSetDTO the fieldAgentSkillRuleSetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldAgentSkillRuleSetDTO,
     * or with status {@code 400 (Bad Request)} if the fieldAgentSkillRuleSetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldAgentSkillRuleSetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FieldAgentSkillRuleSetDTO> updateFieldAgentSkillRuleSet(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update FieldAgentSkillRuleSet : {}, {}", id, fieldAgentSkillRuleSetDTO);
        if (fieldAgentSkillRuleSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldAgentSkillRuleSetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldAgentSkillRuleSetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fieldAgentSkillRuleSetDTO = fieldAgentSkillRuleSetService.update(fieldAgentSkillRuleSetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldAgentSkillRuleSetDTO.getId().toString()))
            .body(fieldAgentSkillRuleSetDTO);
    }

    /**
     * {@code PATCH  /field-agent-skill-rule-sets/:id} : Partial updates given fields of an existing fieldAgentSkillRuleSet, field will ignore if it is null
     *
     * @param id the id of the fieldAgentSkillRuleSetDTO to save.
     * @param fieldAgentSkillRuleSetDTO the fieldAgentSkillRuleSetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldAgentSkillRuleSetDTO,
     * or with status {@code 400 (Bad Request)} if the fieldAgentSkillRuleSetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fieldAgentSkillRuleSetDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldAgentSkillRuleSetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FieldAgentSkillRuleSetDTO> partialUpdateFieldAgentSkillRuleSet(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FieldAgentSkillRuleSet partially : {}, {}", id, fieldAgentSkillRuleSetDTO);
        if (fieldAgentSkillRuleSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldAgentSkillRuleSetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldAgentSkillRuleSetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FieldAgentSkillRuleSetDTO> result = fieldAgentSkillRuleSetService.partialUpdate(fieldAgentSkillRuleSetDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldAgentSkillRuleSetDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /field-agent-skill-rule-sets} : get all the fieldAgentSkillRuleSets.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldAgentSkillRuleSets in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FieldAgentSkillRuleSetDTO>> getAllFieldAgentSkillRuleSets(
        FieldAgentSkillRuleSetCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get FieldAgentSkillRuleSets by criteria: {}", criteria);

        Page<FieldAgentSkillRuleSetDTO> page = fieldAgentSkillRuleSetQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /field-agent-skill-rule-sets/count} : count all the fieldAgentSkillRuleSets.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFieldAgentSkillRuleSets(FieldAgentSkillRuleSetCriteria criteria) {
        LOG.debug("REST request to count FieldAgentSkillRuleSets by criteria: {}", criteria);
        return ResponseEntity.ok().body(fieldAgentSkillRuleSetQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /field-agent-skill-rule-sets/:id} : get the "id" fieldAgentSkillRuleSet.
     *
     * @param id the id of the fieldAgentSkillRuleSetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldAgentSkillRuleSetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FieldAgentSkillRuleSetDTO> getFieldAgentSkillRuleSet(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FieldAgentSkillRuleSet : {}", id);
        Optional<FieldAgentSkillRuleSetDTO> fieldAgentSkillRuleSetDTO = fieldAgentSkillRuleSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldAgentSkillRuleSetDTO);
    }

    /**
     * {@code DELETE  /field-agent-skill-rule-sets/:id} : delete the "id" fieldAgentSkillRuleSet.
     *
     * @param id the id of the fieldAgentSkillRuleSetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFieldAgentSkillRuleSet(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FieldAgentSkillRuleSet : {}", id);
        fieldAgentSkillRuleSetService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
