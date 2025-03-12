FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY .mvn ./.mvn
COPY mvnw mnvw
RUN ./mvnw clean package -DskipTests=true

FROM tomcat:10.1.36-jre21-temurin
COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/ROOT.war
ENV JAVA_OPTS="-Xms256m -Xmx320m -XX:+UseContainerSupport"
EXPOSE 8080