package uz.nt.mediumclone.exeption;

import org.springframework.dao.InvalidDataAccessResourceUsageException;

import java.util.NoSuchElementException;

public class UserNotSavedException extends InvalidDataAccessResourceUsageException {
    public UserNotSavedException(String message) {
        super(message);
    }

}
