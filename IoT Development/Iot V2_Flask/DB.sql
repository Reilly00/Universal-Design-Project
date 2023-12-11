

sqlite3

.databases
.tables
.exit



ATTACH DATABASE 'medical_data_channel_DB.db' AS medical_data_channel_DB;

CREATE TABLE IF NOT EXISTS medical_data_channel_DB.cards (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    patient_id TEXT NOT NULL,
    medical_data TEXT NOT NULL
);


CREATE TABLE IF NOT EXISTS medical_data_channel_DB.tags (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    patient_id TEXT NOT NULL,
    medical_data TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS medical_data_channel_DB.users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    password TEXT NOT NULL
);

SELECT * FROM cards;

SELECT * FROM tags;

