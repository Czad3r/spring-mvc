package pl.kowalczuk.springmvc.validators;

import pl.kowalczuk.springmvc.domain.annotations.PasswordMatches;
import pl.kowalczuk.springmvc.domain.forms.PasswordWithTokenForm;
import pl.kowalczuk.springmvc.domain.forms.RegisterForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj instanceof RegisterForm) {
            RegisterForm registerForm = (RegisterForm) obj;
            return registerForm.getPassword().equals(registerForm.getPassword2());
        } else if (obj instanceof PasswordWithTokenForm) {
            PasswordWithTokenForm passwordWithTokenForm = (PasswordWithTokenForm) obj;
            return passwordWithTokenForm.getPassword().equals(passwordWithTokenForm.getPassword2());
        } else {
            System.out.println("Nie można użyć PasswordMatchesValidator dla " + obj.toString() + ". Przedefiniuj implementację validatora!");
            return false;
        }
    }
}
