package space.bielsolososdev.rdl.api.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "registered", required = false) String registered,
            Model model) {
        
        if (error != null) {
            model.addAttribute("error", "Usuário ou senha inválidos");
        }
        
        if (logout != null) {
            model.addAttribute("success", "Logout realizado com sucesso!");
        }
        
        if (registered != null) {
            model.addAttribute("success", "Conta criada com sucesso! Faça login para continuar.");
        }
        
        return "auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }
}
