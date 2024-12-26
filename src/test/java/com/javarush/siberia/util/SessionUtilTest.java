package com.javarush.siberia.util;

import com.javarush.siberia.model.QuestState;
import com.javarush.siberia.model.Stats;
import com.javarush.siberia.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SessionUtilTest {

    private HttpSession session;

    @BeforeEach
    void setup() {
        session = Mockito.mock(HttpSession.class);
    }

    @Test
    void testGetQuestState_Existing() {
        QuestState mockState = new QuestState("testQuest","start");
        Mockito.when(session.getAttribute("questState")).thenReturn(mockState);

        QuestState result = SessionUtil.getQuestState(session);
        Assertions.assertEquals("testQuest", result.getQuestId());
        Mockito.verify(session, Mockito.times(1)).getAttribute("questState");
    }

    @Test
    void testGetQuestState_NotExisting() {
        Mockito.when(session.getAttribute("questState")).thenReturn(null);
        QuestState result = SessionUtil.getQuestState(session);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("start", result.getCurrentStepId());
        Mockito.verify(session, Mockito.times(1)).setAttribute(Mockito.eq("questState"), Mockito.any(QuestState.class));
    }

    @Test
    void testIncrementStats() {
        SessionUtil.incrementStats("admin", true);
    }
}