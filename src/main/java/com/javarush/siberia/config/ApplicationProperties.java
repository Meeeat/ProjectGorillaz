package com.javarush.siberia.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class ApplicationProperties extends Properties {

    public static final String HIBERNATE_CONNECTION_URL = "hibernate.connection.url";
    public static final String HIBERNATE_CONNECTION_USERNAME = "hibernate.connection.username";
    public static final String HIBERNATE_CONNECTION_PASSWORD = "hibernate.connection.password";
    public static final String HIBERNATE_CONNECTION_DRIVER_CLASS = "hibernate.connection.driver_class";

    @SneakyThrows
    public ApplicationProperties() {
        //this.load(new FileReader(CLASSES_ROOT + "/application.properties"));
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            this.load(input);
        }
        injectEnvironmentVariables();
        try {
            String driver = this.getProperty(HIBERNATE_CONNECTION_DRIVER_CLASS);
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void injectEnvironmentVariables() {

        log.info("Environment size: {}", System.getenv().size());
        System.getenv().forEach((k, v) -> log.info("{} = {}", k, v));

        this.forEach((key, value) -> {
            String strKey = key.toString();
            String strValue = value.toString();
            if (strValue.startsWith("${") && strValue.endsWith("}")) {
                String[] valueParts = strValue
                        .replace("${","")
                        .replace("}","")
                        .split(":", 2);
                if (valueParts.length == 2) {
                    strValue = valueParts[1];
                }
                String keyEnvironmentVariable = valueParts[0];
                String valueEnvironmentVariable = System.getenv(keyEnvironmentVariable);
                if (keyEnvironmentVariable != null && valueEnvironmentVariable != null) {
                    strValue = valueEnvironmentVariable;
                }
                this.setProperty(strKey, strValue);
            }
        });
    }

    public final static Path CLASSES_ROOT = Paths.get(URI.create(
            Objects.requireNonNull(
                    ApplicationProperties.class.getResource("/")
            ).toString()));

    //only in Tomcat (not use in tests)
    public final static Path WEB_INF = CLASSES_ROOT.getParent();
}