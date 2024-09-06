FROM openjdk:17

COPY ImpulsArt_Java/ImpulsArtApp/target/ImpulsArtApp-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8086
ENTRYPOINT ["java", "-jar", "/app.jar"]