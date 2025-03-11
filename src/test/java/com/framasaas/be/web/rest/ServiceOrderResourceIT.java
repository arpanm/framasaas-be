package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.ServiceOrderAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.ServiceOrder;
import com.framasaas.be.domain.enumeration.ServiceOrderStatus;
import com.framasaas.be.domain.enumeration.ServiceOrderType;
import com.framasaas.be.repository.ServiceOrderRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ServiceOrderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiceOrderResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ServiceOrderType DEFAULT_ORDER_TYPE = ServiceOrderType.REPAIR;
    private static final ServiceOrderType UPDATED_ORDER_TYPE = ServiceOrderType.PERIODICMAINTENANCE;

    private static final ServiceOrderStatus DEFAULT_ORDER_STATUS = ServiceOrderStatus.CREATED;
    private static final ServiceOrderStatus UPDATED_ORDER_STATUS = ServiceOrderStatus.ASSIGNED;

    private static final Boolean DEFAULT_IN_WARRANTY = false;
    private static final Boolean UPDATED_IN_WARRANTY = true;

    private static final Boolean DEFAULT_IS_FREE = false;
    private static final Boolean UPDATED_IS_FREE = true;

    private static final Boolean DEFAULT_IS_SPARE_NEEDED = false;
    private static final Boolean UPDATED_IS_SPARE_NEEDED = true;

    private static final Instant DEFAULT_CONFIRMED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CONFIRMED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CLOSED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CLOSED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_SERVICE_CHARGE = 1F;
    private static final Float UPDATED_SERVICE_CHARGE = 2F;

    private static final Float DEFAULT_TAX = 1F;
    private static final Float UPDATED_TAX = 2F;

    private static final Float DEFAULT_TOTAL_SPARE_CHARGES = 1F;
    private static final Float UPDATED_TOTAL_SPARE_CHARGES = 2F;

    private static final Float DEFAULT_TOTAL_SPARE_TAX = 1F;
    private static final Float UPDATED_TOTAL_SPARE_TAX = 2F;

    private static final Float DEFAULT_TOTAL_CHARGES = 1F;
    private static final Float UPDATED_TOTAL_CHARGES = 2F;

    private static final Float DEFAULT_TOTAL_PAYMENT_DONE = 1F;
    private static final Float UPDATED_TOTAL_PAYMENT_DONE = 2F;

    private static final String DEFAULT_CUSTOMER_INVOICE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_INVOICE_PATH = "BBBBBBBBBB";

    private static final Float DEFAULT_NPS = 1F;
    private static final Float UPDATED_NPS = 2F;

    private static final Long DEFAULT_PRIORITY = 1L;
    private static final Long UPDATED_PRIORITY = 2L;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/service-orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServiceOrderRepository serviceOrderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceOrderMockMvc;

    private ServiceOrder serviceOrder;

    private ServiceOrder insertedServiceOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceOrder createEntity() {
        return new ServiceOrder()
            .description(DEFAULT_DESCRIPTION)
            .orderType(DEFAULT_ORDER_TYPE)
            .orderStatus(DEFAULT_ORDER_STATUS)
            .inWarranty(DEFAULT_IN_WARRANTY)
            .isFree(DEFAULT_IS_FREE)
            .isSpareNeeded(DEFAULT_IS_SPARE_NEEDED)
            .confirmedTime(DEFAULT_CONFIRMED_TIME)
            .closedTime(DEFAULT_CLOSED_TIME)
            .serviceCharge(DEFAULT_SERVICE_CHARGE)
            .tax(DEFAULT_TAX)
            .totalSpareCharges(DEFAULT_TOTAL_SPARE_CHARGES)
            .totalSpareTax(DEFAULT_TOTAL_SPARE_TAX)
            .totalCharges(DEFAULT_TOTAL_CHARGES)
            .totalPaymentDone(DEFAULT_TOTAL_PAYMENT_DONE)
            .customerInvoicePath(DEFAULT_CUSTOMER_INVOICE_PATH)
            .nps(DEFAULT_NPS)
            .priority(DEFAULT_PRIORITY)
            .isActive(DEFAULT_IS_ACTIVE)
            .createddBy(DEFAULT_CREATEDD_BY)
            .createdTime(DEFAULT_CREATED_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedTime(DEFAULT_UPDATED_TIME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceOrder createUpdatedEntity() {
        return new ServiceOrder()
            .description(UPDATED_DESCRIPTION)
            .orderType(UPDATED_ORDER_TYPE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .inWarranty(UPDATED_IN_WARRANTY)
            .isFree(UPDATED_IS_FREE)
            .isSpareNeeded(UPDATED_IS_SPARE_NEEDED)
            .confirmedTime(UPDATED_CONFIRMED_TIME)
            .closedTime(UPDATED_CLOSED_TIME)
            .serviceCharge(UPDATED_SERVICE_CHARGE)
            .tax(UPDATED_TAX)
            .totalSpareCharges(UPDATED_TOTAL_SPARE_CHARGES)
            .totalSpareTax(UPDATED_TOTAL_SPARE_TAX)
            .totalCharges(UPDATED_TOTAL_CHARGES)
            .totalPaymentDone(UPDATED_TOTAL_PAYMENT_DONE)
            .customerInvoicePath(UPDATED_CUSTOMER_INVOICE_PATH)
            .nps(UPDATED_NPS)
            .priority(UPDATED_PRIORITY)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        serviceOrder = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedServiceOrder != null) {
            serviceOrderRepository.delete(insertedServiceOrder);
            insertedServiceOrder = null;
        }
    }

    @Test
    @Transactional
    void createServiceOrder() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ServiceOrder
        var returnedServiceOrder = om.readValue(
            restServiceOrderMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrder)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServiceOrder.class
        );

        // Validate the ServiceOrder in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertServiceOrderUpdatableFieldsEquals(returnedServiceOrder, getPersistedServiceOrder(returnedServiceOrder));

        insertedServiceOrder = returnedServiceOrder;
    }

    @Test
    @Transactional
    void createServiceOrderWithExistingId() throws Exception {
        // Create the ServiceOrder with an existing ID
        serviceOrder.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrder)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrder.setCreateddBy(null);

        // Create the ServiceOrder, which fails.

        restServiceOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrder)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrder.setCreatedTime(null);

        // Create the ServiceOrder, which fails.

        restServiceOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrder)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrder.setUpdatedBy(null);

        // Create the ServiceOrder, which fails.

        restServiceOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrder)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrder.setUpdatedTime(null);

        // Create the ServiceOrder, which fails.

        restServiceOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrder)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllServiceOrders() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList
        restServiceOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].orderType").value(hasItem(DEFAULT_ORDER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS.toString())))
            .andExpect(jsonPath("$.[*].inWarranty").value(hasItem(DEFAULT_IN_WARRANTY)))
            .andExpect(jsonPath("$.[*].isFree").value(hasItem(DEFAULT_IS_FREE)))
            .andExpect(jsonPath("$.[*].isSpareNeeded").value(hasItem(DEFAULT_IS_SPARE_NEEDED)))
            .andExpect(jsonPath("$.[*].confirmedTime").value(hasItem(DEFAULT_CONFIRMED_TIME.toString())))
            .andExpect(jsonPath("$.[*].closedTime").value(hasItem(DEFAULT_CLOSED_TIME.toString())))
            .andExpect(jsonPath("$.[*].serviceCharge").value(hasItem(DEFAULT_SERVICE_CHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].totalSpareCharges").value(hasItem(DEFAULT_TOTAL_SPARE_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].totalSpareTax").value(hasItem(DEFAULT_TOTAL_SPARE_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].totalCharges").value(hasItem(DEFAULT_TOTAL_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].totalPaymentDone").value(hasItem(DEFAULT_TOTAL_PAYMENT_DONE.doubleValue())))
            .andExpect(jsonPath("$.[*].customerInvoicePath").value(hasItem(DEFAULT_CUSTOMER_INVOICE_PATH)))
            .andExpect(jsonPath("$.[*].nps").value(hasItem(DEFAULT_NPS.doubleValue())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getServiceOrder() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get the serviceOrder
        restServiceOrderMockMvc
            .perform(get(ENTITY_API_URL_ID, serviceOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceOrder.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.orderType").value(DEFAULT_ORDER_TYPE.toString()))
            .andExpect(jsonPath("$.orderStatus").value(DEFAULT_ORDER_STATUS.toString()))
            .andExpect(jsonPath("$.inWarranty").value(DEFAULT_IN_WARRANTY))
            .andExpect(jsonPath("$.isFree").value(DEFAULT_IS_FREE))
            .andExpect(jsonPath("$.isSpareNeeded").value(DEFAULT_IS_SPARE_NEEDED))
            .andExpect(jsonPath("$.confirmedTime").value(DEFAULT_CONFIRMED_TIME.toString()))
            .andExpect(jsonPath("$.closedTime").value(DEFAULT_CLOSED_TIME.toString()))
            .andExpect(jsonPath("$.serviceCharge").value(DEFAULT_SERVICE_CHARGE.doubleValue()))
            .andExpect(jsonPath("$.tax").value(DEFAULT_TAX.doubleValue()))
            .andExpect(jsonPath("$.totalSpareCharges").value(DEFAULT_TOTAL_SPARE_CHARGES.doubleValue()))
            .andExpect(jsonPath("$.totalSpareTax").value(DEFAULT_TOTAL_SPARE_TAX.doubleValue()))
            .andExpect(jsonPath("$.totalCharges").value(DEFAULT_TOTAL_CHARGES.doubleValue()))
            .andExpect(jsonPath("$.totalPaymentDone").value(DEFAULT_TOTAL_PAYMENT_DONE.doubleValue()))
            .andExpect(jsonPath("$.customerInvoicePath").value(DEFAULT_CUSTOMER_INVOICE_PATH))
            .andExpect(jsonPath("$.nps").value(DEFAULT_NPS.doubleValue()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.intValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingServiceOrder() throws Exception {
        // Get the serviceOrder
        restServiceOrderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServiceOrder() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrder
        ServiceOrder updatedServiceOrder = serviceOrderRepository.findById(serviceOrder.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedServiceOrder are not directly saved in db
        em.detach(updatedServiceOrder);
        updatedServiceOrder
            .description(UPDATED_DESCRIPTION)
            .orderType(UPDATED_ORDER_TYPE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .inWarranty(UPDATED_IN_WARRANTY)
            .isFree(UPDATED_IS_FREE)
            .isSpareNeeded(UPDATED_IS_SPARE_NEEDED)
            .confirmedTime(UPDATED_CONFIRMED_TIME)
            .closedTime(UPDATED_CLOSED_TIME)
            .serviceCharge(UPDATED_SERVICE_CHARGE)
            .tax(UPDATED_TAX)
            .totalSpareCharges(UPDATED_TOTAL_SPARE_CHARGES)
            .totalSpareTax(UPDATED_TOTAL_SPARE_TAX)
            .totalCharges(UPDATED_TOTAL_CHARGES)
            .totalPaymentDone(UPDATED_TOTAL_PAYMENT_DONE)
            .customerInvoicePath(UPDATED_CUSTOMER_INVOICE_PATH)
            .nps(UPDATED_NPS)
            .priority(UPDATED_PRIORITY)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restServiceOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServiceOrder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedServiceOrder))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServiceOrderToMatchAllProperties(updatedServiceOrder);
    }

    @Test
    @Transactional
    void putNonExistingServiceOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrder.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceOrder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServiceOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServiceOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiceOrderWithPatch() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrder using partial update
        ServiceOrder partialUpdatedServiceOrder = new ServiceOrder();
        partialUpdatedServiceOrder.setId(serviceOrder.getId());

        partialUpdatedServiceOrder
            .description(UPDATED_DESCRIPTION)
            .orderType(UPDATED_ORDER_TYPE)
            .inWarranty(UPDATED_IN_WARRANTY)
            .isFree(UPDATED_IS_FREE)
            .isSpareNeeded(UPDATED_IS_SPARE_NEEDED)
            .closedTime(UPDATED_CLOSED_TIME)
            .totalSpareTax(UPDATED_TOTAL_SPARE_TAX)
            .totalCharges(UPDATED_TOTAL_CHARGES)
            .totalPaymentDone(UPDATED_TOTAL_PAYMENT_DONE)
            .customerInvoicePath(UPDATED_CUSTOMER_INVOICE_PATH)
            .createddBy(UPDATED_CREATEDD_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restServiceOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceOrder))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrder in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceOrderUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServiceOrder, serviceOrder),
            getPersistedServiceOrder(serviceOrder)
        );
    }

    @Test
    @Transactional
    void fullUpdateServiceOrderWithPatch() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrder using partial update
        ServiceOrder partialUpdatedServiceOrder = new ServiceOrder();
        partialUpdatedServiceOrder.setId(serviceOrder.getId());

        partialUpdatedServiceOrder
            .description(UPDATED_DESCRIPTION)
            .orderType(UPDATED_ORDER_TYPE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .inWarranty(UPDATED_IN_WARRANTY)
            .isFree(UPDATED_IS_FREE)
            .isSpareNeeded(UPDATED_IS_SPARE_NEEDED)
            .confirmedTime(UPDATED_CONFIRMED_TIME)
            .closedTime(UPDATED_CLOSED_TIME)
            .serviceCharge(UPDATED_SERVICE_CHARGE)
            .tax(UPDATED_TAX)
            .totalSpareCharges(UPDATED_TOTAL_SPARE_CHARGES)
            .totalSpareTax(UPDATED_TOTAL_SPARE_TAX)
            .totalCharges(UPDATED_TOTAL_CHARGES)
            .totalPaymentDone(UPDATED_TOTAL_PAYMENT_DONE)
            .customerInvoicePath(UPDATED_CUSTOMER_INVOICE_PATH)
            .nps(UPDATED_NPS)
            .priority(UPDATED_PRIORITY)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restServiceOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceOrder))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrder in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceOrderUpdatableFieldsEquals(partialUpdatedServiceOrder, getPersistedServiceOrder(partialUpdatedServiceOrder));
    }

    @Test
    @Transactional
    void patchNonExistingServiceOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrder.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServiceOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServiceOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(serviceOrder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServiceOrder() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the serviceOrder
        restServiceOrderMockMvc
            .perform(delete(ENTITY_API_URL_ID, serviceOrder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return serviceOrderRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected ServiceOrder getPersistedServiceOrder(ServiceOrder serviceOrder) {
        return serviceOrderRepository.findById(serviceOrder.getId()).orElseThrow();
    }

    protected void assertPersistedServiceOrderToMatchAllProperties(ServiceOrder expectedServiceOrder) {
        assertServiceOrderAllPropertiesEquals(expectedServiceOrder, getPersistedServiceOrder(expectedServiceOrder));
    }

    protected void assertPersistedServiceOrderToMatchUpdatableProperties(ServiceOrder expectedServiceOrder) {
        assertServiceOrderAllUpdatablePropertiesEquals(expectedServiceOrder, getPersistedServiceOrder(expectedServiceOrder));
    }
}
