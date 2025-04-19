package com.framasaas.repository;

import com.framasaas.domain.FranchiseDocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FranchiseDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FranchiseDocumentRepository extends JpaRepository<FranchiseDocument, Long> {}
