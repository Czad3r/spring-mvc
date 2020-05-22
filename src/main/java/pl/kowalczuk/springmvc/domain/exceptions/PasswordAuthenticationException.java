package pl.kowalczuk.springmvc.domain.exceptions;

import org.springframework.security.core.AuthenticationException;

public class PasswordAuthenticationException extends AuthenticationException {
    public PasswordAuthenticationException(String msg) {
        super(msg);
    }
}
