package com.framasaas.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FieldAgentSkillRuleTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FieldAgentSkillRule getFieldAgentSkillRuleSample1() {
        return new FieldAgentSkillRule().id(1L).createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static FieldAgentSkillRule getFieldAgentSkillRuleSample2() {
        return new FieldAgentSkillRule().id(2L).createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static FieldAgentSkillRule getFieldAgentSkillRuleRandomSampleGenerator() {
        return new FieldAgentSkillRule()
            .id(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
