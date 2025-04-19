package com.framasaas.repository;

import com.framasaas.domain.SupportingDocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SupportingDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupportingDocumentRepository
    extends JpaRepository<SupportingDocument, Long>, JpaSpecificationExecutor<SupportingDocument> {}
