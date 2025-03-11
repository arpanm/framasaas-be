package com.framasaas.be.web.rest;

import com.framasaas.be.domain.LanguageMapping;
import com.framasaas.be.repository.LanguageMappingRepository;
import com.framasaas.be.service.LanguageMappingService;
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
 * REST controller for managing {@link com.framasaas.be.domain.LanguageMapping}.
 */
@RestController
@RequestMapping("/api/language-mappings")
public class LanguageMappingResource {

    private static final Logger LOG = LoggerFactory.getLogger(LanguageMappingResource.class);

    private static final String ENTITY_NAME = "languageMapping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LanguageMappingService languageMappingService;

    private final LanguageMappingRepository languageMappingRepository;

    public LanguageMappingResource(LanguageMappingService languageMappingService, LanguageMappingRepository languageMappingRepository) {
        this.languageMappingService = languageMappingService;
        this.languageMappingRepository = languageMappingRepository;
    }

    /**
     * {@code POST  /language-mappings} : Create a new languageMapping.
     *
     * @param languageMapping the languageMapping to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new languageMapping, or with status {@code 400 (Bad Request)} if the languageMapping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LanguageMapping> createLanguageMapping(@Valid @RequestBody LanguageMapping languageMapping)
        throws URISyntaxException {
        LOG.debug("REST request to save LanguageMapping : {}", languageMapping);
        if (languageMapping.getId() != null) {
            throw new BadRequestAlertException("A new languageMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        languageMapping = languageMappingService.save(languageMapping);
        return ResponseEntity.created(new URI("/api/language-mappings/" + languageMapping.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, languageMapping.getId().toString()))
            .body(languageMapping);
    }

    /**
     * {@code PUT  /language-mappings/:id} : Updates an existing languageMapping.
     *
     * @param id the id of the languageMapping to save.
     * @param languageMapping the languageMapping to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated languageMapping,
     * or with status {@code 400 (Bad Request)} if the languageMapping is not valid,
     * or with status {@code 500 (Internal Server Error)} if the languageMapping couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LanguageMapping> updateLanguageMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LanguageMapping languageMapping
    ) throws URISyntaxException {
        LOG.debug("REST request to update LanguageMapping : {}, {}", id, languageMapping);
        if (languageMapping.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, languageMapping.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!languageMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        languageMapping = languageMappingService.update(languageMapping);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, languageMapping.getId().toString()))
            .body(languageMapping);
    }

    /**
     * {@code PATCH  /language-mappings/:id} : Partial updates given fields of an existing languageMapping, field will ignore if it is null
     *
     * @param id the id of the languageMapping to save.
     * @param languageMapping the languageMapping to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated languageMapping,
     * or with status {@code 400 (Bad Request)} if the languageMapping is not valid,
     * or with status {@code 404 (Not Found)} if the languageMapping is not found,
     * or with status {@code 500 (Internal Server Error)} if the languageMapping couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LanguageMapping> partialUpdateLanguageMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LanguageMapping languageMapping
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update LanguageMapping partially : {}, {}", id, languageMapping);
        if (languageMapping.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, languageMapping.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!languageMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LanguageMapping> result = languageMappingService.partialUpdate(languageMapping);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, languageMapping.getId().toString())
        );
    }

    /**
     * {@code GET  /language-mappings} : get all the languageMappings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of languageMappings in body.
     */
    @GetMapping("")
    public ResponseEntity<List<LanguageMapping>> getAllLanguageMappings(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of LanguageMappings");
        Page<LanguageMapping> page = languageMappingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /language-mappings/:id} : get the "id" languageMapping.
     *
     * @param id the id of the languageMapping to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the languageMapping, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LanguageMapping> getLanguageMapping(@PathVariable("id") Long id) {
        LOG.debug("REST request to get LanguageMapping : {}", id);
        Optional<LanguageMapping> languageMapping = languageMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(languageMapping);
    }

    /**
     * {@code DELETE  /language-mappings/:id} : delete the "id" languageMapping.
     *
     * @param id the id of the languageMapping to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLanguageMapping(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete LanguageMapping : {}", id);
        languageMappingService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
