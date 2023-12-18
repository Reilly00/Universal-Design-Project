CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_login DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE user_details (
    user_detail_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    phone_number VARCHAR(255),
    address VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS cards (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    patient_id TEXT NOT NULL,
    medical_data TEXT NOT NULL
);

INSERT INTO users (username, email, password_hash) VALUES ('testuser', 'testuser@example.com', 'testpassword');

ALTER TABLE users ADD COLUMN profile_pic_url VARCHAR(255);
UPDATE users SET profile_pic_url = 'https://tr.rbxcdn.com/38c6edcb50633730ff4cf39ac8859840/420/420/Hat/Png' WHERE user_id = 2;
UPDATE users SET profile_pic_url = 'https://www.trustontap.com/wp-content/uploads/2020/04/helen-de-hartog-trustontap.jpg' WHERE user_id = 4;

ALTER TABLE cards
ADD COLUMN user_id INT,
ADD FOREIGN KEY (user_id) REFERENCES users(user_id);

UPDATE cards
SET user_id = 4
WHERE id = 1;