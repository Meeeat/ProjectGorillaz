package com.javarush.siberia.repository;

import com.javarush.siberia.model.QuestStep;

import java.util.HashMap;
import java.util.Map;

public class QuestRepository {
    private static final Map<String, Map<String, QuestStep>> QUESTS = new HashMap<>();

    static {
        Map<String, QuestStep> defaultQuest = new HashMap<>();
        defaultQuest.put("start", new QuestStep(
                "Вы находитесь в темном лесу. Куда пойдете?",
                "images/step1.jpg",
                "На Север", "На Юг",
                "north", "south",
                false, false
        ));
        defaultQuest.put("north", new QuestStep(
                "Вы пришли к озеру. Выпьете воды?",
                "images/step2_north.jpg",
                "Да, выпить", "Нет, уйти",
                "drink", "ignore",
                false, false
        ));
        defaultQuest.put("south", new QuestStep(
                "Вы встретили гоблина. Будете сражаться?",
                "images/step2_south.jpg",
                "Сражаться", "Убежать",
                "fight", "run",
                false, false
        ));
        defaultQuest.put("drink", new QuestStep(
                "Вода оказалась волшебной! Вы победили!",
                "images/victory.jpg",
                null, null,
                null, null,
                true, true
        ));
        defaultQuest.put("ignore", new QuestStep(
                "Вы ушли и заблудились. Поражение.",
                "images/defeat.jpg",
                null, null,
                null, null,
                true, false
        ));
        defaultQuest.put("fight", new QuestStep(
                "Гоблин оказался слаб. Вы победили!",
                "images/victory.jpg",
                null, null,
                null, null,
                true, true
        ));
        defaultQuest.put("run", new QuestStep(
                "Вы бежали так быстро, что упали в пропасть. Поражение.",
                "images/defeat.jpg",
                null, null,
                null, null,
                true, false
        ));

        QUESTS.put("defaultQuest", defaultQuest);
    }

    public QuestStep getStep(String questId, String stepId) {
        Map<String, QuestStep> steps = QUESTS.get(questId);
        if (steps == null) return null;
        return steps.get(stepId);
    }

    public void addQuest(String questId) {
        if (!QUESTS.containsKey(questId)) {
            QUESTS.put(questId, new HashMap<>());
        }
    }

    public void addStep(String questId, String stepId, QuestStep step) {
        QUESTS.computeIfAbsent(questId, k->new HashMap<>()).put(stepId, step);
    }

    public Map<String, Map<String, QuestStep>> getAllQuests() {
        return QUESTS;
    }

}