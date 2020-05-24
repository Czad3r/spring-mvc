package pl.kowalczuk.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.kowalczuk.springmvc.domain.entities.User;
import pl.kowalczuk.springmvc.domain.exceptions.EmailAlreadyExistException;
import pl.kowalczuk.springmvc.domain.exceptions.UserNotFoundException;
import pl.kowalczuk.springmvc.domain.exceptions.UsernameAlreadyExistException;
import pl.kowalczuk.springmvc.domain.forms.LoginForm;
import pl.kowalczuk.springmvc.domain.forms.PasswordRecoverForm;
import pl.kowalczuk.springmvc.domain.forms.RegisterForm;
import pl.kowalczuk.springmvc.service.MailService;
import pl.kowalczuk.springmvc.service.PasswordService;
import pl.kowalczuk.springmvc.service.SecurityContextService;
import pl.kowalczuk.springmvc.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

import static pl.kowalczuk.springmvc.domain.constants.FormsConstants.*;
import static pl.kowalczuk.springmvc.utils.Utils.getURLBase;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailSender;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private SecurityContextService securityContext;

    @ModelAttribute("genderList")
    public List<String> genderList() {
        return GENDERS;
    }

    @ModelAttribute("countryList")
    public List<String> countryList() {
        return COUNTRIES;
    }


    @GetMapping("/register")
    public String register(Model model) {
        if (securityContext.isCurrentAuthenticated()) {
            return "redirect:/";
        } else {
            model.addAttribute("registerForm", new RegisterForm());
            model.addAttribute("isRegistered", false);
            return "auth/register";
        }
    }

    @PostMapping("/register")
    public String registerSubmit(@Valid RegisterForm registerForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        try {
            User registered = userService.registerNewUserAccount(registerForm);

            securityContext.setAuthentication(userService.loadUserByUsername(registered.getUsername()));

            model.addAttribute("isRegistered", true);
        } catch (EmailAlreadyExistException e) {
            bindingResult.addError(new FieldError(bindingResult.getObjectName(), "email", e.getMessage()));
            return "auth/register";
        } catch (UsernameAlreadyExistException e) {
            bindingResult.addError(new FieldError(bindingResult.getObjectName(), "username", e.getMessage()));
            return "auth/register";
        }

        return "auth/register";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String message) {
        if (securityContext.isCurrentAuthenticated()) {
            return "redirect:/";
        } else {
            if (error != null) {
                model.addAttribute("error", error.equals("") ? USERNAME_OR_PASSWORD_ERROR_MESSAGE : error);
            }
            if (message != null) {
                model.addAttribute("message", message);
            }
            model.addAttribute("loginForm", new LoginForm());
            return "auth/login";
        }
    }

    @GetMapping("/passwordRecover")
    public String passwordRecover(Model model) {
        if (securityContext.isCurrentAuthenticated()) {
            return "redirect:/";
        } else {
            model.addAttribute("passwordRecoverForm", new PasswordRecoverForm());
            return "auth/passwordRecover";
        }
    }

    @PostMapping("/passwordRecover")
    public String passwordRecover(@Valid PasswordRecoverForm passwordRecoverForm, BindingResult bindingResult, Model model,
                                  HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "auth/passwordRecover";
        }

        try {
            User user = userService.findUserByEmail(passwordRecoverForm.getEmail());

            if (user == null) {
                throw new UserNotFoundException("Nie znaleziono użytkownika z podanym emailem!");
            }
            String token = UUID.randomUUID().toString();
            passwordService.createPasswordResetTokenForUser(user, token);

            mailSender.sendMail(passwordService.constructResetTokenEmail(getURLBase(request), token, user));

            model.addAttribute("isSended", true);
        } catch (UserNotFoundException e) {
            bindingResult.addError(new FieldError(bindingResult.getObjectName(), "email", e.getMessage()));
            return "auth/passwordRecover";
        } catch (MalformedURLException e) {
            bindingResult.addError(new FieldError(bindingResult.getObjectName(), "email", "Usługa wysyłania emaili chwilowo nie działa."));
            return "auth/passwordRecover";
        }

        return "auth/passwordRecover";
    }
}
