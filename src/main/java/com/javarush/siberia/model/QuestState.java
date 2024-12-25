package com.javarush.siberia.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestState {
    private String questId;
    private String currentStepId;

    public QuestState(String questId, String currentStepId) {
        this.questId = questId;
        this.currentStepId = currentStepId;
    }

}