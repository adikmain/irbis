## Spring-boot приложение для отображения новостных данных.

#### Реализовано

1. REST-контроллер с пагинацией, со следующими путями:
    1. /sources
    2. /topics
    3. /news

2. Защита с помощью api-token'а. (Параметр в заголовке запроса - `X-Client-Api-Token`)

3. Статистическая выгрузка новостей раз в n минут, в виде csv файла.

4. Миграции для базы данных.

#### Установка

Для запуска сервиса необходимо:

1. Скопировать репозиторий `git clone https://github.com/adikmain/irbis.git`
2. В корневой папке проекта запустить `mvn clean install -DskipTests` для того, чтобы собрать проект.
3. Запустить `docker-compose-up`, чтобы поднять контейнеры.
4. (Опционально) `docker-compose down` чтобы остановить и удалить контейнеры.

#### Заметки

1. В application.yaml можно задать папку, в которую будет выгружаться статистика.
   (`statistics-folder: *название папки*`)

2. Так же в application.yaml можно задать api token сервиса (`client-api-token: *токен*`)