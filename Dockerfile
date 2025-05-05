FROM openjdk:23
COPY build/libs/discord-bot.jar /app/discord-bot.jar
COPY .env /app/.env
WORKDIR /app
LABEL authors="frily"

CMD ["java", "-jar", "discord-bot.jar"]

