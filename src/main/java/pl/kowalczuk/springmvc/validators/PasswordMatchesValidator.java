package pl.kowalczuk.springmvc.validators;

import pl.kowalczuk.springmvc.domain.annotations.PasswordMatches;
import pl.kowalczuk.springmvc.domain.forms.RegisterForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        RegisterForm registerForm = (RegisterForm) obj;
        return registerForm.getPassword().equals(registerForm.getPassword2());
    }
}
