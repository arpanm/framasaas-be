package com.framasaas.web.rest;

import com.framasaas.repository.ProductPriceHistoryRepository;
import com.framasaas.service.ProductPriceHistoryQueryService;
import com.framasaas.service.ProductPriceHistoryService;
import com.framasaas.service.criteria.ProductPriceHistoryCriteria;
import com.framasaas.service.dto.ProductPriceHistoryDTO;
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
 * REST controller for managing {@link com.framasaas.domain.ProductPriceHistory}.
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

    private final ProductPriceHistoryQueryService productPriceHistoryQueryService;

    public ProductPriceHistoryResource(
        ProductPriceHistoryService productPriceHistoryService,
        ProductPriceHistoryRepository productPriceHistoryRepository,
        ProductPriceHistoryQueryService productPriceHistoryQueryService
    ) {
        this.productPriceHistoryService = productPriceHistoryService;
        this.productPriceHistoryRepository = productPriceHistoryRepository;
        this.productPriceHistoryQueryService = productPriceHistoryQueryService;
    }

    /**
     * {@code POST  /product-price-histories} : Create a new productPriceHistory.
     *
     * @param productPriceHistoryDTO the productPriceHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productPriceHistoryDTO, or with status {@code 400 (Bad Request)} if the productPriceHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProductPriceHistoryDTO> createProductPriceHistory(
        @Valid @RequestBody ProductPriceHistoryDTO productPriceHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save ProductPriceHistory : {}", productPriceHistoryDTO);
        if (productPriceHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new productPriceHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        productPriceHistoryDTO = productPriceHistoryService.save(productPriceHistoryDTO);
        return ResponseEntity.created(new URI("/api/product-price-histories/" + productPriceHistoryDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, productPriceHistoryDTO.getId().toString()))
            .body(productPriceHistoryDTO);
    }

    /**
     * {@code PUT  /product-price-histories/:id} : Updates an existing productPriceHistory.
     *
     * @param id the id of the productPriceHistoryDTO to save.
     * @param productPriceHistoryDTO the productPriceHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productPriceHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the productPriceHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productPriceHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductPriceHistoryDTO> updateProductPriceHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductPriceHistoryDTO productPriceHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ProductPriceHistory : {}, {}", id, productPriceHistoryDTO);
        if (productPriceHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productPriceHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productPriceHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        productPriceHistoryDTO = productPriceHistoryService.update(productPriceHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productPriceHistoryDTO.getId().toString()))
            .body(productPriceHistoryDTO);
    }

    /**
     * {@code PATCH  /product-price-histories/:id} : Partial updates given fields of an existing productPriceHistory, field will ignore if it is null
     *
     * @param id the id of the productPriceHistoryDTO to save.
     * @param productPriceHistoryDTO the productPriceHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productPriceHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the productPriceHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productPriceHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productPriceHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductPriceHistoryDTO> partialUpdateProductPriceHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductPriceHistoryDTO productPriceHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ProductPriceHistory partially : {}, {}", id, productPriceHistoryDTO);
        if (productPriceHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productPriceHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productPriceHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductPriceHistoryDTO> result = productPriceHistoryService.partialUpdate(productPriceHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productPriceHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-price-histories} : get all the productPriceHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productPriceHistories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProductPriceHistoryDTO>> getAllProductPriceHistories(
        ProductPriceHistoryCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get ProductPriceHistories by criteria: {}", criteria);

        Page<ProductPriceHistoryDTO> page = productPriceHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-price-histories/count} : count all the productPriceHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countProductPriceHistories(ProductPriceHistoryCriteria criteria) {
        LOG.debug("REST request to count ProductPriceHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(productPriceHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-price-histories/:id} : get the "id" productPriceHistory.
     *
     * @param id the id of the productPriceHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productPriceHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductPriceHistoryDTO> getProductPriceHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ProductPriceHistory : {}", id);
        Optional<ProductPriceHistoryDTO> productPriceHistoryDTO = productPriceHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productPriceHistoryDTO);
    }

    /**
     * {@code DELETE  /product-price-histories/:id} : delete the "id" productPriceHistory.
     *
     * @param id the id of the productPriceHistoryDTO to delete.
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
