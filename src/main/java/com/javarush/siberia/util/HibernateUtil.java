package com.javarush.siberia.util;

import com.javarush.siberia.config.ApplicationProperties;
import com.javarush.siberia.model.Quest;
import com.javarush.siberia.model.QuestStep;
import com.javarush.siberia.model.User;
import com.javarush.siberia.model.UserStats;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration()
                    .setProperties(new ApplicationProperties())
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Quest.class)
                    .addAnnotatedClass(QuestStep.class)
                    .addAnnotatedClass(UserStats.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}