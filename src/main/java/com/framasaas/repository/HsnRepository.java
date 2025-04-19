package com.framasaas.repository;

import com.framasaas.domain.Hsn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Hsn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HsnRepository extends JpaRepository<Hsn, Long>, JpaSpecificationExecutor<Hsn> {}
