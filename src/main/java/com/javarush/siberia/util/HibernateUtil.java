package com.javarush.siberia.util;

import com.javarush.siberia.config.ApplicationProperties;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory;

    static {
        try {
            ApplicationProperties props = new ApplicationProperties();
            Configuration configuration = new Configuration()
                    .setProperties(props)
                    .addAnnotatedClass(com.javarush.siberia.model.User.class)
                    .addAnnotatedClass(com.javarush.siberia.model.Quest.class)
                    .addAnnotatedClass(com.javarush.siberia.model.QuestStep.class)
                    .addAnnotatedClass(com.javarush.siberia.model.UserStats.class);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

}