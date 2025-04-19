package com.framasaas.service;

import com.framasaas.service.dto.FieldAgentSkillRuleDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.FieldAgentSkillRule}.
 */
public interface FieldAgentSkillRuleService {
    /**
     * Save a fieldAgentSkillRule.
     *
     * @param fieldAgentSkillRuleDTO the entity to save.
     * @return the persisted entity.
     */
    FieldAgentSkillRuleDTO save(FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO);

    /**
     * Updates a fieldAgentSkillRule.
     *
     * @param fieldAgentSkillRuleDTO the entity to update.
     * @return the persisted entity.
     */
    FieldAgentSkillRuleDTO update(FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO);

    /**
     * Partially updates a fieldAgentSkillRule.
     *
     * @param fieldAgentSkillRuleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FieldAgentSkillRuleDTO> partialUpdate(FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO);

    /**
     * Get the "id" fieldAgentSkillRule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FieldAgentSkillRuleDTO> findOne(Long id);

    /**
     * Delete the "id" fieldAgentSkillRule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
