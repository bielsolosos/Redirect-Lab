package space.bielsolososdev.rdl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("appName", "Redirect Lab");
        model.addAttribute("message", "Aplicação funcionando perfeitamente! 🚀");
        model.addAttribute("timestamp", LocalDateTime.now());
        return "index";
    }

    @GetMapping("/health")
    public String health(Model model) {
        model.addAttribute("status", "UP");
        model.addAttribute("database", "PostgreSQL");
        model.addAttribute("timestamp", LocalDateTime.now());
        return "health";
    }
}
