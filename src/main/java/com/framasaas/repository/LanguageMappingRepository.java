package com.framasaas.repository;

import com.framasaas.domain.LanguageMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LanguageMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LanguageMappingRepository extends JpaRepository<LanguageMapping, Long>, JpaSpecificationExecutor<LanguageMapping> {}
