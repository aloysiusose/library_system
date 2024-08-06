CREATE TABLE IF NOT EXISTS books(
        id INT PRIMARY KEY AUTO_INCREMENT,
        title VARCHAR(255),
        author VARCHAR(255),
        published_year VARCHAR(10),
        isbn VARCHAR(255),
        copies_available INTEGER
);

CREATE TABLE IF NOT EXISTS users(
        id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(255),
        email VARCHAR(255),
        membership_date DATE
);

CREATE TABLE IF NOT EXISTS book_loans(
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        loan_date DATE,
        return_date DATE,
        user_id INT,
        book_id INT,
        FOREIGN KEY (user_id) REFERENCES users(id),
        FOREIGN KEY (book_id) REFERENCES books(id)
);