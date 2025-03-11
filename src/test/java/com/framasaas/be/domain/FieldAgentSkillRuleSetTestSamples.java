package com.framasaas.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FieldAgentSkillRuleSetTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FieldAgentSkillRuleSet getFieldAgentSkillRuleSetSample1() {
        return new FieldAgentSkillRuleSet().id(1L).createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static FieldAgentSkillRuleSet getFieldAgentSkillRuleSetSample2() {
        return new FieldAgentSkillRuleSet().id(2L).createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static FieldAgentSkillRuleSet getFieldAgentSkillRuleSetRandomSampleGenerator() {
        return new FieldAgentSkillRuleSet()
            .id(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
