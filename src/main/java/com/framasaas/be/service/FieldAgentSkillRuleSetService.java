package com.framasaas.be.service;

import com.framasaas.be.domain.FieldAgentSkillRuleSet;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.FieldAgentSkillRuleSet}.
 */
public interface FieldAgentSkillRuleSetService {
    /**
     * Save a fieldAgentSkillRuleSet.
     *
     * @param fieldAgentSkillRuleSet the entity to save.
     * @return the persisted entity.
     */
    FieldAgentSkillRuleSet save(FieldAgentSkillRuleSet fieldAgentSkillRuleSet);

    /**
     * Updates a fieldAgentSkillRuleSet.
     *
     * @param fieldAgentSkillRuleSet the entity to update.
     * @return the persisted entity.
     */
    FieldAgentSkillRuleSet update(FieldAgentSkillRuleSet fieldAgentSkillRuleSet);

    /**
     * Partially updates a fieldAgentSkillRuleSet.
     *
     * @param fieldAgentSkillRuleSet the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FieldAgentSkillRuleSet> partialUpdate(FieldAgentSkillRuleSet fieldAgentSkillRuleSet);

    /**
     * Get all the fieldAgentSkillRuleSets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FieldAgentSkillRuleSet> findAll(Pageable pageable);

    /**
     * Get the "id" fieldAgentSkillRuleSet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FieldAgentSkillRuleSet> findOne(Long id);

    /**
     * Delete the "id" fieldAgentSkillRuleSet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
