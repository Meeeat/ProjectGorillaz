package com.javarush.siberia.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StatsTest {

    @Test
    public void testInitialValues() {
        Stats stats = new Stats();
        Assertions.assertEquals(0, stats.getTotal());
        Assertions.assertEquals(0, stats.getWins());
        Assertions.assertEquals(0, stats.getLosses());
    }

    @Test
    public void testIncrementVictory() {
        Stats stats = new Stats();
        stats.increment(true);
        Assertions.assertEquals(1, stats.getTotal());
        Assertions.assertEquals(1, stats.getWins());
        Assertions.assertEquals(0, stats.getLosses());
    }

    @Test
    public void testIncrementDefeat() {
        Stats stats = new Stats();
        stats.increment(false);
        Assertions.assertEquals(1, stats.getTotal());
        Assertions.assertEquals(0, stats.getWins());
        Assertions.assertEquals(1, stats.getLosses());
    }

    @Test
    public void testSetters() {
        Stats stats = new Stats();
        stats.setTotal(10);
        stats.setWins(3);
        stats.setLosses(7);
        Assertions.assertEquals(10, stats.getTotal());
        Assertions.assertEquals(3, stats.getWins());
        Assertions.assertEquals(7, stats.getLosses());
    }

}