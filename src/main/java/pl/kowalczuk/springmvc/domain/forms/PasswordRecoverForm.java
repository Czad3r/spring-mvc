package pl.kowalczuk.springmvc.domain.forms;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import static pl.kowalczuk.springmvc.domain.constants.FormsConstants.EMPTY_ERROR_MESSAGE;

public class PasswordRecoverForm {

    @NotEmpty(message = EMPTY_ERROR_MESSAGE)
    @Email
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
