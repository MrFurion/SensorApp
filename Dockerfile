# Первый этап: сборка приложения с пропуском тестов
FROM maven:3.8.3-openjdk-17 AS builder

WORKDIR /build

# Копируем файлы mvnw и pom.xml и загружаем зависимости
COPY mvnw pom.xml ./
COPY .mvn/ .mvn
RUN ./mvnw dependency:go-offline

# Копируем исходный код и выполняем сборку приложения с пропуском тестов
COPY ./src ./src
RUN ./mvnw clean install -DskipTests

# Второй этап: создание образа для выполнения приложения
FROM eclipse-temurin:17-jre-alpine

WORKDIR /opt/app

# Копируем собранный JAR файл из этапа сборки
COPY --from=builder /build/target/*.jar /opt/app/app.jar

# Открываем порт 8080
EXPOSE 8080

# Команда для запуска Java приложения
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]
