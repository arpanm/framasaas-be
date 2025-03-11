package com.framasaas.be.repository;

import com.framasaas.be.domain.AdditionalAttributePossibleValue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AdditionalAttributePossibleValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdditionalAttributePossibleValueRepository extends JpaRepository<AdditionalAttributePossibleValue, Long> {}
