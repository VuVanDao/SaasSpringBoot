package PersonalProject.demo.mapper;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import PersonalProject.demo.Dto.Request.CreateUserRequest;
import PersonalProject.demo.Dto.Response.UserDto;
import PersonalProject.demo.domain.UserRole;
import PersonalProject.demo.models.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class userMapper {
    private final PasswordEncoder passwordEncoder;

    public User convertToModel(CreateUserRequest request) {
        UserRole role = UserRole.ROLE_USER;
        if (request.getRole() != null) {
            role = request.getRole();
        }
        return User.builder().email(request.getEmail()).fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword())).phone(request.getPhone()).role(role)
                .lastLogin(LocalDateTime.now()).build();
    }
    public User convertDtoToModel(UserDto userDto) {
        return User.builder()
                .email(userDto.getEmail())
                .fullName(userDto.getFullName())
                .phone(userDto.getPhone())
                .lastLogin(userDto.getLastLogin())
                .role(userDto.getRole())
                // .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
    }

    public UserDto convertToDto(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .lastLogin(user.getLastLogin())
                .role(user.getRole())
        .build();
    }
}
