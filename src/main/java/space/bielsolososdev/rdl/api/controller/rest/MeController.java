package space.bielsolososdev.rdl.api.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import space.bielsolososdev.rdl.api.mapper.UserMapper;
import space.bielsolososdev.rdl.api.model.user.UserResponse;
import space.bielsolososdev.rdl.domain.users.service.UserService;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class MeController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> getMe() {
        return ResponseEntity.ok(UserMapper.toUserResponse(userService.getMe()));
    }
}