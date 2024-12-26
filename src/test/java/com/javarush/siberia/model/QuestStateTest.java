package com.javarush.siberia.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QuestStateTest {

    @Test
    public void testQuestState() {
        QuestState state = new QuestState("myQuest", "start");
        Assertions.assertEquals("myQuest", state.getQuestId());
        Assertions.assertEquals("start", state.getCurrentStepId());

        state.setQuestId("anotherQuest");
        state.setCurrentStepId("middle");
        Assertions.assertEquals("anotherQuest", state.getQuestId());
        Assertions.assertEquals("middle", state.getCurrentStepId());
    }
}