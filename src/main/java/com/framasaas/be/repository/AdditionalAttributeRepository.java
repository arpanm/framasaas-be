package com.framasaas.be.repository;

import com.framasaas.be.domain.AdditionalAttribute;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AdditionalAttribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdditionalAttributeRepository extends JpaRepository<AdditionalAttribute, Long> {}
