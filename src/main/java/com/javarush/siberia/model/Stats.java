package com.javarush.siberia.model;

import lombok.Getter;

@Getter
public class Stats {
    private int total;
    private int wins;
    private int losses;

    public Stats() {
        this.total = 0;
        this.wins = 0;
        this.losses = 0;
    }

    public void increment(boolean victory) {
        total++;
        if (victory) {
            wins++;
        } else {
            losses++;
        }
    }

}