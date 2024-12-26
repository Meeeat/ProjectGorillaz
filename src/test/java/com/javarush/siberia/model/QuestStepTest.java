package com.javarush.siberia.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QuestStepTest {

    @Test
    public void testQuestStepFields() {
        QuestStep step = new QuestStep(
                "Welcome to the dungeon",
                "images/dungeon.jpg",
                "Go left", "Go right",
                "left", "right",
                false, false
        );
        Assertions.assertEquals("Welcome to the dungeon", step.getText());
        Assertions.assertEquals("images/dungeon.jpg", step.getImagePath());
        Assertions.assertEquals("Go left", step.getOption1());
        Assertions.assertEquals("Go right", step.getOption2());
        Assertions.assertEquals("left", step.getNextStepIfOption1());
        Assertions.assertEquals("right", step.getNextStepIfOption2());
        Assertions.assertFalse(step.isEnd());
        Assertions.assertFalse(step.isVictory());
    }

}