package pl.kowalczuk.springmvc.domain.exceptions;

public class UsernameAlreadyExistException extends Exception {
    public UsernameAlreadyExistException(String message) {
        super(message);
    }
}
