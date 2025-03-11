package com.framasaas.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.be.domain.enumeration.ModeOfPayment;
import com.framasaas.be.domain.enumeration.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ServiceOrderPayment.
 */
@Entity
@Table(name = "service_order_payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceOrderPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "payment_link")
    private String paymentLink;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "mop")
    private ModeOfPayment mop;

    @Column(name = "pg_txn_id")
    private String pgTxnId;

    @Column(name = "pg_txn_response")
    private String pgTxnResponse;

    @Column(name = "pg_txn_response_time")
    private Instant pgTxnResponseTime;

    @Column(name = "payment_initiated_by")
    private String paymentInitiatedBy;

    @Column(name = "is_active")
    private Boolean isActive;

    @NotNull
    @Column(name = "createdd_by", nullable = false)
    private String createddBy;

    @NotNull
    @Column(name = "created_time", nullable = false)
    private Instant createdTime;

    @NotNull
    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @NotNull
    @Column(name = "updated_time", nullable = false)
    private Instant updatedTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceOrderPayment")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "additionalAttributePossibleValues",
            "franchise",
            "franchiseStatus",
            "franchisePerformance",
            "brand",
            "category",
            "address",
            "location",
            "franchiseUser",
            "customer",
            "document",
            "product",
            "hsn",
            "priceHistory",
            "warrantyMaster",
            "warrantyMasterPriceHistory",
            "article",
            "articleWarranty",
            "articleWarrantyDocument",
            "serviceOrder",
            "serviceOrderPayment",
            "serviceOrderFranchiseAssignment",
            "serviceOrderFieldAgentAssignment",
            "franchiseAllocationRuleSet",
            "franchiseAllocationRule",
            "fieldAgentSkillRuleSet",
            "fieldAgentSkillRule",
            "serviceOrderAssignment",
        },
        allowSetters = true
    )
    private Set<AdditionalAttribute> additionalAttributes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ServiceOrderPayment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentLink() {
        return this.paymentLink;
    }

    public ServiceOrderPayment paymentLink(String paymentLink) {
        this.setPaymentLink(paymentLink);
        return this;
    }

    public void setPaymentLink(String paymentLink) {
        this.paymentLink = paymentLink;
    }

    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }

    public ServiceOrderPayment paymentStatus(PaymentStatus paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public ModeOfPayment getMop() {
        return this.mop;
    }

    public ServiceOrderPayment mop(ModeOfPayment mop) {
        this.setMop(mop);
        return this;
    }

    public void setMop(ModeOfPayment mop) {
        this.mop = mop;
    }

    public String getPgTxnId() {
        return this.pgTxnId;
    }

    public ServiceOrderPayment pgTxnId(String pgTxnId) {
        this.setPgTxnId(pgTxnId);
        return this;
    }

    public void setPgTxnId(String pgTxnId) {
        this.pgTxnId = pgTxnId;
    }

    public String getPgTxnResponse() {
        return this.pgTxnResponse;
    }

    public ServiceOrderPayment pgTxnResponse(String pgTxnResponse) {
        this.setPgTxnResponse(pgTxnResponse);
        return this;
    }

    public void setPgTxnResponse(String pgTxnResponse) {
        this.pgTxnResponse = pgTxnResponse;
    }

    public Instant getPgTxnResponseTime() {
        return this.pgTxnResponseTime;
    }

    public ServiceOrderPayment pgTxnResponseTime(Instant pgTxnResponseTime) {
        this.setPgTxnResponseTime(pgTxnResponseTime);
        return this;
    }

    public void setPgTxnResponseTime(Instant pgTxnResponseTime) {
        this.pgTxnResponseTime = pgTxnResponseTime;
    }

    public String getPaymentInitiatedBy() {
        return this.paymentInitiatedBy;
    }

    public ServiceOrderPayment paymentInitiatedBy(String paymentInitiatedBy) {
        this.setPaymentInitiatedBy(paymentInitiatedBy);
        return this;
    }

    public void setPaymentInitiatedBy(String paymentInitiatedBy) {
        this.paymentInitiatedBy = paymentInitiatedBy;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public ServiceOrderPayment isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public ServiceOrderPayment createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public ServiceOrderPayment createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public ServiceOrderPayment updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public ServiceOrderPayment updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<AdditionalAttribute> getAdditionalAttributes() {
        return this.additionalAttributes;
    }

    public void setAdditionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        if (this.additionalAttributes != null) {
            this.additionalAttributes.forEach(i -> i.setServiceOrderPayment(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setServiceOrderPayment(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public ServiceOrderPayment additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public ServiceOrderPayment addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setServiceOrderPayment(this);
        return this;
    }

    public ServiceOrderPayment removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setServiceOrderPayment(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceOrderPayment)) {
            return false;
        }
        return getId() != null && getId().equals(((ServiceOrderPayment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOrderPayment{" +
            "id=" + getId() +
            ", paymentLink='" + getPaymentLink() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", mop='" + getMop() + "'" +
            ", pgTxnId='" + getPgTxnId() + "'" +
            ", pgTxnResponse='" + getPgTxnResponse() + "'" +
            ", pgTxnResponseTime='" + getPgTxnResponseTime() + "'" +
            ", paymentInitiatedBy='" + getPaymentInitiatedBy() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
