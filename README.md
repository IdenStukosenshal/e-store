
# Абстрактный интернет-магазин с интерфейсом в виде REST

Для работы приложения нужно получить yandex geocoder api key

## Запуск:

* Собрать jar-file:
```
mvn package
```
или (без тестов)
```
mvn package -DskipTests
```
* Собрать docker-image:
```
docker build -t estore-image-name .
```
* Создать Docker-сеть для связи контейнеров:
```
docker network create estore-network-name
```
* Запустить в ней контейнер mysql  
  Пример:
```
docker run -d --name mysqlContainerName --network estore-network-name -e MYSQL_ROOT_PASSWORD=rootPassword -e MYSQL_DATABASE=dbName -e MYSQL_USER=dbUserName -e MYSQL_PASSWORD=password mysql
```
* запустить контейнер с приложением, указав переменные окружения:  
  Пример:
```
docker run -d --name e-store --network estore-network-name -p 8080:8080 -e DATASOURCE_URL=jdbc:mysql://mysqlContainerName:3306/dbName -e DATASOURCE_USERNAME=dbUserName -e DATASOURCE_PASSWORD=password -e API_KEY=YOUR_KEY estore-image-name
```

Проверить работу приложения через swagger-ui:
http://localhost:8080/swagger-ui.html

## Используемые технологии:
* Java 21
* Spring Boot
* Jpa
* QueryDSL
* MySQL
* OpenFeign
* JUnit 5
* Docker
* Git
* Swagger
* Flyway
