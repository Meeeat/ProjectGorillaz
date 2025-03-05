package com.javarush.siberia.service;

import com.javarush.siberia.model.QuestStep;
import com.javarush.siberia.repository.QuestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestService {
    private final QuestRepository questRepo = new QuestRepository();

    public QuestStep getStep(String questId, String stepId) {
        return questRepo.getStep(questId, stepId);
    }

    public void addQuest(String questId) {
        questRepo.addQuest(questId);
    }

    public void addStep(String questId, String stepId, String text, String imagePath,
                        Map<String, String> options, boolean isEnd, boolean isVictory) {
        QuestStep step = new QuestStep(text, imagePath, options, isEnd, isVictory);
        questRepo.addStep(questId, stepId, step);
    }

    public List<String> getAllQuestIds() {
        return new ArrayList<>(questRepo.getAllQuests().keySet());
    }

}