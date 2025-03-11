package com.framasaas.be.repository;

import com.framasaas.be.domain.ArticleWarrantyDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArticleWarrantyDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleWarrantyDetailsRepository extends JpaRepository<ArticleWarrantyDetails, Long> {}
