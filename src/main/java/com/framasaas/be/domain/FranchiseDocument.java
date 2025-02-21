package com.framasaas.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.be.domain.enumeration.DocumentFormat;
import com.framasaas.be.domain.enumeration.DocumentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FranchiseDocument.
 */
@Entity
@Table(name = "franchise_document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FranchiseDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "document_name", nullable = false)
    private String documentName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private DocumentType documentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "document_format", nullable = false)
    private DocumentFormat documentFormat;

    @Column(name = "document_size")
    private Long documentSize;

    @NotNull
    @Column(name = "document_path", nullable = false)
    private String documentPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "address", "franchiseStatusHistories", "brands", "categories", "documents" }, allowSetters = true)
    private Franchise franchise;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FranchiseDocument id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return this.documentName;
    }

    public FranchiseDocument documentName(String documentName) {
        this.setDocumentName(documentName);
        return this;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public DocumentType getDocumentType() {
        return this.documentType;
    }

    public FranchiseDocument documentType(DocumentType documentType) {
        this.setDocumentType(documentType);
        return this;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public DocumentFormat getDocumentFormat() {
        return this.documentFormat;
    }

    public FranchiseDocument documentFormat(DocumentFormat documentFormat) {
        this.setDocumentFormat(documentFormat);
        return this;
    }

    public void setDocumentFormat(DocumentFormat documentFormat) {
        this.documentFormat = documentFormat;
    }

    public Long getDocumentSize() {
        return this.documentSize;
    }

    public FranchiseDocument documentSize(Long documentSize) {
        this.setDocumentSize(documentSize);
        return this;
    }

    public void setDocumentSize(Long documentSize) {
        this.documentSize = documentSize;
    }

    public String getDocumentPath() {
        return this.documentPath;
    }

    public FranchiseDocument documentPath(String documentPath) {
        this.setDocumentPath(documentPath);
        return this;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public Franchise getFranchise() {
        return this.franchise;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
    }

    public FranchiseDocument franchise(Franchise franchise) {
        this.setFranchise(franchise);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FranchiseDocument)) {
            return false;
        }
        return getId() != null && getId().equals(((FranchiseDocument) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FranchiseDocument{" +
            "id=" + getId() +
            ", documentName='" + getDocumentName() + "'" +
            ", documentType='" + getDocumentType() + "'" +
            ", documentFormat='" + getDocumentFormat() + "'" +
            ", documentSize=" + getDocumentSize() +
            ", documentPath='" + getDocumentPath() + "'" +
            "}";
    }
}
