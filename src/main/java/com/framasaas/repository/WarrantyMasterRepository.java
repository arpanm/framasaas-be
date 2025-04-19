package com.framasaas.repository;

import com.framasaas.domain.WarrantyMaster;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WarrantyMaster entity.
 *
 * When extending this class, extend WarrantyMasterRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface WarrantyMasterRepository
    extends WarrantyMasterRepositoryWithBagRelationships, JpaRepository<WarrantyMaster, Long>, JpaSpecificationExecutor<WarrantyMaster> {
    default Optional<WarrantyMaster> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<WarrantyMaster> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<WarrantyMaster> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
