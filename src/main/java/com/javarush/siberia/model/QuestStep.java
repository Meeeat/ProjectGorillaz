package com.javarush.siberia.model;

import lombok.Getter;

@Getter
public class QuestStep {
    private String text;
    private String imagePath;
    private String option1;
    private String option2;
    private String nextStepIfOption1;
    private String nextStepIfOption2;
    private boolean isEnd;
    private boolean isVictory;

    public QuestStep(String text, String imagePath, String option1, String option2,
                     String nextStepIfOption1, String nextStepIfOption2,
                     boolean isEnd, boolean isVictory) {
        this.text = text;
        this.imagePath = imagePath;
        this.option1 = option1;
        this.option2 = option2;
        this.nextStepIfOption1 = nextStepIfOption1;
        this.nextStepIfOption2 = nextStepIfOption2;
        this.isEnd = isEnd;
        this.isVictory = isVictory;
    }
}