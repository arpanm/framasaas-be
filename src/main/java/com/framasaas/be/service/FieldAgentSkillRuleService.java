package com.framasaas.be.service;

import com.framasaas.be.domain.FieldAgentSkillRule;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.FieldAgentSkillRule}.
 */
public interface FieldAgentSkillRuleService {
    /**
     * Save a fieldAgentSkillRule.
     *
     * @param fieldAgentSkillRule the entity to save.
     * @return the persisted entity.
     */
    FieldAgentSkillRule save(FieldAgentSkillRule fieldAgentSkillRule);

    /**
     * Updates a fieldAgentSkillRule.
     *
     * @param fieldAgentSkillRule the entity to update.
     * @return the persisted entity.
     */
    FieldAgentSkillRule update(FieldAgentSkillRule fieldAgentSkillRule);

    /**
     * Partially updates a fieldAgentSkillRule.
     *
     * @param fieldAgentSkillRule the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FieldAgentSkillRule> partialUpdate(FieldAgentSkillRule fieldAgentSkillRule);

    /**
     * Get all the fieldAgentSkillRules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FieldAgentSkillRule> findAll(Pageable pageable);

    /**
     * Get the "id" fieldAgentSkillRule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FieldAgentSkillRule> findOne(Long id);

    /**
     * Delete the "id" fieldAgentSkillRule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
