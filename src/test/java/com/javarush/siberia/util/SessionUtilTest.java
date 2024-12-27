package com.javarush.siberia.util;

import com.javarush.siberia.model.QuestState;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;

class SessionUtilTest {

    @Test
    void testGetQuestState_Existing() {
        HttpSession session = mock(HttpSession.class);
        QuestState mockState = new QuestState("testQuest","start");
        when(session.getAttribute("questState")).thenReturn(mockState);
        QuestState result = SessionUtil.getQuestState(session);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("testQuest", result.getQuestId());
        verify(session).getAttribute("questState");
    }

    @Test
    void testStartQuest() {
        HttpSession session = mock(HttpSession.class);
        SessionUtil.startQuest(session, "myQuest");
        verify(session).setAttribute(eq("questState"), any(QuestState.class));
    }

    @Test
    void testResetQuestState() {
        HttpSession session = mock(HttpSession.class);
        SessionUtil.resetQuestState(session);
        verify(session).removeAttribute("questState");
    }

    @Test
    void testIncrementStats() {
        SessionUtil.incrementStats("admin", true);
    }

}