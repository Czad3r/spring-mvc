package pl.kowalczuk.springmvc.domain.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class FormsConstants {
    public static final String ALPHANUMERIC_POLISH_MIN_0_REGEX = "^[0-9A-Za-zĄąŻżŹźÓóĘęŁł ]*$";

    public static final String EMPTY_ERROR_MESSAGE = "EMPTY_ERROR_MESSAGE";
    public static final String INVALID_FORMAT_ERROR_MESSAGE = "INVALID_FORMAT_ERROR_MESSAGE";
    public static final String EMPTY_OR_INVALID_FORMAT_ERROR_MESSAGE = "EMPTY_OR_INVALID_FORMAT_ERROR_MESSAGE";
    public static final String INVALID_TOKEN_MESSAGE = "INVALID_TOKEN_MESSAGE";
    public static final String PASSWORD_CHANGE_SUCCESS_MESSAGE = "PASSWORD_CHANGE_SUCCESS_MESSAGE";
    public static final String EXPIRED_TOKEN_MESSAGE = "EXPIRED_TOKEN_MESSAGE";
    public static final String USERNAME_OR_PASSWORD_ERROR_MESSAGE = "USERNAME_OR_PASSWORD_ERROR_MESSAGE";
    public static final String EMAIL_EXIST_ERROR_MESSAGE = "EMAIL_EXIST_ERROR_MESSAGE";
    public static final String USERNAME_EXIST_ERROR_MESSAGE = "USERNAME_EXIST_ERROR_MESSAGE";
    public static final String USERNAME_NOT_EXIST_ERROR_MESSAGE = "USERNAME_NOT_EXIST_ERROR_MESSAGE";
    public static final String EMAIL_NOT_EXIST_ERROR_MESSAGE = "EMAIL_NOT_EXIST_ERROR_MESSAGE";
    public static final String EMAIL_SERVICE_ERROR_MESSAGE = "EMAIL_SERVICE_ERROR_MESSAGE";
    public static final String PASSWORD_EDIT_SUCCESS_MESSAGE = "PASSWORD_EDIT_SUCCESS_MESSAGE";
    public static final String PROFILE_EDIT_SUCCESS_MESSAGE = "PROFILE_EDIT_SUCCESS_MESSAGE";
    public static final String FIELD_5_30_ERROR_MESSAGE = "FIELD_5_30_ERROR_MESSAGE";
    public static final String FIELD_6_30_ERROR_MESSAGE = "FIELD_6_30_ERROR_MESSAGE";
    public static final String PASSWORD_MISMATCH_ERROR_MESSAGE = "PASSWORD_MISMATCH_ERROR_MESSAGE";

    public static final List<String> GENDERS = Collections.unmodifiableList(Arrays.asList(
            "Woman",
            "Man"
    ));
    public static final List<String> COUNTRIES = Collections.unmodifiableList(Arrays.asList(
            null,
            "Austria",
            "Lithuania",
            "Belgium",
            "Luxembourg",
            "Bulgaria",
            "Latvia",
            "Croatia",
            "Malta",
            "Cyprus",
            "Germany",
            "CzechRepublic",
            "Poland",
            "Denmark",
            "Portugal",
            "Estonia",
            "Romania",
            "Finland",
            "Slovakia",
            "France",
            "Slovenia",
            "Greece",
            "Sweden",
            "Spain",
            "Hungary",
            "Netherlands",
            "Italy",
            "Ireland"
    ));

}
