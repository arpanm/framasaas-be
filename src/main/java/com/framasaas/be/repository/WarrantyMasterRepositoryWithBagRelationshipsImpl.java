package com.framasaas.be.repository;

import com.framasaas.be.domain.WarrantyMaster;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class WarrantyMasterRepositoryWithBagRelationshipsImpl implements WarrantyMasterRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String WARRANTYMASTERS_PARAMETER = "warrantyMasters";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<WarrantyMaster> fetchBagRelationships(Optional<WarrantyMaster> warrantyMaster) {
        return warrantyMaster.map(this::fetchCoveredSpares);
    }

    @Override
    public Page<WarrantyMaster> fetchBagRelationships(Page<WarrantyMaster> warrantyMasters) {
        return new PageImpl<>(
            fetchBagRelationships(warrantyMasters.getContent()),
            warrantyMasters.getPageable(),
            warrantyMasters.getTotalElements()
        );
    }

    @Override
    public List<WarrantyMaster> fetchBagRelationships(List<WarrantyMaster> warrantyMasters) {
        return Optional.of(warrantyMasters).map(this::fetchCoveredSpares).orElse(Collections.emptyList());
    }

    WarrantyMaster fetchCoveredSpares(WarrantyMaster result) {
        return entityManager
            .createQuery(
                "select warrantyMaster from WarrantyMaster warrantyMaster left join fetch warrantyMaster.coveredSpares where warrantyMaster.id = :id",
                WarrantyMaster.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<WarrantyMaster> fetchCoveredSpares(List<WarrantyMaster> warrantyMasters) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, warrantyMasters.size()).forEach(index -> order.put(warrantyMasters.get(index).getId(), index));
        List<WarrantyMaster> result = entityManager
            .createQuery(
                "select warrantyMaster from WarrantyMaster warrantyMaster left join fetch warrantyMaster.coveredSpares where warrantyMaster in :warrantyMasters",
                WarrantyMaster.class
            )
            .setParameter(WARRANTYMASTERS_PARAMETER, warrantyMasters)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
