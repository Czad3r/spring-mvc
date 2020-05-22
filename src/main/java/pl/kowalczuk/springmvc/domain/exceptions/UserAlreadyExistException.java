package pl.kowalczuk.springmvc.domain.exceptions;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
