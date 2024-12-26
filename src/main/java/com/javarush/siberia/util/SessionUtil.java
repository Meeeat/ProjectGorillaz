package com.javarush.siberia.util;

import com.javarush.siberia.model.QuestState;
import com.javarush.siberia.repository.UserRepository;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {
    private static final UserRepository userRepo = new UserRepository();

    public static QuestState getQuestState(HttpSession session) {
        QuestState state = (QuestState) session.getAttribute("questState");
        if (state == null) {
            state = new QuestState("start", "start");
            session.setAttribute("questState", state);
        }
        return state;
    }

    public static void startQuest(HttpSession session, String questId) {
        QuestState state = new QuestState(questId, "start");
        session.setAttribute("questState", state);
    }

    public static void resetQuestState(HttpSession session) {
        session.removeAttribute("questState");
    }

    public static void incrementStats(String username, boolean victory) {
        userRepo.incrementStats(username, victory);
    }

}