# Используем официальный образ Maven с JDK 17
FROM maven:3.8.7-eclipse-temurin-18 AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем pom.xml и загружаем зависимости
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Копируем исходный код проекта
COPY src ./src

# Собираем проект, пропуская тесты
RUN mvn package -DskipTests

# Второй этап: создаем финальный образ с JRE 17
FROM eclipse-temurin:17-jdk-jammy

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем jar-файл из предыдущего этапа
COPY --from=build /app/target/library-collection-0.0.1-SNAPSHOT.jar app.jar

# Устанавливаем переменную окружения для профиля Spring
ENV SPRING_PROFILES_ACTIVE=local

# Открываем порт
EXPOSE 8090

# Используем базовый образ, подходящий для работы с PostgreSQL
FROM postgres:latest

# Копируем backup.sql из локальной директории в контейнер
COPY backup.sql /docker-entrypoint-initdb.d/
# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
