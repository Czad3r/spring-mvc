package pl.kowalczuk.springmvc.domain.forms;

import pl.kowalczuk.springmvc.domain.annotations.PasswordMatches;

import javax.validation.constraints.*;

import static pl.kowalczuk.springmvc.domain.constants.FormsConstants.*;

@PasswordMatches
public class PasswordWithTokenForm {

    @NotEmpty
    private String token;

    @Size(min = 6, max = 30, message = "Pole musi mieć od 6 do 30 znaków!")
    private String password;

    @Size(min = 6, max = 30, message = "Pole musi mieć od 6 do 30 znaków!")
    private String password2;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
