package pl.kowalczuk.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.kowalczuk.springmvc.domain.entities.User;
import pl.kowalczuk.springmvc.domain.forms.PasswordWithTokenForm;
import pl.kowalczuk.springmvc.service.PasswordService;
import pl.kowalczuk.springmvc.service.UserService;

import javax.validation.Valid;

import static pl.kowalczuk.springmvc.domain.constants.FormsConstants.INVALID_TOKEN_MESSAGE;
import static pl.kowalczuk.springmvc.domain.constants.FormsConstants.PASSWORD_CHANGE_SUCCESS_MESSAGE;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserService userService;

    @GetMapping("/edit")
    public String edit() {
        return "edit";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
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
}
