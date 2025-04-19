package com.framasaas.service.dto;

import com.framasaas.domain.enumeration.ModeOfPayment;
import com.framasaas.domain.enumeration.PaymentStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.ServiceOrderPayment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceOrderPaymentDTO implements Serializable {

    private Long id;

    private String paymentLink;

    private PaymentStatus paymentStatus;

    private ModeOfPayment mop;

    private String pgTxnId;

    private String pgTxnResponse;

    private Instant pgTxnResponseTime;

    private String paymentInitiatedBy;

    private Boolean isActive;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentLink() {
        return paymentLink;
    }

    public void setPaymentLink(String paymentLink) {
        this.paymentLink = paymentLink;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public ModeOfPayment getMop() {
        return mop;
    }

    public void setMop(ModeOfPayment mop) {
        this.mop = mop;
    }

    public String getPgTxnId() {
        return pgTxnId;
    }

    public void setPgTxnId(String pgTxnId) {
        this.pgTxnId = pgTxnId;
    }

    public String getPgTxnResponse() {
        return pgTxnResponse;
    }

    public void setPgTxnResponse(String pgTxnResponse) {
        this.pgTxnResponse = pgTxnResponse;
    }

    public Instant getPgTxnResponseTime() {
        return pgTxnResponseTime;
    }

    public void setPgTxnResponseTime(Instant pgTxnResponseTime) {
        this.pgTxnResponseTime = pgTxnResponseTime;
    }

    public String getPaymentInitiatedBy() {
        return paymentInitiatedBy;
    }

    public void setPaymentInitiatedBy(String paymentInitiatedBy) {
        this.paymentInitiatedBy = paymentInitiatedBy;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return createddBy;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceOrderPaymentDTO)) {
            return false;
        }

        ServiceOrderPaymentDTO serviceOrderPaymentDTO = (ServiceOrderPaymentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, serviceOrderPaymentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOrderPaymentDTO{" +
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
