package com.javarush.siberia.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class QuestStepTest {

    @Test
    public void testQuestStepFields() {

        Map<String, String> options = new HashMap<>();
        options.put("Go left", "left");
        options.put("Go right", "right");

        QuestStep step = new QuestStep(
                "Welcome to the jungle",
                "images/step1.jpg",
                options,
                false,
                false
        );

        Assertions.assertEquals("Welcome to the jungle", step.getText());
        Assertions.assertEquals("images/jungle.jpg", step.getImagePath());

        Map<String, String> stepOptions = step.getOptions();
        Assertions.assertEquals("left", stepOptions.get("Go left"));
        Assertions.assertEquals("right", stepOptions.get("Go right"));

        Assertions.assertFalse(step.isEnd());
        Assertions.assertFalse(step.isVictory());
    }
}