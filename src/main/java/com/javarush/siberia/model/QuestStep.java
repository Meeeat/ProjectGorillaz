package com.javarush.siberia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Table(name = "quest_steps", schema = "game")
@Getter
@Setter
public class QuestStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "queststeps_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quest_id", nullable = false)
    private Quest quest;

    @Column(name = "step_id", nullable = false)
    private String stepId;

    @Column(nullable = false)
    private String text;

    @Column(name = "image_path")
    private String imagePath;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> options;

    @Column(name = "is_end", nullable = false)
    private boolean isEnd;

    @Column(name = "is_victory", nullable = false)
    private boolean isVictory;
}