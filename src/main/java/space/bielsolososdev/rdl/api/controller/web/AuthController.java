package space.bielsolososdev.rdl.api.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import space.bielsolososdev.rdl.core.exception.BusinessException;
import space.bielsolososdev.rdl.domain.users.service.UserService;
import space.bielsolososdev.rdl.infrastructure.RdlProperties;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final RdlProperties rdlProperties;
    private final UserService userService;

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
        
        model.addAttribute("registrationEnabled", rdlProperties.getRegistrationEnabled());
        
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (!rdlProperties.getRegistrationEnabled()) {
            return "redirect:/login?error";
        }
        
        model.addAttribute("registrationEnabled", rdlProperties.getRegistrationEnabled());
        return "auth/register";
    }
    
    @PostMapping("/register")
    public String registerSubmit(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            RedirectAttributes redirectAttributes) {
        
        if (!rdlProperties.getRegistrationEnabled()) {
            return "redirect:/login?error";
        }
        
        try {
            if (!password.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "As senhas não coincidem");
                return "redirect:/register";
            }
            
            if (password.length() < 6) {
                redirectAttributes.addFlashAttribute("error", "A senha deve ter no mínimo 6 caracteres");
                return "redirect:/register";
            }
            
            userService.createUser(username, email, password);
            return "redirect:/login?registered";
            
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao criar usuário. Tente novamente.");
            return "redirect:/register";
        }
    }
}
