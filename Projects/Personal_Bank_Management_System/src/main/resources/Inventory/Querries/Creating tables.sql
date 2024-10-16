CREATE DATABASE PersonalBakingSystem;
DROP DATABASE PersonalBakingSystem;

USE PersonalBakingSystem;


CREATE TABLE UserProfile (
	UserID int AUTO_INCREMENT PRIMARY KEY,
    Username varchar(50),
    FirstName varchar(255) NOT NULL,
    LastName varchar(255) NOT NULL,
    DateOfBirth date NOT NULL,
    Gender varchar(50) NOT NULL,
    PhoneNumber varchar(9) NOT NULL, 
    Email varchar(50) NOT NULL,
    Password varchar(50) NOT NULL,
    Token varchar(50) NOT NULL,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
    
    CREATE TABLE Account(
		AccountID int AUTO_INCREMENT, 
		UserID INT NOT NULL,
		AccountType varchar(20) NOT NULL, 
		AccountNumber int NOT NULL, 
		Balance decimal(13, 2),
		CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
		PRIMARY KEY (AccountID),
		FOREIGN KEY (UserID) REFERENCES UserProfile(UserID)
	);
    
    CREATE TABLE Admin (
    Username varchar(50),
    Password varchar(50),
    PRIMARY KEY (Username));
    
CREATE TABLE Transaction (
    TransactionID int AUTO_INCREMENT PRIMARY KEY, 
    UserID int, 
    AccountID int,
    Beneficiary varchar(50) NOT NULL,
    MyReference varchar(50) NOT NULL,
    TheirReference varchar(50) NOT NULL,
    Email varchar(50),
    phoneNumber varchar(9) NOT NULL CHECK (phoneNumber REGEXP '^[0-9]{9}$'), 
    Amount decimal(18,2) NOT NULL CHECK (Amount >= 0),
    AccountNumber int NOT NULL CHECK (AccountNumber REGEXP '^[0-9]+$'),
    Status varchar(50),
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (UserID) REFERENCES UserProfile(UserID),
	CONSTRAINT fk_account FOREIGN KEY (AccountID) REFERENCES Account(AccountID),
    CONSTRAINT chk_email_format CHECK (Email REGEXP '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$')
);


    