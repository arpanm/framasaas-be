package com.framasaas.be.web.rest;

import com.framasaas.be.domain.FieldAgentSkillRuleSet;
import com.framasaas.be.repository.FieldAgentSkillRuleSetRepository;
import com.framasaas.be.service.FieldAgentSkillRuleSetService;
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
 * REST controller for managing {@link com.framasaas.be.domain.FieldAgentSkillRuleSet}.
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

    public FieldAgentSkillRuleSetResource(
        FieldAgentSkillRuleSetService fieldAgentSkillRuleSetService,
        FieldAgentSkillRuleSetRepository fieldAgentSkillRuleSetRepository
    ) {
        this.fieldAgentSkillRuleSetService = fieldAgentSkillRuleSetService;
        this.fieldAgentSkillRuleSetRepository = fieldAgentSkillRuleSetRepository;
    }

    /**
     * {@code POST  /field-agent-skill-rule-sets} : Create a new fieldAgentSkillRuleSet.
     *
     * @param fieldAgentSkillRuleSet the fieldAgentSkillRuleSet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldAgentSkillRuleSet, or with status {@code 400 (Bad Request)} if the fieldAgentSkillRuleSet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FieldAgentSkillRuleSet> createFieldAgentSkillRuleSet(
        @Valid @RequestBody FieldAgentSkillRuleSet fieldAgentSkillRuleSet
    ) throws URISyntaxException {
        LOG.debug("REST request to save FieldAgentSkillRuleSet : {}", fieldAgentSkillRuleSet);
        if (fieldAgentSkillRuleSet.getId() != null) {
            throw new BadRequestAlertException("A new fieldAgentSkillRuleSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fieldAgentSkillRuleSet = fieldAgentSkillRuleSetService.save(fieldAgentSkillRuleSet);
        return ResponseEntity.created(new URI("/api/field-agent-skill-rule-sets/" + fieldAgentSkillRuleSet.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, fieldAgentSkillRuleSet.getId().toString()))
            .body(fieldAgentSkillRuleSet);
    }

    /**
     * {@code PUT  /field-agent-skill-rule-sets/:id} : Updates an existing fieldAgentSkillRuleSet.
     *
     * @param id the id of the fieldAgentSkillRuleSet to save.
     * @param fieldAgentSkillRuleSet the fieldAgentSkillRuleSet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldAgentSkillRuleSet,
     * or with status {@code 400 (Bad Request)} if the fieldAgentSkillRuleSet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldAgentSkillRuleSet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FieldAgentSkillRuleSet> updateFieldAgentSkillRuleSet(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FieldAgentSkillRuleSet fieldAgentSkillRuleSet
    ) throws URISyntaxException {
        LOG.debug("REST request to update FieldAgentSkillRuleSet : {}, {}", id, fieldAgentSkillRuleSet);
        if (fieldAgentSkillRuleSet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldAgentSkillRuleSet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldAgentSkillRuleSetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fieldAgentSkillRuleSet = fieldAgentSkillRuleSetService.update(fieldAgentSkillRuleSet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldAgentSkillRuleSet.getId().toString()))
            .body(fieldAgentSkillRuleSet);
    }

    /**
     * {@code PATCH  /field-agent-skill-rule-sets/:id} : Partial updates given fields of an existing fieldAgentSkillRuleSet, field will ignore if it is null
     *
     * @param id the id of the fieldAgentSkillRuleSet to save.
     * @param fieldAgentSkillRuleSet the fieldAgentSkillRuleSet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldAgentSkillRuleSet,
     * or with status {@code 400 (Bad Request)} if the fieldAgentSkillRuleSet is not valid,
     * or with status {@code 404 (Not Found)} if the fieldAgentSkillRuleSet is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldAgentSkillRuleSet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FieldAgentSkillRuleSet> partialUpdateFieldAgentSkillRuleSet(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FieldAgentSkillRuleSet fieldAgentSkillRuleSet
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FieldAgentSkillRuleSet partially : {}, {}", id, fieldAgentSkillRuleSet);
        if (fieldAgentSkillRuleSet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldAgentSkillRuleSet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldAgentSkillRuleSetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FieldAgentSkillRuleSet> result = fieldAgentSkillRuleSetService.partialUpdate(fieldAgentSkillRuleSet);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldAgentSkillRuleSet.getId().toString())
        );
    }

    /**
     * {@code GET  /field-agent-skill-rule-sets} : get all the fieldAgentSkillRuleSets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldAgentSkillRuleSets in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FieldAgentSkillRuleSet>> getAllFieldAgentSkillRuleSets(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of FieldAgentSkillRuleSets");
        Page<FieldAgentSkillRuleSet> page = fieldAgentSkillRuleSetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /field-agent-skill-rule-sets/:id} : get the "id" fieldAgentSkillRuleSet.
     *
     * @param id the id of the fieldAgentSkillRuleSet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldAgentSkillRuleSet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FieldAgentSkillRuleSet> getFieldAgentSkillRuleSet(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FieldAgentSkillRuleSet : {}", id);
        Optional<FieldAgentSkillRuleSet> fieldAgentSkillRuleSet = fieldAgentSkillRuleSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldAgentSkillRuleSet);
    }

    /**
     * {@code DELETE  /field-agent-skill-rule-sets/:id} : delete the "id" fieldAgentSkillRuleSet.
     *
     * @param id the id of the fieldAgentSkillRuleSet to delete.
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
