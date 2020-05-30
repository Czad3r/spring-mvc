package pl.kowalczuk.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;
import pl.kowalczuk.springmvc.repository.QuoteRepository;

@Controller
public class MainController {

    @Autowired
    private QuoteRepository quoteRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/quotesList")
    public String quotesList(Model model) {
        model.addAttribute("quotes", quoteRepository.findAll());
        return "quotesList";
    }

    @GetMapping("/memoryGame")
    public String memoryGame() {
        return "memoryGame";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/downloadSource")
    public RedirectView downloadSource() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("https://github.com/Czad3r/spring-mvc/archive/master.zip");
        return redirectView;
    }
}
