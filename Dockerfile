FROM amazoncorretto:17.0.11-alpine

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN apk add --no-cache maven

RUN mvn package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/assembleia-1.0.0.jar"]