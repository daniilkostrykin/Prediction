
-- События (10 карточек)
INSERT INTO prediction_schema.events (title, description, status, closes_at) VALUES
('Оскар 2026: Лучший фильм', 'Какая картина заберет главную статуэтку?', 'ACTIVE', '2026-03-10 03:00:00'),
('Курс Bitcoin к Новому году', 'Преодолеет ли Bitcoin отметку в $100,000 к 31 декабря?', 'ACTIVE', '2025-12-31 23:59:00'),
('Игра года (TGA 2025)', 'Кто получит титул Game of the Year?', 'ACTIVE', '2025-12-12 04:00:00'),
('Погода: Первый снег в Москве', 'Выпадет ли устойчивый снег до 1 ноября?', 'ACTIVE', '2025-11-01 00:00:00'),
('SpaceX: Полет на Марс', 'Состоится ли успешная беспилотная посадка Starship на Марс в 2026?', 'ACTIVE', '2026-12-31 23:59:00'),
('Релиз GTA VI', 'В каком квартале выйдет игра?', 'ACTIVE', '2026-11-19 00:00:00'),
('Евровидение 2025', 'Страна-победитель музыкального конкурса', 'CLOSED', '2025-05-15 22:00:00'),
('Box Office: Лидер лета', 'Какой фильм соберет больше всего денег в прокате?', 'ACTIVE', '2025-09-01 00:00:00'),
('Apple iPhone Fold', 'Представят ли складной iPhone (Fold) в сентябре 2026?', 'ACTIVE', '2026-09-30 20:00:00'),
('Человек года TIME', 'Кого журнал TIME назовет человеком года: Человек или ИИ?', 'ACTIVE', '2025-12-10 12:00:00');

-- Опции

-- 1. Оскар
INSERT INTO prediction_schema.event_options (event_id, text) VALUES
(1, '«Дюна: Часть 3»'),
(1, 'Новый фильм Тарантино'),
(1, '«Аватар 3»');

-- 2. Биткоин
INSERT INTO prediction_schema.event_options (event_id, text) VALUES
(2, 'Да, будет выше $100k'),
(2, 'Нет, будет ниже $100k');

-- 3. Игра года
INSERT INTO prediction_schema.event_options (event_id, text) VALUES
(3, 'Death Stranding 2'),
(3, 'GTA VI'),
(3, 'Hollow Knight: Silksong');

-- 4. Погода
INSERT INTO prediction_schema.event_options (event_id, text) VALUES
(4, 'Да, выпадет'),
(4, 'Нет, будет позже');

-- 5. SpaceX
INSERT INTO prediction_schema.event_options (event_id, text) VALUES
(5, 'Успешная посадка'),
(5, 'Авария / Перенос');

-- 6. GTA VI
INSERT INTO prediction_schema.event_options (event_id, text) VALUES
(6, 'Q4 2026'),
(6, 'Перенос на 2027');

-- 7. Евровидение (закрыто)
INSERT INTO prediction_schema.event_options (event_id, text, is_correct_outcome) VALUES
(7, 'Швейцария', TRUE),
(7, 'Хорватия', FALSE),
(7, 'Франция', FALSE);

-- 8. Box Office
INSERT INTO prediction_schema.event_options (event_id, text) VALUES
(8, 'Миссия невыполнима 8'),
(8, 'Супермен: Наследие'),
(8, 'Фантастическая четвёрка');

-- 9. iPhone Fold
INSERT INTO prediction_schema.event_options (event_id, text) VALUES
(9, 'Да, покажут iPhone Fold'),
(9, 'Нет, только обычный iPhone 17/18');

-- 10. TIME
INSERT INTO prediction_schema.event_options (event_id, text) VALUES
(10, 'Конкретная персона (человек)'),
(10, 'Искусственный интеллект (технология)'),
(10, 'Собирательный образ (группа людей)');

