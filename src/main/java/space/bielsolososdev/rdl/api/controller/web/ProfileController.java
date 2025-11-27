package space.bielsolososdev.rdl.api.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import space.bielsolososdev.rdl.core.exception.BusinessException;
import space.bielsolososdev.rdl.domain.users.model.User;
import space.bielsolososdev.rdl.domain.users.service.UserService;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping
    public String profile(Model model) {
        User user = userService.getMe();
        model.addAttribute("user", user);
        return "profile/index";
    }

    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            RedirectAttributes redirectAttributes) {

        // Validar se as senhas novas conferem
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "As senhas não conferem");
            return "redirect:/profile";
        }

        // Validar tamanho mínimo
        if (newPassword.length() < 6) {
            redirectAttributes.addFlashAttribute("error", "A nova senha deve ter no mínimo 6 caracteres");
            return "redirect:/profile";
        }

        try {
            User user = userService.getMe();
            userService.changePassword(user.getId(), currentPassword, newPassword);
            redirectAttributes.addFlashAttribute("success", "Senha alterada com sucesso!");
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/profile";
    }
}
