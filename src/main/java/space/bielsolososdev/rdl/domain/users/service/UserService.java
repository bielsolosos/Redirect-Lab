package space.bielsolososdev.rdl.domain.users.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import space.bielsolososdev.rdl.core.exception.BusinessException;
import space.bielsolososdev.rdl.domain.users.model.User;
import space.bielsolososdev.rdl.domain.users.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public User getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException("Usuário não autenticado");
        }

        String username = authentication.getName();
        return repository.findByUsername(username)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));
    }

    public User changePassword(Long id, String oldPassword, String newPassword) {
        User user = repository.findById(id)
                .orElseThrow(() -> new BusinessException("User não encontrado"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BusinessException("Senha atual incorreta");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        return repository.save(user);
    }

    /**
     * TODO: Fazer envio e confirmação de email, setar role padrão e outras features mais. 
     * @param username
     * @param email
     * @param password
     * @return
     */
    public User createUser(String username, String email, String password){
        if (repository.findByUsername(username).isPresent()) {
            throw new BusinessException("Nome de usuário já está em uso");
        }
        
        if (repository.findByEmail(email).isPresent()) {
            throw new BusinessException("E-mail já está em uso");
        }
        
        User entity = new User();  
        entity.setEmail(email);
        entity.setUsername(username); 
        entity.setPassword(passwordEncoder.encode(password));

        return repository.save(entity);
    }
}
