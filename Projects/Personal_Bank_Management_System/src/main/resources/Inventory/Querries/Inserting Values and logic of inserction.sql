USE PersonalBakingSystem;

INSERT INTO Admin VALUES
('snafer30san', 'Ss32566565');
 
 -- PROCESS OF ADDING USER TO DATABASE --
 -- 1. User Inserted
 INSERT INTO UserProfile  (FirstName, LastName, DateOfBirth, Gender, PhoneNumber, Email, Password, Token) VALUES 
('Luamy', 'Vicente', '2003-05-30', 'Male', '816548711', 'manuelvicentee30@gmail.com', '123456', '0000');

-- 2. Get UserID of the newly inserted User
SELECT UserID FROM UserProfile ORDER BY CreatedAt DESC LIMIT 1;
 
-- 3. Use the UserId Fetched to insert new row(s)
INSERT INTO Account (UserID, AccountType, AccountNumber) VALUES 
(1, 'Credit', 600000000);
-- Or If he has chosen two accounts
INSERT INTO Account (Username, AccountType, AccountNumber) VALUES 
('132', 'Credit', 600000000),
('025', 'Savings', 600000000);


-- PROCESS OF EDITING USER TO DATABASE --
-- 1. Fetch the UserID of user based on the Account Number 
SELECT UserID FROM Account WHERE AccountNumber = '600000004'; 

-- 2. Using the UserID you fetched get the details of the user 
SELECT FirstName, LastName,  Email, PhoneNumber, Gender, DateOfBirth FROM UserProfile WHERE UserID = '1';
SELECT AccountType FROM Account WHERE UserID = 3;

-- 3. Update the information of the user
UPDATE UserProfile SET FirstName = 'Pai', LastName = 'Evaristo', Email = 'yakelui2000@hotmail.com', PhoneNumber = '935951538', Gender = 'Male', DateOfBirth = '1960-05-10' WHERE UserID = 2;

-- DEPOSITING MONEY TO USER'S ACCOUNT --
-- 1. Fetch the required Details of user based on the Account Number 
SELECT FirstName, LastName, Balance FROM Account INNER JOIN UserProfile ON Account.UserID = UserProfile.UserID WHERE AccountNumber = '600000000' AND AccountType = 'Credit';

-- 2. Update amount
UPDATE Account SET Balance = '100' WHERE AccountNumber = '600000000' AND AccountType = 'Credit';
