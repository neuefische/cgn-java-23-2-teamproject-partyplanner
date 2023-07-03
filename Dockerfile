FROM openjdk:20
ENV ENVIRONMENT=prod
LABEL maintainer="partyplanner@partypets.org"
EXPOSE 8080
ADD backend/target/pawpalaceparties.jar app.jar
CMD [ "sh", "-c", "java -jar /app.jar" ]