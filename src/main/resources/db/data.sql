-- Create tables for the application
CREATE TABLE IF NOT EXISTS prediction_schema.users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(25) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    role VARCHAR(50) DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS prediction_schema.events (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    status VARCHAR(50) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS prediction_schema.event_options (
    id SERIAL PRIMARY KEY,
    event_id INTEGER REFERENCES prediction_schema.events(id),
    option_name VARCHAR(255) NOT NULL,
    odd DECIMAL(10, 2) DEFAULT 1.00
);

CREATE TABLE IF NOT EXISTS prediction_schema.predictions (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES prediction_schema.users(id),
    event_id INTEGER REFERENCES prediction_schema.events(id),
    event_option_id INTEGER REFERENCES prediction_schema.event_options(id),
    amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS prediction_schema.prediction_activities (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES prediction_schema.users(id),
    prediction_id INTEGER REFERENCES prediction_schema.predictions(id),
    activity_type VARCHAR(50) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample data
INSERT INTO prediction_schema.users (username, password, email, role) VALUES
('admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'admin@example.com', 'ADMIN'),
('user1', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'user1@example.com', 'USER'),
('user2', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'user2@example.com', 'USER');

INSERT INTO prediction_schema.events (name, description, start_date, end_date, status) VALUES
('Football Match', 'Sample football match between Team A and Team B', '2025-12-01 15:00', '2025-12-01 17:00:00', 'ACTIVE'),
('Basketball Game', 'Sample basketball game between Lakers and Celtics', '2025-12-02 19:00:00', '2025-12-02 21:00:00', 'ACTIVE'),
('Tennis Tournament', 'Sample tennis match between two players', '2025-12-03 14:00:00', '2025-12-03 16:00:00', 'SCHEDULED');

INSERT INTO prediction_schema.event_options (event_id, option_name, odd) VALUES
(1, 'Team A wins', 1.80),
(1, 'Team B wins', 2.10),
(1, 'Draw', 3.20),
(2, 'Lakers win', 1.65),
(2, 'Celtics win', 2.25),
(3, 'Player 1 wins', 1.75),
(3, 'Player 2 wins', 2.05);

INSERT INTO prediction_schema.predictions (user_id, event_id, event_option_id, amount, status) VALUES
(2, 1, 1, 50.00, 'PENDING'),
(3, 1, 2, 30.00, 'PENDING'),
(2, 2, 4, 75.00, 'PENDING');