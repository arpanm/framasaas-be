package com.framasaas.repository;

import com.framasaas.domain.ServiceOrderPayment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ServiceOrderPayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceOrderPaymentRepository
    extends JpaRepository<ServiceOrderPayment, Long>, JpaSpecificationExecutor<ServiceOrderPayment> {}
