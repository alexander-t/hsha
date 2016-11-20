package se.tarlinder.hsha.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RootController {

    @Autowired
    Environment environment;

    @RequestMapping("/")
    public String greeting(Model model) {
        model.addAttribute("endpoint", environment.getProperty("endpoint", "localhost"));
        return "index";
    }
}
