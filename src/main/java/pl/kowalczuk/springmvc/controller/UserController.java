package pl.kowalczuk.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.kowalczuk.springmvc.domain.entities.User;
import pl.kowalczuk.springmvc.domain.exceptions.EmailAlreadyExistException;
import pl.kowalczuk.springmvc.domain.exceptions.UsernameAlreadyExistException;
import pl.kowalczuk.springmvc.domain.forms.EditProfileForm;
import pl.kowalczuk.springmvc.domain.forms.PasswordForm;
import pl.kowalczuk.springmvc.domain.forms.PasswordWithTokenForm;
import pl.kowalczuk.springmvc.service.PasswordService;
import pl.kowalczuk.springmvc.service.SecurityContextService;
import pl.kowalczuk.springmvc.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static pl.kowalczuk.springmvc.domain.constants.FormsConstants.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserService userService;

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

    @GetMapping("/changePassword")
    public ModelAndView showChangePasswordPage(Model model, @RequestParam("token") String token) {
        String result = passwordService.validatePasswordResetToken(token);

        if (result != null) {
            return new ModelAndView("redirect:/login?error=" + result);
        } else {
            model.addAttribute("token", token);
            model.addAttribute("passwordWithTokenForm", new PasswordWithTokenForm());
            return new ModelAndView("user/updatePassword", model.asMap());
        }
    }

    @PostMapping("/savePassword")
    public ModelAndView savePassword(@Valid PasswordWithTokenForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("user/updatePassword");
        }

        String result = passwordService.validatePasswordResetToken(form.getToken());

        if (result != null) {
            return new ModelAndView("redirect:/login?error=" + result);
        }

        User user = passwordService.getUserByPasswordResetToken(form.getToken());
        if (user != null) {
            userService.changeUserPassword(user, form.getPassword());
            return new ModelAndView("redirect:/login", "message", PASSWORD_CHANGE_SUCCESS_MESSAGE);
        } else {
            return new ModelAndView("redirect:/login?error=" + INVALID_TOKEN_MESSAGE);
        }
    }

    @GetMapping("/editPassword")
    public String editPassword(Model model) {
        if (securityContext.isCurrentAuthenticated()) {

            model.addAttribute("passwordForm", new PasswordForm());
            return "user/editPassword";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/editPassword")
    public String editPassword(@Valid PasswordForm form, BindingResult bindingResult, Model model) {
        if (!securityContext.isCurrentAuthenticated()) {
            return "redirect:/";
        }
        if (bindingResult.hasErrors()) {
            return "user/editPassword";
        }

        try {
            User user = userService.updateUserPassword(form);

            securityContext.setAuthentication(userService.loadUserByUsername(user.getUsername()));
            model.addAttribute("message", "Pomyślnie zmieniono hasło!");
            return "user/editPassword";
        } catch (Exception e) {
            bindingResult.addError(new FieldError(bindingResult.getObjectName(), "password2", e.getMessage()));
            return "user/editPassword";
        }
    }


    @GetMapping("/editProfile")
    public String editProfile(Model model) {
        if (securityContext.isCurrentAuthenticated()) {

            User currentUser = userService.getCurrentUser();

            EditProfileForm form = new EditProfileForm();
            form.setUsername(currentUser.getUsername());

            form.setCity(currentUser.getCity());
            form.setCountry(currentUser.getCountry());
            form.setGender(currentUser.getGender());
            form.setPhone(currentUser.getPhone());
            form.setPostalCode(currentUser.getPostalCode());
            form.setStreetNo(currentUser.getStreetNo());
            form.setStreetNo2(currentUser.getStreetNo2());
            form.setEmail(currentUser.getEmail());
            form.setStreet(currentUser.getStreet());

            model.addAttribute("editProfileForm", form);
            return "user/editProfile";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/editProfile")
    public String editProfile(@Valid EditProfileForm form, BindingResult bindingResult, Model model) {
        if (!securityContext.isCurrentAuthenticated()) {
            return "redirect:/";
        }
        if (bindingResult.hasErrors()) {
            return "user/editProfile";
        }

        try {
            User user = userService.updateUserAccount(form);

            securityContext.setAuthentication(userService.loadUserByUsername(user.getUsername()));
            model.addAttribute("message", "Pomyślnie zaaktualizowano dane!");
            return "user/editProfile";
        } catch (EmailAlreadyExistException e) {
            bindingResult.addError(new FieldError(bindingResult.getObjectName(), "email", e.getMessage()));
            return "user/editProfile";
        } catch (UsernameAlreadyExistException e) {
            bindingResult.addError(new FieldError(bindingResult.getObjectName(), "username", e.getMessage()));
            return "user/editProfile";
        } catch (UsernameNotFoundException e) {
            model.addAttribute("message", e.getMessage());
            return "user/editProfile";
        }
    }
}
