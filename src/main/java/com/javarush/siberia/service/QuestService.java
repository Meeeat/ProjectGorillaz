package com.javarush.siberia.service;

import com.javarush.siberia.model.Quest;
import com.javarush.siberia.model.QuestStep;
import com.javarush.siberia.model.User;
import com.javarush.siberia.repository.QuestRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QuestService {
    private final QuestRepository questRepo = new QuestRepository();

    public QuestStep getStep(String questId, String stepId) {
        return questRepo.getStep(questId, stepId);
    }

    public void addQuest(String questId, User author) {
        Quest quest = new Quest();
        quest.setTitle(questId);
        quest.setAuthor(author);
        questRepo.saveQuest(quest);
    }

    public void addStep(String questId, String stepId, String text, String imagePath,
                        Map<String, String> options, boolean isEnd, boolean isVictory) {
        Quest quest = questRepo.findByTitle(questId);
        if (quest != null) {
            QuestStep step = new QuestStep();
            step.setQuest(quest);
            step.setStepId(stepId);
            step.setText(text);
            step.setImagePath(imagePath);
            step.setOptions(options);
            step.setEnd(isEnd);
            step.setVictory(isVictory);
            questRepo.saveStep(step);
        }
    }

    public List<String> getAllQuestIds() {
        return questRepo.getAllQuests().stream().map(Quest::getTitle).collect(Collectors.toList());
    }

}