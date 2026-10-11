package org.example.mapper;

import org.example.domain.User;
import org.example.dto.UserResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public UserResponseDto toUserResponseDto(User user){
        return new UserResponseDto(user.getId(), user.getEmail(), user.getRole());
    }
}
