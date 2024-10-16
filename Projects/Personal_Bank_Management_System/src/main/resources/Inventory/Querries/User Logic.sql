USE PersonalBakingSystem;

-- REGISTER USER -- 
-- 1. Update User info based on Token
UPDATE UserProfile SET Username = 'DaVicent', Password = '123456' WHERE Token = '0000';
-- 2. Check of User is already registred
SELECT Username FROM UserProfile WHERE Token = '0000';
-- 3. Check if Username is unique
SELECT UserID FROM UserProfile WHERE Username = 'DaVicent';

-- VALIDATE LOGIN -- 
SELECT COUNT(1) FROM UserProfile WHERE Username = 'DaVicent' AND Password = '123456';

-- FETCHING INFORMATION FOR USER DASHBOARD -- 
-- 1. 
SELECT FirstName, AccountNumber, AccountType, Balance
FROM UserProfile 
INNER JOIN Account 
ON UserProfile.UserID = Account.UserID
WHERE Username = 'snafer30san';

-- FETCHING INFORMATION 
-- 1. Select required Details using username
SELECT FirstName, LastName, Email, PhoneNumber FROM UserProfile WHERE Username = 'snafer30san';

-- Getting Details for Dashboard 
SELECT AccountType FROM Account WHERE UserID = '1';
SELECT FirstName, AccountNumber, Balance, AccountType FROM UserProfile INNER JOIN Account ON UserProfile.UserID = Account.UserID WHERE Username = 'manuel30';

-- VALIDATING TRANSACTION --
-- 1. Fetch user ID using Username
SELECT UserID FROM UserProfile WHERE Username = 'DaVicent'; 
-- 2. Fetch AccountID using AccountType and UserID
SELECT AccountID FROM Account WHERE UserID = '3' AND AccountType = 'Credit'; 
-- 3. Validate Account Number
SELECT COUNT(1) FROM Account WHERE AccountNumber = '600000000';

INSERT INTO Transaction (UserID, AccountID, Beneficiary, MyReference, TheirReference, Email, PhoneNumber, Amount, AccountNumber, Status)
VALUES (1, 1, 'Jane Smith', 'Ref123', 'TheirRef123', 'jane.smith@example.com', 987654321, 500, 123456789, 'Completed');

-- GETTING DETAILS NEEDED FOR TABLE VIEW OF ALL USERS --
SELECT Username, FirstName, LastName, Email, DateOfBirth, Gender, PhoneNumber, AccountType, AccountNumber, Balance FROM UserProfile INNER JOIN Account ON Account.UserID = UserProfile.UserID
;
DELETE FROM UserProfile WHERE UserID = ''; 
DELETE FROM Account WHERE UserID = '';
DELETE FROM Transaction WHERE UserID = '';