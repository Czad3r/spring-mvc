package pl.kowalczuk.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/user")
public class UserController {

    @GetMapping("/edit")
    public String edit() {
        return "edit";
    }


}
