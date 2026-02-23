-- Drop tables if already exist


-- ==============================
-- USERS TABLE
-- ==============================
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER',  -- USER / ADMIN
    status VARCHAR(20) DEFAULT 'ACTIVE', -- ACTIVE / BLOCKED
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==============================
-- ACCOUNTS TABLE
-- ==============================
CREATE TABLE IF NOT EXISTS accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    user_id INT NOT NULL,
    balance DECIMAL(15,2) DEFAULT 0.00,
    status VARCHAR(20) DEFAULT 'ACTIVE', -- ACTIVE / FROZEN
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE
);

-- ==============================
-- TRANSACTIONS TABLE
-- ==============================
CREATE TABLE IF NOT EXISTS transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    from_account VARCHAR(20),
    to_account VARCHAR(20),
    amount DECIMAL(15,2) NOT NULL,
    transaction_type VARCHAR(20) NOT NULL, -- DEPOSIT / WITHDRAW / TRANSFER
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_from_account
    FOREIGN KEY (from_account) REFERENCES accounts(account_number)
    ON DELETE SET NULL,
    
    CONSTRAINT fk_to_account
    FOREIGN KEY (to_account) REFERENCES accounts(account_number)
    ON DELETE SET NULL
);

-- ==============================
-- DEFAULT ADMIN USER
-- password: admin123  (hash করে রাখতে পারো)
-- ==============================

