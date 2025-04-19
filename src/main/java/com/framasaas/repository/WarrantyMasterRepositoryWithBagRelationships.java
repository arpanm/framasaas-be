package com.framasaas.repository;

import com.framasaas.domain.WarrantyMaster;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface WarrantyMasterRepositoryWithBagRelationships {
    Optional<WarrantyMaster> fetchBagRelationships(Optional<WarrantyMaster> warrantyMaster);

    List<WarrantyMaster> fetchBagRelationships(List<WarrantyMaster> warrantyMasters);

    Page<WarrantyMaster> fetchBagRelationships(Page<WarrantyMaster> warrantyMasters);
}
