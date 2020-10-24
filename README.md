# IssRepresenter
 
Сервис для обработки выгрузок Московской биржи:
* информация о ценных бумагах (securities_*.xml)
* история торгов за произвольную дату (history_*.xml)
 
 Не гарантируется работоспособность с неподходящими форматами входных данных
 

## Используемые технологии

* Scala 2.13.3
* Play2
* MySQL 
* slick
* scalaj
* Docker


## Функционал

* Импорт файлов из XML-файлов. В случае существования истории без описания
ценной бумаги выполняется запрос к API биржи.
* Хранение объектов в базе данных MySQL 
* CRUD операции для объектов
    - Единственное исключение - нет возможности вручную ввести историю торгов, только импортом. 
* Возможность сортировки при клике на заголовок таблицы
* Общая таблица (ценные бумаги и история торгов вместе) с данными из заявленных тегов
* Возможность поиска по всем полям (для главной таблицы)
* Возможность поиска по emitent_title и trade_date для общей таблицы
* Возможность очистить историю для какой-либо ценной бумаги
* Функционирующий фронтенд

## Запуск


Запуск приложения с использованием Docker:

 * Клонировать репозиторий `git clone` 
 * Прописать url MySQL-сервера, имя пользователя и пароль в `conf/application.conf`
 * Создать базу данных `stockDb` на MySQL сервере.

```mysql
    CREATE DATABASE stockDb;
```

 * Создать образ приложения `docker build -t myapp .`
 * Запустить контейнер `docker run --rm --name app -p 9000:9000 myapp`
 * Зайти на <http://localhost:9000>
 * Принять предложение и применить скрипт для эволюции базы данных
 * Приложение должно заработать