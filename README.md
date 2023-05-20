Solva Test Project
====
Микросервис на основе Spring REST API. 
Данный сервис способен принимать информацию о каждой 
расходной операции в тенге (KZT) или рублях (RUB) в реальном времени и 
сохранять ее в своей собственной базе данных (БД). Кроме того, сервис 
позволяет назначать новый месячный лимит по расходам в долларах США (USD)
и хранить его в базе данных (Если лимит не установлен, он принимается равным 0).<br>
<br>

Инструмены, используемые в данном проекте:<br>
Spring Boot, Spring Web, Lombok, Swagger 3.0, FlyWay, Spring Data JPA.<br>
База данных: PostgreSQL



Чтобы подключить проект, необходимо:<br>
1. Перейти в [файле настроек](src/main/resources/application.properties) и подключить базу данных.<br>
   spring.datasource.driver-class-name=org.postgresql.Driver<br>
   spring.datasource.url=jdbc:postgresql://localhost:5432/solva<br>
   spring.datasource.username=USERNAME<br>
   spring.datasource.password=PASSWORD<br>
2. В файле настроек указать API KEY для сервиса twelvedata. <br>
   twelvedata.apikey=abababab123123<br>
   Данный ключ можно бесплатно получить для пакета Basic на сайте [https://twelvedata.com](https://twelvedata.com) <br>

Миграция базы данных осуществляется автоматически, с помощью инструмента миграции баз данных FlyWay. Скрипты миграции рассположены в [папке](src/main/resources/db/migration). <br><br>
Получение текущего курса валют осуществляется 1 раз в сутки. Данная функция реализованна в классе [CurrencyRateUpdater.java](src/main/java/com/roland/solva/rateupdater/CurrencyRateUpdater.java).<br>
Документацию по API можно найти по адресу: [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)


Тестирование
----
Для того чтобы запустить тесты, необходимо создать новую базу данных для тестов.
Параметры этой новой базы данных указать в [файле](src/test/resources/application_test.properties).
Далее запустить тесты, FlyWay и Spring добавят таблицы и сущности самостоятельно.
