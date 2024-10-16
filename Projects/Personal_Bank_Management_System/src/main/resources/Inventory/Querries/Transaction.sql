USE PersonalBakingSystem;

SELECT * FROM Transaction;

Truncate Transaction;

SELECT Transaction.Beneficiary, Account.AccountNumber, Transaction.Amount, Transaction.Status, Transaction.MyReference, Transaction.TheirReference, Transaction.CreatedAt
FROM Transaction
JOIN Account ON Transaction.AccountID = Account.AccountID
JOIN UserProfile ON Transaction.UserID = UserProfile.UserID
WHERE UserProfile.Username = 'DaVicent';


