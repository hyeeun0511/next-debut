FROM eclipse-temurin:21

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew clean bootWar -x test --no-daemon

EXPOSE 8080

CMD ["java", "-jar", "build/libs/projectx-0.0.1-SNAPSHOT.war"]