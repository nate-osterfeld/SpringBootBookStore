# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
# Copy the pom and source code
COPY pom.xml .
COPY src ./src
# Build the jar file (everything in src/main/resources gets baked into the JAR here)
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jre
WORKDIR /app
# Copy only the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# We removed the manual COPY of the JSON file because it's now inside the app.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "app.jar"]