package com.javarush.siberia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_stats", schema = "game")
@Getter
@Setter
public class UserStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userstats_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total_games", nullable = false)
    private int totalGames = 0;

    @Column(nullable = false)
    private int wins = 0;

    @Column(nullable = false)
    private int losses = 0;

    public void increment(boolean victory) {
        totalGames++;
        if (victory) {
            wins++;
        } else {
            losses++;
        }
    }
}