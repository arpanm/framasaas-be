package com.framasaas.repository;

import com.framasaas.domain.ArticleWarrantyDetailsDocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArticleWarrantyDetailsDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleWarrantyDetailsDocumentRepository extends JpaRepository<ArticleWarrantyDetailsDocument, Long> {}
