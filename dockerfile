FROM eclipse-temurin:21 AS builder
WORKDIR /marcus-barber

COPY pom.xml .
COPY .mvn .mvn/
COPY mvnw .

RUN ./mvnw dependency:go-offline

COPY src src/

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21
WORKDIR /app

COPY --from=builder /marcus-barber/target/Marcus-Barber-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
