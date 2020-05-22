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
import pl.kowalczuk.springmvc.domain.exceptions.UserAlreadyExistException;
import pl.kowalczuk.springmvc.domain.forms.LoginForm;
import pl.kowalczuk.springmvc.domain.forms.RegisterForm;
import pl.kowalczuk.springmvc.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static pl.kowalczuk.springmvc.domain.constants.FormsConstants.COUNTRIES;
import static pl.kowalczuk.springmvc.domain.constants.FormsConstants.GENDERS;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @ModelAttribute("genderList")
    public List<String> genderList() {
        return GENDERS;
    }

    @ModelAttribute("countryList")
    public List<String> countryList() {
        return COUNTRIES;
    }

    @ModelAttribute("loginError")
    public String loginError() {
        return "Nieprawidłowa nazwa użytkownika lub hasło!";
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (isCurrentAuthenticated()) {
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

            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken(userService.userToPrincipal(registered),
                    registerForm.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authReq);
//            SecurityContext sc = SecurityContextHolder.getContext();
//            sc.setAuthentication(authReq);
//            HttpSession session = req.getSession(true);
//            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

            model.addAttribute("isRegistered", true);
        } catch (UserAlreadyExistException e) {
            bindingResult.addError(new FieldError(bindingResult.getObjectName(), "email", e.getMessage()));
            return "auth/register";
        }

        return "auth/register";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (isCurrentAuthenticated()) {
            return "redirect:/";
        } else {
            if (error != null) model.addAttribute("error", true);
            model.addAttribute("loginForm", new LoginForm());
            return "auth/login";
        }
    }

//    @GetMapping("/logout")
//    public String logout() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            SecurityContextHolder.getContext().setAuthentication(null);
//        }
//        return "redirect:/login";
//    }

    @GetMapping("/forgetPassword")
    public String forgetPassword() {
        return "auth/forgetPassword";
    }


    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    private boolean isCurrentAuthenticated() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.User) {
            return ((org.springframework.security.core.userdetails.User) principal).isEnabled();
        } else
            return false;
    }
}
