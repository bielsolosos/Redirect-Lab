package space.bielsolososdev.rdl.api.mapper;

import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;
import space.bielsolososdev.rdl.api.model.user.UserResponse;
import space.bielsolososdev.rdl.domain.users.model.User;

@UtilityClass
public class UserMapper {

    public UserResponse toUserResponse(User user){
        return new UserResponse(
            user.getId(), 
            user.getUsername(), 
            user.getEmail(),
            user.getIsActive(),
            user.getCreatedAt(),
            user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet())
        );
    }
}
