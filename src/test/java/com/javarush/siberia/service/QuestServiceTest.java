package com.javarush.siberia.service;

import com.javarush.siberia.model.QuestStep;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class QuestServiceTest {

    private QuestService questService;

    @BeforeEach
    void init() {
        questService = new QuestService();
    }

    @Test
    void testGetStep_DefaultQuest() {
        QuestStep step = questService.getStep("defaultQuest", "start");
        Assertions.assertNotNull(step);
        Assertions.assertEquals("Вы находитесь в темном лесу. Куда пойдете?", step.getText());
    }

    @Test
    void testGetStep_NotFoundQuest() {
        QuestStep step = questService.getStep("unknownQuest", "start");
        Assertions.assertNull(step);
    }

    @Test
    void testAddQuest() {
        questService.addQuest("myQuest");

        Map<String, String> options = new HashMap<>();
        options.put("Go", "next");

        questService.addStep("myQuest", "start", "Begin", "images/start.png", options, false, false);

        QuestStep step = questService.getStep("myQuest", "start");
        Assertions.assertNotNull(step);
        Assertions.assertEquals("Begin", step.getText());
        Assertions.assertEquals("images/start.png", step.getImagePath());
        Assertions.assertEquals("next", step.getOptions().get("Go"));
        Assertions.assertFalse(step.isEnd());
        Assertions.assertFalse(step.isVictory());
    }
}