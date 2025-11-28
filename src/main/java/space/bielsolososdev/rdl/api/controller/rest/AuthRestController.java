package space.bielsolososdev.rdl.api.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import space.bielsolososdev.rdl.domain.users.model.dto.LoginRequest;
import space.bielsolososdev.rdl.domain.users.model.dto.RefreshRequest;
import space.bielsolososdev.rdl.domain.users.model.dto.TokenResponse;
import space.bielsolososdev.rdl.domain.users.service.AuthService;

@Tag(name = "Autenticação", description = "Endpoints para autenticação e gerenciamento de tokens JWT")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok().body(service.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.ok().body(service.refresh(request));
    }
}
