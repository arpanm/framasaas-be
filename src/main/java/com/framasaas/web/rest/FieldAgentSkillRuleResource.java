package com.framasaas.web.rest;

import com.framasaas.repository.FieldAgentSkillRuleRepository;
import com.framasaas.service.FieldAgentSkillRuleQueryService;
import com.framasaas.service.FieldAgentSkillRuleService;
import com.framasaas.service.criteria.FieldAgentSkillRuleCriteria;
import com.framasaas.service.dto.FieldAgentSkillRuleDTO;
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
 * REST controller for managing {@link com.framasaas.domain.FieldAgentSkillRule}.
 */
@RestController
@RequestMapping("/api/field-agent-skill-rules")
public class FieldAgentSkillRuleResource {

    private static final Logger LOG = LoggerFactory.getLogger(FieldAgentSkillRuleResource.class);

    private static final String ENTITY_NAME = "fieldAgentSkillRule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldAgentSkillRuleService fieldAgentSkillRuleService;

    private final FieldAgentSkillRuleRepository fieldAgentSkillRuleRepository;

    private final FieldAgentSkillRuleQueryService fieldAgentSkillRuleQueryService;

    public FieldAgentSkillRuleResource(
        FieldAgentSkillRuleService fieldAgentSkillRuleService,
        FieldAgentSkillRuleRepository fieldAgentSkillRuleRepository,
        FieldAgentSkillRuleQueryService fieldAgentSkillRuleQueryService
    ) {
        this.fieldAgentSkillRuleService = fieldAgentSkillRuleService;
        this.fieldAgentSkillRuleRepository = fieldAgentSkillRuleRepository;
        this.fieldAgentSkillRuleQueryService = fieldAgentSkillRuleQueryService;
    }

    /**
     * {@code POST  /field-agent-skill-rules} : Create a new fieldAgentSkillRule.
     *
     * @param fieldAgentSkillRuleDTO the fieldAgentSkillRuleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldAgentSkillRuleDTO, or with status {@code 400 (Bad Request)} if the fieldAgentSkillRule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FieldAgentSkillRuleDTO> createFieldAgentSkillRule(
        @Valid @RequestBody FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save FieldAgentSkillRule : {}", fieldAgentSkillRuleDTO);
        if (fieldAgentSkillRuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new fieldAgentSkillRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fieldAgentSkillRuleDTO = fieldAgentSkillRuleService.save(fieldAgentSkillRuleDTO);
        return ResponseEntity.created(new URI("/api/field-agent-skill-rules/" + fieldAgentSkillRuleDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, fieldAgentSkillRuleDTO.getId().toString()))
            .body(fieldAgentSkillRuleDTO);
    }

    /**
     * {@code PUT  /field-agent-skill-rules/:id} : Updates an existing fieldAgentSkillRule.
     *
     * @param id the id of the fieldAgentSkillRuleDTO to save.
     * @param fieldAgentSkillRuleDTO the fieldAgentSkillRuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldAgentSkillRuleDTO,
     * or with status {@code 400 (Bad Request)} if the fieldAgentSkillRuleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldAgentSkillRuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FieldAgentSkillRuleDTO> updateFieldAgentSkillRule(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update FieldAgentSkillRule : {}, {}", id, fieldAgentSkillRuleDTO);
        if (fieldAgentSkillRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldAgentSkillRuleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldAgentSkillRuleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fieldAgentSkillRuleDTO = fieldAgentSkillRuleService.update(fieldAgentSkillRuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldAgentSkillRuleDTO.getId().toString()))
            .body(fieldAgentSkillRuleDTO);
    }

    /**
     * {@code PATCH  /field-agent-skill-rules/:id} : Partial updates given fields of an existing fieldAgentSkillRule, field will ignore if it is null
     *
     * @param id the id of the fieldAgentSkillRuleDTO to save.
     * @param fieldAgentSkillRuleDTO the fieldAgentSkillRuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldAgentSkillRuleDTO,
     * or with status {@code 400 (Bad Request)} if the fieldAgentSkillRuleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fieldAgentSkillRuleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldAgentSkillRuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FieldAgentSkillRuleDTO> partialUpdateFieldAgentSkillRule(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FieldAgentSkillRule partially : {}, {}", id, fieldAgentSkillRuleDTO);
        if (fieldAgentSkillRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldAgentSkillRuleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldAgentSkillRuleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FieldAgentSkillRuleDTO> result = fieldAgentSkillRuleService.partialUpdate(fieldAgentSkillRuleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldAgentSkillRuleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /field-agent-skill-rules} : get all the fieldAgentSkillRules.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldAgentSkillRules in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FieldAgentSkillRuleDTO>> getAllFieldAgentSkillRules(
        FieldAgentSkillRuleCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get FieldAgentSkillRules by criteria: {}", criteria);

        Page<FieldAgentSkillRuleDTO> page = fieldAgentSkillRuleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /field-agent-skill-rules/count} : count all the fieldAgentSkillRules.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFieldAgentSkillRules(FieldAgentSkillRuleCriteria criteria) {
        LOG.debug("REST request to count FieldAgentSkillRules by criteria: {}", criteria);
        return ResponseEntity.ok().body(fieldAgentSkillRuleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /field-agent-skill-rules/:id} : get the "id" fieldAgentSkillRule.
     *
     * @param id the id of the fieldAgentSkillRuleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldAgentSkillRuleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FieldAgentSkillRuleDTO> getFieldAgentSkillRule(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FieldAgentSkillRule : {}", id);
        Optional<FieldAgentSkillRuleDTO> fieldAgentSkillRuleDTO = fieldAgentSkillRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldAgentSkillRuleDTO);
    }

    /**
     * {@code DELETE  /field-agent-skill-rules/:id} : delete the "id" fieldAgentSkillRule.
     *
     * @param id the id of the fieldAgentSkillRuleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFieldAgentSkillRule(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FieldAgentSkillRule : {}", id);
        fieldAgentSkillRuleService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
