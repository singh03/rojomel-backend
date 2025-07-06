1.
create database rojomel_db;
use rojomel_db;

2. -- Create the customer table
CREATE TABLE customer (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255),
                          email VARCHAR(255),
                          isDeleted BOOLEAN DEFAULT FALSE,
                          dateCreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          dateUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

3. -- Create the finance_entry table
CREATE TABLE financeEntry (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               expense DOUBLE,
                               income DOUBLE,
                               dateCreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               dateUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               userId BIGINT,
                               isDeleted BOOLEAN DEFAULT FALSE,

                               CONSTRAINT fk_finance_entry_customer FOREIGN KEY (userId)
                                   REFERENCES customer(id)
);

4. --Add balance field in financeEntry table
ALTER TABLE finance_entry ADD COLUMN outstanding_balance DOUBLE NOT NULL DEFAULT 0;
