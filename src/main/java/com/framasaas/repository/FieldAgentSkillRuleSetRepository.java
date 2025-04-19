package com.framasaas.repository;

import com.framasaas.domain.FieldAgentSkillRuleSet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FieldAgentSkillRuleSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldAgentSkillRuleSetRepository
    extends JpaRepository<FieldAgentSkillRuleSet, Long>, JpaSpecificationExecutor<FieldAgentSkillRuleSet> {}
