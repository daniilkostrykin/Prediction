-- Создание пользователей
INSERT INTO prediction_schema.users (id, username, email, password_hash, role, successful_predictions, created_at) VALUES
(1, 'admin', 'admin@example.com', '$2a$10$NzQ1NjQ1NupBcJvDwEtN6s4i8F9i8', 'ADMIN', 0, CURRENT_TIMESTAMP),
(2, 'john_doe', 'john@example.com', '$2a$10$NzQ1NjQ1NupBcJvDwEtN6s4i8F9i8', 'USER', 0, CURRENT_TIMESTAMP),
(3, 'jane_smith', 'jane@example.com', '$2a$10$NzQ1NjQ1NupBcJvDwEtN6s4i8F9i8', 'USER', 0, CURRENT_TIMESTAMP);

-- Создание событий
INSERT INTO prediction_schema.events (id, title, description, status, outcome, closes_at, created_at, updated_at) VALUES
(1, 'Финал Чемпионата Мира по Футболу', 'Матч за звание чемпиона мира между двумя сильнейшими командами', 'ACTIVE', 'UNDEFINED', CURRENT_TIMESTAMP + INTERVAL '7 days', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Финал NBA', 'Решающий матч финальной серии плей-офф НБА', 'ACTIVE', 'UNDEFINED', CURRENT_TIMESTAMP + INTERVAL '14 days', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Теннисный турнир ATP', 'Финал крупного теннисного турнира ATP', 'ACTIVE', 'UNDEFINED', CURRENT_TIMESTAMP + INTERVAL '10 days', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Создание предсказаний
INSERT INTO prediction_schema.predictions (id, user_id, event_id, prediction, status, created_at) VALUES
(1, 2, 1, 'YES', 'PLACED', CURRENT_TIMESTAMP),
(2, 3, 1, 'NO', 'PLACED', CURRENT_TIMESTAMP),
(3, 2, 2, 'YES', 'PLACED', CURRENT_TIMESTAMP);

-- Создание активностей предсказаний
INSERT INTO prediction_schema.prediction_activities (id, user_id, prediction_id, type, created_at) VALUES
(1, 2, 1, 'PREDICTION_PLACED', CURRENT_TIMESTAMP),
(2, 3, 2, 'PREDICTION_PLACED', CURRENT_TIMESTAMP),
(3, 2, 3, 'PREDICTION_PLACED', CURRENT_TIMESTAMP);