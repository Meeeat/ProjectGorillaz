package com.javarush.siberia.util;

import com.javarush.siberia.model.QuestState;
import com.javarush.siberia.repository.UserRepository;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {
    private static final UserRepository userRepo = new UserRepository();

    public static QuestState getQuestState(HttpSession session) {
        return (QuestState) session.getAttribute("questState");
    }

    public static void startQuest(HttpSession session, String questId) {
        QuestState state = new QuestState(questId, "start");
        session.setAttribute("questState", state);
    }

    public static void resetQuestState(HttpSession session) {
        session.removeAttribute("questState");
    }

    public static void incrementStats(Long userId, boolean victory) {
        userRepo.incrementStats(userId, victory);
    }

}