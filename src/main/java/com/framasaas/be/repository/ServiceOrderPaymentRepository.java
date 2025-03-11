package com.framasaas.be.repository;

import com.framasaas.be.domain.ServiceOrderPayment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ServiceOrderPayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceOrderPaymentRepository extends JpaRepository<ServiceOrderPayment, Long> {}
