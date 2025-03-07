package com.javarush.siberia.repository;

import com.javarush.siberia.model.Role;
import com.javarush.siberia.model.User;
import com.javarush.siberia.model.UserStats;
import com.javarush.siberia.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserRepository {
    public User findByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM User WHERE username = :username";
            return session.createQuery(hql, User.class)
                    .setParameter("username", username)
                    .uniqueResult();
        }
    }

    public void save(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(user);
            UserStats stats = new UserStats();
            stats.setUser(user);
            session.persist(stats);
            tx.commit();
        }
    }

    public boolean updateUser(String oldUsername, String newUsername, String newPassword, Role newRole) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            User user = findByUsername(oldUsername);
            if (user != null) {
                if (newUsername != null && !newUsername.trim().isEmpty()) user.setUsername(newUsername);
                if (newPassword != null && !newPassword.isEmpty()) user.setPassword(newPassword);
                if (newRole != null) user.setRole(newRole);
                session.merge(user);
                tx.commit();
                return true;
            } else {
                tx.commit();
                return false;
            }
        }
    }

    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM User";
            return session.createQuery(hql, User.class).list();
        }
    }

    public UserStats getStats(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM UserStats WHERE user.id = :userId";
            return session.createQuery(hql, UserStats.class)
                    .setParameter("userId", userId)
                    .uniqueResult();
        }
    }

    public void incrementStats(Long userId, boolean victory) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            UserStats stats = getStats(userId);
            if (stats != null) {
                stats.increment(victory);
                session.merge(stats);
            }
            tx.commit();
        }
    }

    public List<UserStats> getAllStats() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM UserStats";
            return session.createQuery(hql, UserStats.class).list();
        }
    }
}