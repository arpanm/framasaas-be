package com.framasaas.domain;

import static com.framasaas.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.domain.AddressTestSamples.*;
import static com.framasaas.domain.ArticleTestSamples.*;
import static com.framasaas.domain.CustomerTestSamples.*;
import static com.framasaas.domain.ServiceOrderFranchiseAssignmentTestSamples.*;
import static com.framasaas.domain.ServiceOrderMasterTestSamples.*;
import static com.framasaas.domain.ServiceOrderSpareTestSamples.*;
import static com.framasaas.domain.ServiceOrderTestSamples.*;
import static com.framasaas.domain.SupportingDocumentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ServiceOrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOrder.class);
        ServiceOrder serviceOrder1 = getServiceOrderSample1();
        ServiceOrder serviceOrder2 = new ServiceOrder();
        assertThat(serviceOrder1).isNotEqualTo(serviceOrder2);

        serviceOrder2.setId(serviceOrder1.getId());
        assertThat(serviceOrder1).isEqualTo(serviceOrder2);

        serviceOrder2 = getServiceOrderSample2();
        assertThat(serviceOrder1).isNotEqualTo(serviceOrder2);
    }

    @Test
    void supportingDocumentTest() {
        ServiceOrder serviceOrder = getServiceOrderRandomSampleGenerator();
        SupportingDocument supportingDocumentBack = getSupportingDocumentRandomSampleGenerator();

        serviceOrder.addSupportingDocument(supportingDocumentBack);
        assertThat(serviceOrder.getSupportingDocuments()).containsOnly(supportingDocumentBack);
        assertThat(supportingDocumentBack.getServiceOrder()).isEqualTo(serviceOrder);

        serviceOrder.removeSupportingDocument(supportingDocumentBack);
        assertThat(serviceOrder.getSupportingDocuments()).doesNotContain(supportingDocumentBack);
        assertThat(supportingDocumentBack.getServiceOrder()).isNull();

        serviceOrder.supportingDocuments(new HashSet<>(Set.of(supportingDocumentBack)));
        assertThat(serviceOrder.getSupportingDocuments()).containsOnly(supportingDocumentBack);
        assertThat(supportingDocumentBack.getServiceOrder()).isEqualTo(serviceOrder);

        serviceOrder.setSupportingDocuments(new HashSet<>());
        assertThat(serviceOrder.getSupportingDocuments()).doesNotContain(supportingDocumentBack);
        assertThat(supportingDocumentBack.getServiceOrder()).isNull();
    }

    @Test
    void serviceOrderFranchiseAssignmentTest() {
        ServiceOrder serviceOrder = getServiceOrderRandomSampleGenerator();
        ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignmentBack = getServiceOrderFranchiseAssignmentRandomSampleGenerator();

        serviceOrder.addServiceOrderFranchiseAssignment(serviceOrderFranchiseAssignmentBack);
        assertThat(serviceOrder.getServiceOrderFranchiseAssignments()).containsOnly(serviceOrderFranchiseAssignmentBack);
        assertThat(serviceOrderFranchiseAssignmentBack.getServiceOrder()).isEqualTo(serviceOrder);

        serviceOrder.removeServiceOrderFranchiseAssignment(serviceOrderFranchiseAssignmentBack);
        assertThat(serviceOrder.getServiceOrderFranchiseAssignments()).doesNotContain(serviceOrderFranchiseAssignmentBack);
        assertThat(serviceOrderFranchiseAssignmentBack.getServiceOrder()).isNull();

        serviceOrder.serviceOrderFranchiseAssignments(new HashSet<>(Set.of(serviceOrderFranchiseAssignmentBack)));
        assertThat(serviceOrder.getServiceOrderFranchiseAssignments()).containsOnly(serviceOrderFranchiseAssignmentBack);
        assertThat(serviceOrderFranchiseAssignmentBack.getServiceOrder()).isEqualTo(serviceOrder);

        serviceOrder.setServiceOrderFranchiseAssignments(new HashSet<>());
        assertThat(serviceOrder.getServiceOrderFranchiseAssignments()).doesNotContain(serviceOrderFranchiseAssignmentBack);
        assertThat(serviceOrderFranchiseAssignmentBack.getServiceOrder()).isNull();
    }

    @Test
    void serviceOrderSpareTest() {
        ServiceOrder serviceOrder = getServiceOrderRandomSampleGenerator();
        ServiceOrderSpare serviceOrderSpareBack = getServiceOrderSpareRandomSampleGenerator();

        serviceOrder.addServiceOrderSpare(serviceOrderSpareBack);
        assertThat(serviceOrder.getServiceOrderSpares()).containsOnly(serviceOrderSpareBack);
        assertThat(serviceOrderSpareBack.getServiceOrder()).isEqualTo(serviceOrder);

        serviceOrder.removeServiceOrderSpare(serviceOrderSpareBack);
        assertThat(serviceOrder.getServiceOrderSpares()).doesNotContain(serviceOrderSpareBack);
        assertThat(serviceOrderSpareBack.getServiceOrder()).isNull();

        serviceOrder.serviceOrderSpares(new HashSet<>(Set.of(serviceOrderSpareBack)));
        assertThat(serviceOrder.getServiceOrderSpares()).containsOnly(serviceOrderSpareBack);
        assertThat(serviceOrderSpareBack.getServiceOrder()).isEqualTo(serviceOrder);

        serviceOrder.setServiceOrderSpares(new HashSet<>());
        assertThat(serviceOrder.getServiceOrderSpares()).doesNotContain(serviceOrderSpareBack);
        assertThat(serviceOrderSpareBack.getServiceOrder()).isNull();
    }

    @Test
    void additionalAttributeTest() {
        ServiceOrder serviceOrder = getServiceOrderRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        serviceOrder.addAdditionalAttribute(additionalAttributeBack);
        assertThat(serviceOrder.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrder()).isEqualTo(serviceOrder);

        serviceOrder.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(serviceOrder.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrder()).isNull();

        serviceOrder.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(serviceOrder.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrder()).isEqualTo(serviceOrder);

        serviceOrder.setAdditionalAttributes(new HashSet<>());
        assertThat(serviceOrder.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getServiceOrder()).isNull();
    }

    @Test
    void customerTest() {
        ServiceOrder serviceOrder = getServiceOrderRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        serviceOrder.setCustomer(customerBack);
        assertThat(serviceOrder.getCustomer()).isEqualTo(customerBack);

        serviceOrder.customer(null);
        assertThat(serviceOrder.getCustomer()).isNull();
    }

    @Test
    void serviceMasterTest() {
        ServiceOrder serviceOrder = getServiceOrderRandomSampleGenerator();
        ServiceOrderMaster serviceOrderMasterBack = getServiceOrderMasterRandomSampleGenerator();

        serviceOrder.setServiceMaster(serviceOrderMasterBack);
        assertThat(serviceOrder.getServiceMaster()).isEqualTo(serviceOrderMasterBack);

        serviceOrder.serviceMaster(null);
        assertThat(serviceOrder.getServiceMaster()).isNull();
    }

    @Test
    void articleTest() {
        ServiceOrder serviceOrder = getServiceOrderRandomSampleGenerator();
        Article articleBack = getArticleRandomSampleGenerator();

        serviceOrder.setArticle(articleBack);
        assertThat(serviceOrder.getArticle()).isEqualTo(articleBack);

        serviceOrder.article(null);
        assertThat(serviceOrder.getArticle()).isNull();
    }

    @Test
    void addressTest() {
        ServiceOrder serviceOrder = getServiceOrderRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        serviceOrder.setAddress(addressBack);
        assertThat(serviceOrder.getAddress()).isEqualTo(addressBack);

        serviceOrder.address(null);
        assertThat(serviceOrder.getAddress()).isNull();
    }
}
