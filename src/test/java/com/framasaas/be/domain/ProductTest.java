package com.framasaas.be.domain;

import static com.framasaas.be.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.be.domain.ArticleTestSamples.*;
import static com.framasaas.be.domain.BrandTestSamples.*;
import static com.framasaas.be.domain.CategoryTestSamples.*;
import static com.framasaas.be.domain.HsnTestSamples.*;
import static com.framasaas.be.domain.InventoryTestSamples.*;
import static com.framasaas.be.domain.ProductPriceHistoryTestSamples.*;
import static com.framasaas.be.domain.ProductTestSamples.*;
import static com.framasaas.be.domain.ServiceOrderMasterTestSamples.*;
import static com.framasaas.be.domain.ServiceOrderSpareTestSamples.*;
import static com.framasaas.be.domain.WarrantyMasterTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = getProductSample1();
        Product product2 = new Product();
        assertThat(product1).isNotEqualTo(product2);

        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);

        product2 = getProductSample2();
        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    void productPriceHistoryTest() {
        Product product = getProductRandomSampleGenerator();
        ProductPriceHistory productPriceHistoryBack = getProductPriceHistoryRandomSampleGenerator();

        product.addProductPriceHistory(productPriceHistoryBack);
        assertThat(product.getProductPriceHistories()).containsOnly(productPriceHistoryBack);
        assertThat(productPriceHistoryBack.getProduct()).isEqualTo(product);

        product.removeProductPriceHistory(productPriceHistoryBack);
        assertThat(product.getProductPriceHistories()).doesNotContain(productPriceHistoryBack);
        assertThat(productPriceHistoryBack.getProduct()).isNull();

        product.productPriceHistories(new HashSet<>(Set.of(productPriceHistoryBack)));
        assertThat(product.getProductPriceHistories()).containsOnly(productPriceHistoryBack);
        assertThat(productPriceHistoryBack.getProduct()).isEqualTo(product);

        product.setProductPriceHistories(new HashSet<>());
        assertThat(product.getProductPriceHistories()).doesNotContain(productPriceHistoryBack);
        assertThat(productPriceHistoryBack.getProduct()).isNull();
    }

    @Test
    void warrantyMasterTest() {
        Product product = getProductRandomSampleGenerator();
        WarrantyMaster warrantyMasterBack = getWarrantyMasterRandomSampleGenerator();

        product.addWarrantyMaster(warrantyMasterBack);
        assertThat(product.getWarrantyMasters()).containsOnly(warrantyMasterBack);
        assertThat(warrantyMasterBack.getProduct()).isEqualTo(product);

        product.removeWarrantyMaster(warrantyMasterBack);
        assertThat(product.getWarrantyMasters()).doesNotContain(warrantyMasterBack);
        assertThat(warrantyMasterBack.getProduct()).isNull();

        product.warrantyMasters(new HashSet<>(Set.of(warrantyMasterBack)));
        assertThat(product.getWarrantyMasters()).containsOnly(warrantyMasterBack);
        assertThat(warrantyMasterBack.getProduct()).isEqualTo(product);

        product.setWarrantyMasters(new HashSet<>());
        assertThat(product.getWarrantyMasters()).doesNotContain(warrantyMasterBack);
        assertThat(warrantyMasterBack.getProduct()).isNull();
    }

    @Test
    void articleTest() {
        Product product = getProductRandomSampleGenerator();
        Article articleBack = getArticleRandomSampleGenerator();

        product.addArticle(articleBack);
        assertThat(product.getArticles()).containsOnly(articleBack);
        assertThat(articleBack.getProduct()).isEqualTo(product);

        product.removeArticle(articleBack);
        assertThat(product.getArticles()).doesNotContain(articleBack);
        assertThat(articleBack.getProduct()).isNull();

        product.articles(new HashSet<>(Set.of(articleBack)));
        assertThat(product.getArticles()).containsOnly(articleBack);
        assertThat(articleBack.getProduct()).isEqualTo(product);

        product.setArticles(new HashSet<>());
        assertThat(product.getArticles()).doesNotContain(articleBack);
        assertThat(articleBack.getProduct()).isNull();
    }

    @Test
    void serviceOrderMasterTest() {
        Product product = getProductRandomSampleGenerator();
        ServiceOrderMaster serviceOrderMasterBack = getServiceOrderMasterRandomSampleGenerator();

        product.addServiceOrderMaster(serviceOrderMasterBack);
        assertThat(product.getServiceOrderMasters()).containsOnly(serviceOrderMasterBack);
        assertThat(serviceOrderMasterBack.getProduct()).isEqualTo(product);

        product.removeServiceOrderMaster(serviceOrderMasterBack);
        assertThat(product.getServiceOrderMasters()).doesNotContain(serviceOrderMasterBack);
        assertThat(serviceOrderMasterBack.getProduct()).isNull();

        product.serviceOrderMasters(new HashSet<>(Set.of(serviceOrderMasterBack)));
        assertThat(product.getServiceOrderMasters()).containsOnly(serviceOrderMasterBack);
        assertThat(serviceOrderMasterBack.getProduct()).isEqualTo(product);

        product.setServiceOrderMasters(new HashSet<>());
        assertThat(product.getServiceOrderMasters()).doesNotContain(serviceOrderMasterBack);
        assertThat(serviceOrderMasterBack.getProduct()).isNull();
    }

    @Test
    void serviceOrderSpareTest() {
        Product product = getProductRandomSampleGenerator();
        ServiceOrderSpare serviceOrderSpareBack = getServiceOrderSpareRandomSampleGenerator();

        product.addServiceOrderSpare(serviceOrderSpareBack);
        assertThat(product.getServiceOrderSpares()).containsOnly(serviceOrderSpareBack);
        assertThat(serviceOrderSpareBack.getProduct()).isEqualTo(product);

        product.removeServiceOrderSpare(serviceOrderSpareBack);
        assertThat(product.getServiceOrderSpares()).doesNotContain(serviceOrderSpareBack);
        assertThat(serviceOrderSpareBack.getProduct()).isNull();

        product.serviceOrderSpares(new HashSet<>(Set.of(serviceOrderSpareBack)));
        assertThat(product.getServiceOrderSpares()).containsOnly(serviceOrderSpareBack);
        assertThat(serviceOrderSpareBack.getProduct()).isEqualTo(product);

        product.setServiceOrderSpares(new HashSet<>());
        assertThat(product.getServiceOrderSpares()).doesNotContain(serviceOrderSpareBack);
        assertThat(serviceOrderSpareBack.getProduct()).isNull();
    }

    @Test
    void inventoryTest() {
        Product product = getProductRandomSampleGenerator();
        Inventory inventoryBack = getInventoryRandomSampleGenerator();

        product.addInventory(inventoryBack);
        assertThat(product.getInventories()).containsOnly(inventoryBack);
        assertThat(inventoryBack.getProduct()).isEqualTo(product);

        product.removeInventory(inventoryBack);
        assertThat(product.getInventories()).doesNotContain(inventoryBack);
        assertThat(inventoryBack.getProduct()).isNull();

        product.inventories(new HashSet<>(Set.of(inventoryBack)));
        assertThat(product.getInventories()).containsOnly(inventoryBack);
        assertThat(inventoryBack.getProduct()).isEqualTo(product);

        product.setInventories(new HashSet<>());
        assertThat(product.getInventories()).doesNotContain(inventoryBack);
        assertThat(inventoryBack.getProduct()).isNull();
    }

    @Test
    void additionalAttributeTest() {
        Product product = getProductRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        product.addAdditionalAttribute(additionalAttributeBack);
        assertThat(product.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getProduct()).isEqualTo(product);

        product.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(product.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getProduct()).isNull();

        product.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(product.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getProduct()).isEqualTo(product);

        product.setAdditionalAttributes(new HashSet<>());
        assertThat(product.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getProduct()).isNull();
    }

    @Test
    void categoryTest() {
        Product product = getProductRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        product.setCategory(categoryBack);
        assertThat(product.getCategory()).isEqualTo(categoryBack);

        product.category(null);
        assertThat(product.getCategory()).isNull();
    }

    @Test
    void brandTest() {
        Product product = getProductRandomSampleGenerator();
        Brand brandBack = getBrandRandomSampleGenerator();

        product.setBrand(brandBack);
        assertThat(product.getBrand()).isEqualTo(brandBack);

        product.brand(null);
        assertThat(product.getBrand()).isNull();
    }

    @Test
    void hsnTest() {
        Product product = getProductRandomSampleGenerator();
        Hsn hsnBack = getHsnRandomSampleGenerator();

        product.setHsn(hsnBack);
        assertThat(product.getHsn()).isEqualTo(hsnBack);

        product.hsn(null);
        assertThat(product.getHsn()).isNull();
    }
}
