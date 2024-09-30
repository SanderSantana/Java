USE PersonalBakingSystem;

INSERT INTO Admin VALUES
('snafer30san', 'Ss32566565');
 
 -- PROCESS OF ADDING USER TO DATABASE --
 INSERT INTO UserProfile VALUES 
('0000', 'San5der', 'S4antana', '2003-05-30', 'Male', 085516548711, 'manuelvicentee30@gmail', '123456', '0000');
 
 -- Only one row if he only chose one account
INSERT INTO Account (Username, AccountType, AccountNumber) VALUES 
('SanderSantana', 'Credit', 111094823);
-- If he chose Two
INSERT INTO Account (Username, AccountType, AccountNumber) VALUES 
('SanderSantana', 'Credit', 111094823),
('SanderSantana', 'Savings', 111094824);

    
    
--     Check if Admin Exists
SELECT COUNT(1) FROM Admin WHERE Username = 'snafer30san' AND Password = 'Ss32566565';

    


-- GET BALACEND BASED ON ACCOUNT NUMBER AND TYPE OF ACCOUNT
SELECT UserProfile.FirstName, UserProfile.LastName, Account.Balance 
FROM UserProfile 
INNER JOIN Account ON UserProfile.Username = Account.Username
WHERE AccountNumber = '600094824' AND AccountType = 'Savings'; 

-- Deposit Amount
UPDATE Account
SET Balance = 1000
WHERE AccountNumber = 600094824  AND AccountType = 'Savings';


ALTER TABLE Account
ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

SELECT AccountNumber
FROM Account
ORDER BY created_at DESC
LIMIT 1;

SELECT FirstName, LastName, DateOfBirth, Gender, PhoneNumber, Email, Account.AccountType
FROM UserProfile 
INNER JOIN Account ON Account.Username = UserProfile.Username
WHERE AccountNumber = '111094827'


-- UPDATE USER PROFILE
    -- Check if user exists
    SELECT COUNT(1) FROM UserProfile WHERE Username = 'snafer30san' AND Password = '123456'
        
    
    SELECT firstName, 
    