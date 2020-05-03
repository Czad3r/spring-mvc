package pl.kowalczuk.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.kowalczuk.springmvc.repository.QuoteRepository;

@Controller
public class MainController {

    @Autowired
    private QuoteRepository quoteRepository;

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
    public String quotesList(Model model) {
        model.addAttribute("quotes",quoteRepository.findAll());
        return "quotesList";
    }

    @GetMapping("/memoryGame")
    public String memoryGame() {
        return "memoryGame";
    }
}
