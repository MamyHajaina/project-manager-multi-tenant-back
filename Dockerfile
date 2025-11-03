# ---------- build stage ----------
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copie du wrapper Gradle et des fichiers de build pour profiter du cache Docker
COPY gradlew ./gradlew
COPY gradle ./gradle
COPY build.gradle* settings.gradle* gradle.properties* ./

# Assure l’exécutabilité du wrapper
RUN chmod +x ./gradlew

# Pré-télécharge les dépendances (accélère les builds suivants)
RUN ./gradlew --no-daemon dependencies || true

# Copie du code source et build de l’uber-jar Spring Boot
COPY src ./src
# Utilise bootJar (équivalent du jar exécutable Spring Boot)
RUN ./gradlew --no-daemon clean bootJar -x test

# ---------- runtime stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Bonnes pratiques mémoire en conteneur
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"
# Render/Koyeb injectent une variable PORT (ex: 10000). On l’écoute.
ENV PORT=10000

# Copie du jar produit par Gradle
COPY --from=build /app/build/libs/*.jar app.jar

# Expose le port attendu par la plateforme (facultatif mais explicite)
EXPOSE 10000

# Lancement : on force Spring à écouter $PORT
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Dserver.port=${PORT} -jar app.jar"]
