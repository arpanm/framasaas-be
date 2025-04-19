package com.framasaas.service;

import com.framasaas.service.dto.FieldAgentSkillRuleSetDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.FieldAgentSkillRuleSet}.
 */
public interface FieldAgentSkillRuleSetService {
    /**
     * Save a fieldAgentSkillRuleSet.
     *
     * @param fieldAgentSkillRuleSetDTO the entity to save.
     * @return the persisted entity.
     */
    FieldAgentSkillRuleSetDTO save(FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO);

    /**
     * Updates a fieldAgentSkillRuleSet.
     *
     * @param fieldAgentSkillRuleSetDTO the entity to update.
     * @return the persisted entity.
     */
    FieldAgentSkillRuleSetDTO update(FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO);

    /**
     * Partially updates a fieldAgentSkillRuleSet.
     *
     * @param fieldAgentSkillRuleSetDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FieldAgentSkillRuleSetDTO> partialUpdate(FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO);

    /**
     * Get the "id" fieldAgentSkillRuleSet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FieldAgentSkillRuleSetDTO> findOne(Long id);

    /**
     * Delete the "id" fieldAgentSkillRuleSet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
