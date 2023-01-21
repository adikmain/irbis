create schema if not exists irbis;
create extension if not exists "uuid-ossp";

create table irbis.news
(
    id          uuid primary key default uuid_generate_v4(),
    source      text,
    topic       text,
    newsContent text
);

insert into irbis.news(id, source, topic, newsContent)
values (default, 'irbis.plus', 'Помощь юр. лицам', 'Обновления законодательства в 2022 году.'),
       (default, 'irbis.plus', 'Помощь юр. лицам', 'Обновления законодательства в 2023 г.'),
       (default, 'irbis.plus', 'Помощь физ. лицам', 'Рассказываем о том, как обезопасить себя от мошенников'),
       (default, 'irbis.plus', 'О нас', 'Рассказываем о том, как отдыхают наши работники'),
       (default, 'irbis.plus', 'О нас', 'Знакомим с нашими клиентами. Часть 1'),
       (default, 'irbis.plus', 'О нас', 'Знакомим с нашими клиентами. Часть 2'),
       (default, 'praktika.irbis.plus', 'Обновления сервиса', 'Знакомство с сервисом'),
       (default, 'praktika.irbis.plus', 'Обновления сервиса', 'Нововведения во вкладке "Суды"');
