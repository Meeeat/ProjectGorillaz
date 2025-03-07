package com.javarush.siberia.util;

import com.javarush.siberia.config.ApplicationProperties;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import java.sql.DriverManager;

public class LiquibaseInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ApplicationProperties props = new ApplicationProperties();
            String url = props.getProperty("hibernate.connection.url");
            String username = props.getProperty("hibernate.connection.username");
            String password = props.getProperty("hibernate.connection.password");
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(
                            new JdbcConnection(DriverManager.getConnection(url, username, password)));
            Liquibase liquibase = new Liquibase(
                    "db/changelog/db.changelog-master.xml",
                    new ClassLoaderResourceAccessor(),
                    database);
            liquibase.update("");
        } catch (Exception e) {
            throw new RuntimeException("can't run Liquibase", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}