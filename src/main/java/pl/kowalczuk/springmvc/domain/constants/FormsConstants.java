package pl.kowalczuk.springmvc.domain.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class FormsConstants {
    public static final String EMPTY_ERROR_MESSAGE = "Uzupełnij to pole!";
    public static final String INVALID_FORMAT_ERROR_MESSAGE = "Niepoprawny format!";
    public static final String EMPTY_OR_INVALID_FORMAT_ERROR_MESSAGE = "Niepoprawny format / Brak wartości!";
    public static final String ALPHANUMERIC_POLISH_MIN_0_REGEX = "^[0-9A-Za-zĄąŻżŹźÓóĘęŁł ]*$";
    public static final String INVALID_TOKEN_MESSAGE = "Niepoprawny token zmiany hasla!";
    public static final String PASSWORD_CHANGE_SUCCESS_MESSAGE = "Pomyślnie zmieniono hasło!!";
    public static final String EXPIRED_TOKEN_MESSAGE = "Token zmiany hasła stracil waznosc!";
    public static final String USERNAME_OR_PASSWORD_ERROR_MESSAGE = "Nieprawidłowa nazwa użytkownika lub hasło!";
    public static final String SIZE_ERROR_MESSAGE = "";
    public static final String EMAIL_ERROR_MESSAGE = "";

    public static final List<String> GENDERS = Collections.unmodifiableList(Arrays.asList(
            "Kobieta",
            "Mężczyzna"
    ));
    public static final List<String> COUNTRIES = Collections.unmodifiableList(Arrays.asList(
            null,
            "Austria",
            "Litwa",
            "Belgia",
            "Luksemburg",
            "Bułgaria",
            "Łotwa",
            "Chorwacja",
            "Malta",
            "Cypr",
            "Niemcy",
            "Czechy",
            "Polska",
            "Dania",
            "Portugalia",
            "Estonia",
            "Rumunia",
            "Finlandia",
            "Słowacja",
            "Francja",
            "Słowenia",
            "Grecja",
            "Szwecja",
            "Hiszpania",
            "Węgry",
            "Holandia",
            "Włochy",
            "Irlandia"
    ));

}
