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
    