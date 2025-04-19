package com.framasaas.repository;

import com.framasaas.domain.FieldAgentSkillRule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FieldAgentSkillRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldAgentSkillRuleRepository
    extends JpaRepository<FieldAgentSkillRule, Long>, JpaSpecificationExecutor<FieldAgentSkillRule> {}
