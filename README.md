# Инструкция по запуску приложения
Эта инструкция описывает необходимые шаги для запуска CRUD-приложения для библиотеки книг на базе Spring Boot с использованием Hibernate и PostgreSQL.

## 1 Предварительные требования
Прежде чем начать, убедитесь, что у вас установлены следующие инструменты:

Java Development Kit (JDK) версии 11 или выше
PostgreSQL сервер
Maven (опционально, если вы будете собирать проект через Maven)
Шаги для запуска
## 2 Клонирование репозитория

Сначала склонируйте репозиторий на локальную машину:
```
git clone <URL репозитория>
cd <название репозитория>
```
Перейдите на ветку develop:
```
git checkout develop
```

## 3 Настройка базы данных

Установите PostgreSQL, если он не установлен.
Создайте базу данных с именем, указанным в конфигурации приложения (обычно это postgres).
Настройка приложения

В файле application.properties (или application.yml) в ресурсах проекта укажите следующие параметры:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/<имя_базы_данных>
spring.datasource.username=<имя_пользователя>
spring.datasource.password=<пароль>
```
## 4 Сборка проекта
Воспользуйтесь подсказкой Intellij Idea, она автоматически соберет проект Maven, либо:

Убедитесь, что Maven установлен и добавлен в переменную среды PATH.
Выполните сборку проекта с помощью Maven:

```
mvn clean package
```
Эта команда создаст JAR файл с вашим приложением в папке target.

## 5 Запуск приложения
Если воспользовались подсказкой, можете запустите приложение обычным способом через IDE. 
Иначе:

### Запустите приложение, используя Java:

```
java -jar target/<имя_вашего_jar_файла>.jar
```
Приложение будет запущено на порту 8090. Вы можете изменить порт, изменив настройки в application.properties.
### Запустите приложение с помощью Docker-compose
Прежде чем начать использовать приложение, убедитесь, что у вас установлены следующие компоненты:
Docker,
Docker Compose.

Для запуска приложения выполните следующие команды:

```
docker-compose up -d --build
```
Эта команда запустит приложение в фоновом режиме (-d) и пересоберет образы (--build), если необходимо.

Остановка приложения: Для остановки приложения выполните:

```
docker-compose down
```
Эта команда остановит и удалит контейнеры, созданные для вашего приложения.

## 6 Доступ к API

### Swagger
После успешного запуска приложения вы можете получить доступ к API через Swagger. Откройте браузер и перейдите по следующему URL:

```
http://localhost:8090/swagger-ui.html
```
Здесь вы сможете просматривать, тестировать и использовать все доступные API методы для управления книгами.
### Postman

Также вы можете воспользоваться коллекцией Postman, которую можно импортировать и использовать для тестирования и взаимодействия с API.

#### Шаги для импорта:

1. Скачайте файл коллекции (library/Postman_library.json).
2. Откройте Postman и импортируйте коллекцию:
   - Нажмите на кнопку "Import" в верхнем левом углу Postman.
   - Выберите файл, который вы только что скачали.
3. Теперь у вас доступны все запросы для тестирования API.





