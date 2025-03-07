package com.javarush.siberia.repository;

import com.javarush.siberia.model.Quest;
import com.javarush.siberia.model.QuestStep;
import com.javarush.siberia.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class QuestRepository {
    public Quest findByTitle(String title) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM Quest WHERE title = :title";

            return session.createQuery(hql, Quest.class)
                    .setParameter("title", title)
                    .uniqueResult();
        }
    }

    public QuestStep getStep(String questTitle, String stepId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM QuestStep WHERE quest.title = :questTitle AND stepId = :stepId";

            return session.createQuery(hql, QuestStep.class)
                    .setParameter("questTitle", questTitle)
                    .setParameter("stepId", stepId)
                    .uniqueResult();
        }
    }

    public void saveQuest(Quest quest) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(quest);
            tx.commit();
        }
    }

    public void saveStep(QuestStep step) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(step);
            tx.commit();
        }
    }

    public List<Quest> getAllQuests() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM Quest";

            return session.createQuery(hql, Quest.class).list();
        }
    }

}