CREATE DATABASE PersonalBakingSystem;

USE PersonalBakingSystem;


CREATE TABLE UserProfile (
    Username varchar(50) PRIMARY KEY,
    FirstName varchar(255),
    LastName varchar(255),
    DateOfBirth date,
    Gender varchar(50),
    PhoneNumber varchar(9), -- Changed to varchar to apply regex constraint
    Email varchar(50),
    Password varchar(50),
    Token varchar(50),
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DELIMITER $$

CREATE TRIGGER ValidateUserProfile
BEFORE INSERT ON UserProfile
FOR EACH ROW
BEGIN
    -- Validate FirstName contains only letters
    IF NEW.FirstName NOT REGEXP '^[A-Za-z]+$' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'FirstName can only contain letters';
    END IF;

    -- Validate LastName contains only letters
    IF NEW.LastName NOT REGEXP '^[A-Za-z]+$' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'LastName can only contain letters';
    END IF;

    -- Validate PhoneNumber is exactly 9 digits
    IF NEW.PhoneNumber NOT REGEXP '^[0-9]{9}$' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'PhoneNumber must be exactly 9 digits';
    END IF;

    -- Validate Email format
    IF NEW.Email NOT REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid Email format';
    END IF;
END$$

DELIMITER ;


    
    CREATE TABLE Account(
    AccountID int auto_increment, 
		Username varchar(50),
		AccountType varchar(20),
		AccountNumber int, 
		Balance decimal(13, 2),
		CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
		PRIMARY KEY (AccountID),
		FOREIGN KEY (Username)
		REFERENCES UserProfile(Username)
	);
    
    CREATE TABLE Admin (
    Username varchar(50),
    Password varchar(50),
    PRIMARY KEY (Username));
    
    CREATE TABLE Transaction (
    TransactionID int AUTO_INCREMENT, 
    Username varchar(50), 
    AccountID int,
    Beneficiary varchar(50),
    MyReference varchar(50),
    TheirReference varchar(50),
    Email varchar(50),
    phoneNumber int, 
    Amount int, 
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ); 	
    