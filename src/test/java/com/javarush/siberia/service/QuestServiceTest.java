package com.javarush.siberia.service;

import com.javarush.siberia.model.QuestStep;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        questService.addStep("myQuest", "start", "Begin", "images/start.png", "Go", null, "next", null, false, false);
        QuestStep step = questService.getStep("myQuest", "start");
        Assertions.assertNotNull(step);
        Assertions.assertEquals("Begin", step.getText());
    }

}