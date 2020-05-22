package pl.kowalczuk.springmvc.domain.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static pl.kowalczuk.springmvc.domain.constants.FormsConstants.EMPTY_ERROR_MESSAGE;

public class LoginForm {

    @NotNull(message = EMPTY_ERROR_MESSAGE)
    @Size(min = 5, max = 20)
    private String username;

    @NotNull(message = EMPTY_ERROR_MESSAGE)
    @Size(min = 6, max = 30)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
