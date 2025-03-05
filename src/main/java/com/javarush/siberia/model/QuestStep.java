package com.javarush.siberia.model;

import lombok.Getter;
import java.util.Map;

@Getter
public class QuestStep {
    private String text;
    private String imagePath;
    private Map<String, String> options;
    private boolean isEnd;
    private boolean isVictory;

    public QuestStep(String text, String imagePath, Map<String, String> options, boolean isEnd, boolean isVictory) {
        this.text = text;
        this.imagePath = imagePath;
        this.options = options;
        this.isEnd = isEnd;
        this.isVictory = isVictory;
    }
}