package com.framasaas.be.web.rest;

import com.framasaas.be.domain.ProductPriceHistory;
import com.framasaas.be.repository.ProductPriceHistoryRepository;
import com.framasaas.be.service.ProductPriceHistoryService;
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
 * REST controller for managing {@link com.framasaas.be.domain.ProductPriceHistory}.
 */
@RestController
@RequestMapping("/api/product-price-histories")
public class ProductPriceHistoryResource {

    private static final Logger LOG = LoggerFactory.getLogger(ProductPriceHistoryResource.class);

    private static final String ENTITY_NAME = "productPriceHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductPriceHistoryService productPriceHistoryService;

    private final ProductPriceHistoryRepository productPriceHistoryRepository;

    public ProductPriceHistoryResource(
        ProductPriceHistoryService productPriceHistoryService,
        ProductPriceHistoryRepository productPriceHistoryRepository
    ) {
        this.productPriceHistoryService = productPriceHistoryService;
        this.productPriceHistoryRepository = productPriceHistoryRepository;
    }

    /**
     * {@code POST  /product-price-histories} : Create a new productPriceHistory.
     *
     * @param productPriceHistory the productPriceHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productPriceHistory, or with status {@code 400 (Bad Request)} if the productPriceHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProductPriceHistory> createProductPriceHistory(@Valid @RequestBody ProductPriceHistory productPriceHistory)
        throws URISyntaxException {
        LOG.debug("REST request to save ProductPriceHistory : {}", productPriceHistory);
        if (productPriceHistory.getId() != null) {
            throw new BadRequestAlertException("A new productPriceHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        productPriceHistory = productPriceHistoryService.save(productPriceHistory);
        return ResponseEntity.created(new URI("/api/product-price-histories/" + productPriceHistory.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, productPriceHistory.getId().toString()))
            .body(productPriceHistory);
    }

    /**
     * {@code PUT  /product-price-histories/:id} : Updates an existing productPriceHistory.
     *
     * @param id the id of the productPriceHistory to save.
     * @param productPriceHistory the productPriceHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productPriceHistory,
     * or with status {@code 400 (Bad Request)} if the productPriceHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productPriceHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductPriceHistory> updateProductPriceHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductPriceHistory productPriceHistory
    ) throws URISyntaxException {
        LOG.debug("REST request to update ProductPriceHistory : {}, {}", id, productPriceHistory);
        if (productPriceHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productPriceHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productPriceHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        productPriceHistory = productPriceHistoryService.update(productPriceHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productPriceHistory.getId().toString()))
            .body(productPriceHistory);
    }

    /**
     * {@code PATCH  /product-price-histories/:id} : Partial updates given fields of an existing productPriceHistory, field will ignore if it is null
     *
     * @param id the id of the productPriceHistory to save.
     * @param productPriceHistory the productPriceHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productPriceHistory,
     * or with status {@code 400 (Bad Request)} if the productPriceHistory is not valid,
     * or with status {@code 404 (Not Found)} if the productPriceHistory is not found,
     * or with status {@code 500 (Internal Server Error)} if the productPriceHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductPriceHistory> partialUpdateProductPriceHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductPriceHistory productPriceHistory
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ProductPriceHistory partially : {}, {}", id, productPriceHistory);
        if (productPriceHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productPriceHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productPriceHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductPriceHistory> result = productPriceHistoryService.partialUpdate(productPriceHistory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productPriceHistory.getId().toString())
        );
    }

    /**
     * {@code GET  /product-price-histories} : get all the productPriceHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productPriceHistories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProductPriceHistory>> getAllProductPriceHistories(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of ProductPriceHistories");
        Page<ProductPriceHistory> page = productPriceHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-price-histories/:id} : get the "id" productPriceHistory.
     *
     * @param id the id of the productPriceHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productPriceHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductPriceHistory> getProductPriceHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ProductPriceHistory : {}", id);
        Optional<ProductPriceHistory> productPriceHistory = productPriceHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productPriceHistory);
    }

    /**
     * {@code DELETE  /product-price-histories/:id} : delete the "id" productPriceHistory.
     *
     * @param id the id of the productPriceHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductPriceHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ProductPriceHistory : {}", id);
        productPriceHistoryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
