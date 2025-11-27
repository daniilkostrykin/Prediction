-- 1. Удаляем старое
DROP TABLE IF EXISTS prediction_schema.prediction_activities CASCADE;
DROP TABLE IF EXISTS prediction_schema.predictions CASCADE;
DROP TABLE IF EXISTS prediction_schema.event_options CASCADE;
DROP TABLE IF EXISTS prediction_schema.events CASCADE;
DROP TABLE IF EXISTS prediction_schema.users CASCADE;

-- 2. Создаем таблицы (СТРОГО КАК В JAVA ENTITY)

CREATE TABLE prediction_schema.users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL, -- В Java поле называется password, но мапится на password_hash (проверь @Column в User.java, если там нет name=..., то просто password)
    email VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(50) NOT NULL,
    successful_predictions INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE prediction_schema.events (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,        -- Было name, стало title
    description TEXT,
    status VARCHAR(50) NOT NULL,
    closes_at TIMESTAMP NOT NULL,       -- Было start_date/end_date, стало closes_at
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE prediction_schema.event_options (
    id SERIAL PRIMARY KEY,
    event_id INTEGER REFERENCES prediction_schema.events(id),
    text VARCHAR(255) NOT NULL,         -- Было option_name, стало text
    is_correct_outcome BOOLEAN DEFAULT FALSE -- Было odd, стало поле правильного ответа
);

CREATE TABLE prediction_schema.predictions (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES prediction_schema.users(id),
    event_id INTEGER REFERENCES prediction_schema.events(id),
    chosen_option_id INTEGER REFERENCES prediction_schema.event_options(id),
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Вставка данных

-- Юзеры (пароль 'password')
INSERT INTO prediction_schema.users (username, password_hash, email, role, successful_predictions) VALUES
('admin', '12345', 'admin@example.com', 'ADMIN', 0),
('user1', '12345', 'user1@example.com', 'USER', 0);

-- События
INSERT INTO prediction_schema.events (title, description, status, closes_at) VALUES
('Финал ЛЧ: Реал - Боруссия', 'Кто победит в главном матче года?', 'ACTIVE', '2025-06-01 22:00:00'),
('Выборы в США', 'Кто станет следующим президентом?', 'ACTIVE', '2025-11-05 00:00:00'),
('The International 2025', 'Победитель турнира', 'CLOSED', '2025-08-20 18:00:00');

-- Опции (Варианты)
-- Для события 1 (Футбол)
INSERT INTO prediction_schema.event_options (event_id, text) VALUES
(1, 'Реал Мадрид'),
(1, 'Боруссия Дортмунд');

-- Для события 2 (Выборы)
INSERT INTO prediction_schema.event_options (event_id, text) VALUES
(2, 'Кандидат А'),
(2, 'Кандидат Б'),
(2, 'Кандидат В');

-- Для события 3 (Дота)
INSERT INTO prediction_schema.event_options (event_id, text) VALUES
(3, 'Team Spirit'),
(3, 'Gaimin Gladiators');

-- Ставки (Predictions)
INSERT INTO prediction_schema.predictions (user_id, event_id, chosen_option_id, status) VALUES
(2, 1, 1, 'PLACED'); -- user1 поставил на Реал