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
