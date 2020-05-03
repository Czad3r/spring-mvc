package pl.kowalczuk.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/registerForm")
    public String registerForm() {
        return "registerForm";
    }

    @GetMapping("/registered")
    public String registered() {
        return "registered";
    }

    @GetMapping("/quotesList")
    public String quotesList() {
        return "quotesList";
    }

    @GetMapping("/memoryGame")
    public String memoryGame() {
        return "memoryGame";
    }
}
