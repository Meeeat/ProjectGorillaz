package com.javarush.siberia.repository;

import com.javarush.siberia.model.QuestStep;

import java.util.HashMap;
import java.util.Map;

public class QuestRepository {
    private static final Map<String, Map<String, QuestStep>> QUESTS = new HashMap<>();

    static {
        Map<String, QuestStep> defaultQuest = new HashMap<>();
        defaultQuest.put("start", new QuestStep(
                "Р’С‹ РЅР°С…РѕРґРёС‚РµСЃСЊ РІ С‚РµРјРЅРѕРј Р»РµСЃСѓ. РљСѓРґР° РїРѕР№РґРµС‚Рµ?",
                "images/step1.jpg",
                "РќР° РЎРµРІРµСЂ", "РќР° Р®Рі",
                "north", "south",
                false, false
        ));
        defaultQuest.put("north", new QuestStep(
                "Р’С‹ РїСЂРёС€Р»Рё Рє РѕР·РµСЂСѓ. Р’С‹РїСЊРµС‚Рµ РІРѕРґС‹?",
                "images/step2_north.jpg",
                "Р”Р°, РІС‹РїРёС‚СЊ", "РќРµС‚, СѓР№С‚Рё",
                "drink", "ignore",
                false, false
        ));
        defaultQuest.put("south", new QuestStep(
                "Р’С‹ РІСЃС‚СЂРµС‚РёР»Рё РіРѕР±Р»РёРЅР°. Р‘СѓРґРµС‚Рµ СЃСЂР°Р¶Р°С‚СЊСЃСЏ?",
                "images/step2_south.jpg",
                "РЎСЂР°Р¶Р°С‚СЊСЃСЏ", "РЈР±РµР¶Р°С‚СЊ",
                "fight", "run",
                false, false
        ));
        defaultQuest.put("drink", new QuestStep(
                "Р’РѕРґР° РѕРєР°Р·Р°Р»Р°СЃСЊ РІРѕР»С€РµР±РЅРѕР№! Р’С‹ РїРѕР±РµРґРёР»Рё!",
                "images/victory.jpg",
                null, null,
                null, null,
                true, true
        ));
        defaultQuest.put("ignore", new QuestStep(
                "Р’С‹ СѓС€Р»Рё Рё Р·Р°Р±Р»СѓРґРёР»РёСЃСЊ. РџРѕСЂР°Р¶РµРЅРёРµ.",
                "images/defeat.jpg",
                null, null,
                null, null,
                true, false
        ));
        defaultQuest.put("fight", new QuestStep(
                "Р“РѕР±Р»РёРЅ РѕРєР°Р·Р°Р»СЃСЏ СЃР»Р°Р±. Р’С‹ РїРѕР±РµРґРёР»Рё!",
                "images/victory.jpg",
                null, null,
                null, null,
                true, true
        ));
        defaultQuest.put("run", new QuestStep(
                "Р’С‹ Р±РµР¶Р°Р»Рё С‚Р°Рє Р±С‹СЃС‚СЂРѕ, С‡С‚Рѕ СѓРїР°Р»Рё РІ РїСЂРѕРїР°СЃС‚СЊ. РџРѕСЂР°Р¶РµРЅРёРµ.",
                "images/defeat.jpg",
                null, null,
                null, null,
                true, false
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
        QUESTS.computeIfAbsent(questId, k->new HashMap<>()).put(stepId, step);
    }

    public Map<String, Map<String, QuestStep>> getAllQuests() {
        return QUESTS;
    }

}