# ---- Stage 1: build (has the full JDK, does the compiling) ----
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN ./mvnw package -DskipTests
# ---- Stage 2: run (fresh small JRE, gets only the JAR) ----
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/expense-tracker-api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]