package com.framasaas.be.web.rest;

import com.framasaas.be.domain.FieldAgentSkillRule;
import com.framasaas.be.repository.FieldAgentSkillRuleRepository;
import com.framasaas.be.service.FieldAgentSkillRuleService;
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
 * REST controller for managing {@link com.framasaas.be.domain.FieldAgentSkillRule}.
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

    public FieldAgentSkillRuleResource(
        FieldAgentSkillRuleService fieldAgentSkillRuleService,
        FieldAgentSkillRuleRepository fieldAgentSkillRuleRepository
    ) {
        this.fieldAgentSkillRuleService = fieldAgentSkillRuleService;
        this.fieldAgentSkillRuleRepository = fieldAgentSkillRuleRepository;
    }

    /**
     * {@code POST  /field-agent-skill-rules} : Create a new fieldAgentSkillRule.
     *
     * @param fieldAgentSkillRule the fieldAgentSkillRule to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldAgentSkillRule, or with status {@code 400 (Bad Request)} if the fieldAgentSkillRule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FieldAgentSkillRule> createFieldAgentSkillRule(@Valid @RequestBody FieldAgentSkillRule fieldAgentSkillRule)
        throws URISyntaxException {
        LOG.debug("REST request to save FieldAgentSkillRule : {}", fieldAgentSkillRule);
        if (fieldAgentSkillRule.getId() != null) {
            throw new BadRequestAlertException("A new fieldAgentSkillRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fieldAgentSkillRule = fieldAgentSkillRuleService.save(fieldAgentSkillRule);
        return ResponseEntity.created(new URI("/api/field-agent-skill-rules/" + fieldAgentSkillRule.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, fieldAgentSkillRule.getId().toString()))
            .body(fieldAgentSkillRule);
    }

    /**
     * {@code PUT  /field-agent-skill-rules/:id} : Updates an existing fieldAgentSkillRule.
     *
     * @param id the id of the fieldAgentSkillRule to save.
     * @param fieldAgentSkillRule the fieldAgentSkillRule to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldAgentSkillRule,
     * or with status {@code 400 (Bad Request)} if the fieldAgentSkillRule is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldAgentSkillRule couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FieldAgentSkillRule> updateFieldAgentSkillRule(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FieldAgentSkillRule fieldAgentSkillRule
    ) throws URISyntaxException {
        LOG.debug("REST request to update FieldAgentSkillRule : {}, {}", id, fieldAgentSkillRule);
        if (fieldAgentSkillRule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldAgentSkillRule.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldAgentSkillRuleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fieldAgentSkillRule = fieldAgentSkillRuleService.update(fieldAgentSkillRule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldAgentSkillRule.getId().toString()))
            .body(fieldAgentSkillRule);
    }

    /**
     * {@code PATCH  /field-agent-skill-rules/:id} : Partial updates given fields of an existing fieldAgentSkillRule, field will ignore if it is null
     *
     * @param id the id of the fieldAgentSkillRule to save.
     * @param fieldAgentSkillRule the fieldAgentSkillRule to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldAgentSkillRule,
     * or with status {@code 400 (Bad Request)} if the fieldAgentSkillRule is not valid,
     * or with status {@code 404 (Not Found)} if the fieldAgentSkillRule is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldAgentSkillRule couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FieldAgentSkillRule> partialUpdateFieldAgentSkillRule(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FieldAgentSkillRule fieldAgentSkillRule
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FieldAgentSkillRule partially : {}, {}", id, fieldAgentSkillRule);
        if (fieldAgentSkillRule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldAgentSkillRule.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldAgentSkillRuleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FieldAgentSkillRule> result = fieldAgentSkillRuleService.partialUpdate(fieldAgentSkillRule);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldAgentSkillRule.getId().toString())
        );
    }

    /**
     * {@code GET  /field-agent-skill-rules} : get all the fieldAgentSkillRules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldAgentSkillRules in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FieldAgentSkillRule>> getAllFieldAgentSkillRules(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of FieldAgentSkillRules");
        Page<FieldAgentSkillRule> page = fieldAgentSkillRuleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /field-agent-skill-rules/:id} : get the "id" fieldAgentSkillRule.
     *
     * @param id the id of the fieldAgentSkillRule to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldAgentSkillRule, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FieldAgentSkillRule> getFieldAgentSkillRule(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FieldAgentSkillRule : {}", id);
        Optional<FieldAgentSkillRule> fieldAgentSkillRule = fieldAgentSkillRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldAgentSkillRule);
    }

    /**
     * {@code DELETE  /field-agent-skill-rules/:id} : delete the "id" fieldAgentSkillRule.
     *
     * @param id the id of the fieldAgentSkillRule to delete.
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
