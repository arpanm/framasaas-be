package com.framasaas.be.repository;

import com.framasaas.be.domain.FieldAgentSkillRule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FieldAgentSkillRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldAgentSkillRuleRepository extends JpaRepository<FieldAgentSkillRule, Long> {}
