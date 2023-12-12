

sqlite3

.databases
.tables
.exit

 -- Show available databases
.databases

-- Switch to the medical_data_channel_DB database or create it if not exists
ATTACH DATABASE 'medical_data_channel_DB.db' AS medical_data_channel_DB;


-- Create the 'cards' table
CREATE TABLE IF NOT EXISTS medical_data_channel_DB.cards (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    patient_id TEXT NOT NULL,
    medical_data TEXT NOT NULL
);

-- Create the 'tags' table
CREATE TABLE IF NOT EXISTS medical_data_channel_DB.tags (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    patient_id TEXT NOT NULL,
    medical_data TEXT NOT NULL
);

-- Create the 'users' table
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    password TEXT NOT NULL
);

-- Create the 'users' table in the medical_data_channel_DB database
CREATE TABLE IF NOT EXISTS medical_data_channel_DB.users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    password TEXT NOT NULL
);


SELECT * FROM cards;

SELECT * FROM tags;

-- Retrieve all data from the 'cards' table
SELECT * FROM medical_data_channel_DB.cards;

-- Retrieve all data from the 'tags' table
SELECT * FROM medical_data_channel_DB.tags;

-- Exit SQLite
.exit
