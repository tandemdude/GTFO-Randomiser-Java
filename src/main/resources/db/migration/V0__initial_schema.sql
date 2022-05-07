CREATE SCHEMA IF NOT EXISTS randomiser;

CREATE TABLE randomiser.users (
    id BIGINT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    discriminator VARCHAR(4) NOT NULL
);

CREATE TABLE randomiser.dailies (
    id VARCHAR(10) PRIMARY KEY,
    loadout TEXT NOT NULL,
    stage VARCHAR(255) NOT NULL
);

CREATE TABLE randomiser.daily_submissions (
    id BIGSERIAL PRIMARY KEY,
    time INTEGER NOT NULL,
    evidence_url TEXT NOT NULL,
    verified BOOLEAN NOT NULL DEFAULT FALSE,
    submitted_for_id VARCHAR(10) NOT NULL REFERENCES randomiser.dailies(id) ON DELETE CASCADE,
    submitted_by_id BIGINT NOT NULL REFERENCES randomiser.users(id) ON DELETE CASCADE
);
