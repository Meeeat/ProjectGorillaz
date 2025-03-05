package com.javarush.siberia.repository;

import com.javarush.siberia.model.QuestStep;

import java.util.HashMap;
import java.util.Map;

public class QuestRepository {
    private static final Map<String, Map<String, QuestStep>> QUESTS = new HashMap<>();

    static {
        Map<String, QuestStep> defaultQuest = new HashMap<>();

        Map<String, String> startOptions = new HashMap<>();
        startOptions.put("На Север", "north");
        startOptions.put("На Юг", "south");
        defaultQuest.put("start", new QuestStep(
                "Вы находитесь в темном лесу. Куда пойдете?",
                "images/step1.jpg",
                startOptions,
                false,
                false
        ));

        Map<String, String> northOptions = new HashMap<>();
        northOptions.put("Да, выпить", "drink");
        northOptions.put("Нет, уйти", "ignore");
        defaultQuest.put("north", new QuestStep(
                "Вы пришли к озеру. Выпьете воды?",
                "images/step2_north.jpg",
                northOptions,
                false,
                false
        ));

        Map<String, String> southOptions = new HashMap<>();
        southOptions.put("Сражаться", "fight");
        southOptions.put("Убежать", "run");
        defaultQuest.put("south", new QuestStep(
                "Вы встретили гоблина. Будете сражаться?",
                "images/step2_south.jpg",
                southOptions,
                false,
                false
        ));

        defaultQuest.put("drink", new QuestStep(
                "Вода оказалась волшебной! Вы победили!",
                "images/victory.jpg",
                new HashMap<>(),
                true,
                true
        ));

        defaultQuest.put("ignore", new QuestStep(
                "Вы ушли и заблудились. Поражение.",
                "images/defeat.jpg",
                new HashMap<>(),
                true,
                false
        ));

        defaultQuest.put("fight", new QuestStep(
                "Гоблин оказался слаб. Вы победили!",
                "images/victory.jpg",
                new HashMap<>(),
                true,
                true
        ));

        defaultQuest.put("run", new QuestStep(
                "Вы бежали так быстро, что упали в пропасть. Поражение.",
                "images/defeat.jpg",
                new HashMap<>(),
                true,
                false
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
        QUESTS.computeIfAbsent(questId, k -> new HashMap<>()).put(stepId, step);
    }

    public Map<String, Map<String, QuestStep>> getAllQuests() {
        return QUESTS;
    }
}