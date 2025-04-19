package com.framasaas.service.dto;

import com.framasaas.domain.enumeration.ServiceOrderStatus;
import com.framasaas.domain.enumeration.ServiceOrderType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.ServiceOrder} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceOrderDTO implements Serializable {

    private Long id;

    private String description;

    private ServiceOrderType orderType;

    private ServiceOrderStatus orderStatus;

    private Boolean inWarranty;

    private Boolean isFree;

    private Boolean isSpareNeeded;

    private Instant confirmedTime;

    private Instant closedTime;

    private Float serviceCharge;

    private Float tax;

    private Float totalSpareCharges;

    private Float totalSpareTax;

    private Float totalCharges;

    private Float totalPaymentDone;

    private String customerInvoicePath;

    private Float nps;

    private Long priority;

    private Boolean isActive;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private CustomerDTO customer;

    private ServiceOrderMasterDTO serviceMaster;

    private ArticleDTO article;

    private AddressDTO address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServiceOrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(ServiceOrderType orderType) {
        this.orderType = orderType;
    }

    public ServiceOrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(ServiceOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Boolean getInWarranty() {
        return inWarranty;
    }

    public void setInWarranty(Boolean inWarranty) {
        this.inWarranty = inWarranty;
    }

    public Boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }

    public Boolean getIsSpareNeeded() {
        return isSpareNeeded;
    }

    public void setIsSpareNeeded(Boolean isSpareNeeded) {
        this.isSpareNeeded = isSpareNeeded;
    }

    public Instant getConfirmedTime() {
        return confirmedTime;
    }

    public void setConfirmedTime(Instant confirmedTime) {
        this.confirmedTime = confirmedTime;
    }

    public Instant getClosedTime() {
        return closedTime;
    }

    public void setClosedTime(Instant closedTime) {
        this.closedTime = closedTime;
    }

    public Float getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Float serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Float getTotalSpareCharges() {
        return totalSpareCharges;
    }

    public void setTotalSpareCharges(Float totalSpareCharges) {
        this.totalSpareCharges = totalSpareCharges;
    }

    public Float getTotalSpareTax() {
        return totalSpareTax;
    }

    public void setTotalSpareTax(Float totalSpareTax) {
        this.totalSpareTax = totalSpareTax;
    }

    public Float getTotalCharges() {
        return totalCharges;
    }

    public void setTotalCharges(Float totalCharges) {
        this.totalCharges = totalCharges;
    }

    public Float getTotalPaymentDone() {
        return totalPaymentDone;
    }

    public void setTotalPaymentDone(Float totalPaymentDone) {
        this.totalPaymentDone = totalPaymentDone;
    }

    public String getCustomerInvoicePath() {
        return customerInvoicePath;
    }

    public void setCustomerInvoicePath(String customerInvoicePath) {
        this.customerInvoicePath = customerInvoicePath;
    }

    public Float getNps() {
        return nps;
    }

    public void setNps(Float nps) {
        this.nps = nps;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
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

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public ServiceOrderMasterDTO getServiceMaster() {
        return serviceMaster;
    }

    public void setServiceMaster(ServiceOrderMasterDTO serviceMaster) {
        this.serviceMaster = serviceMaster;
    }

    public ArticleDTO getArticle() {
        return article;
    }

    public void setArticle(ArticleDTO article) {
        this.article = article;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceOrderDTO)) {
            return false;
        }

        ServiceOrderDTO serviceOrderDTO = (ServiceOrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, serviceOrderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOrderDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", orderType='" + getOrderType() + "'" +
            ", orderStatus='" + getOrderStatus() + "'" +
            ", inWarranty='" + getInWarranty() + "'" +
            ", isFree='" + getIsFree() + "'" +
            ", isSpareNeeded='" + getIsSpareNeeded() + "'" +
            ", confirmedTime='" + getConfirmedTime() + "'" +
            ", closedTime='" + getClosedTime() + "'" +
            ", serviceCharge=" + getServiceCharge() +
            ", tax=" + getTax() +
            ", totalSpareCharges=" + getTotalSpareCharges() +
            ", totalSpareTax=" + getTotalSpareTax() +
            ", totalCharges=" + getTotalCharges() +
            ", totalPaymentDone=" + getTotalPaymentDone() +
            ", customerInvoicePath='" + getCustomerInvoicePath() + "'" +
            ", nps=" + getNps() +
            ", priority=" + getPriority() +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", customer=" + getCustomer() +
            ", serviceMaster=" + getServiceMaster() +
            ", article=" + getArticle() +
            ", address=" + getAddress() +
            "}";
    }
}
