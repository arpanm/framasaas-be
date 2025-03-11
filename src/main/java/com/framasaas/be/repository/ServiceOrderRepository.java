package com.framasaas.be.repository;

import com.framasaas.be.domain.ServiceOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ServiceOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {}
