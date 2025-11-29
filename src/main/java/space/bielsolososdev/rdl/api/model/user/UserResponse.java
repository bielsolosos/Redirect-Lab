package space.bielsolososdev.rdl.api.model.user;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponse(
    Long id, 
    String username, 
    String email, 
    Boolean isActive,
    LocalDateTime createdAt,
    Set<String> roles
) {}