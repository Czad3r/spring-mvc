package pl.kowalczuk.springmvc.domain.forms;

import pl.kowalczuk.springmvc.domain.annotations.PasswordMatches;

import javax.validation.constraints.*;

import static pl.kowalczuk.springmvc.domain.constants.FormsConstants.*;

@PasswordMatches
public class RegisterForm {

    @Size(min = 5, max = 30, message = FIELD_5_30_ERROR_MESSAGE)
    private String username;

    @NotEmpty(message = EMPTY_ERROR_MESSAGE)
    @Email
    private String email;

    @Size(min = 6, max = 30, message = FIELD_6_30_ERROR_MESSAGE)
    private String password;

    @Size(min = 6, max = 30, message = FIELD_6_30_ERROR_MESSAGE)
    private String password2;

    @Pattern(regexp = "^[0-9]{9}$", message = EMPTY_OR_INVALID_FORMAT_ERROR_MESSAGE)
    private String phone;

    @Pattern(regexp = "^[0-9]{2,5}(-[0-9]{3})?( [A-Z]{2})?$" ,message = EMPTY_OR_INVALID_FORMAT_ERROR_MESSAGE)
    private String postalCode;

    @NotEmpty(message = EMPTY_ERROR_MESSAGE)
    private String country;

    @NotEmpty(message = EMPTY_ERROR_MESSAGE)
    @Pattern(regexp = ALPHANUMERIC_POLISH_MIN_0_REGEX, message = INVALID_FORMAT_ERROR_MESSAGE)
    private String street;

    @NotEmpty(message = EMPTY_ERROR_MESSAGE)
    private String streetNo;

    private String streetNo2;

    @NotEmpty(message = EMPTY_ERROR_MESSAGE)
    @Pattern(regexp = ALPHANUMERIC_POLISH_MIN_0_REGEX, message = INVALID_FORMAT_ERROR_MESSAGE)
    private String city;

    @NotNull(message = EMPTY_ERROR_MESSAGE)
    private String gender;

    public String getStreetNo2() {
        return streetNo2;
    }

    public void setStreetNo2(String streetNo2) {
        this.streetNo2 = streetNo2;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
