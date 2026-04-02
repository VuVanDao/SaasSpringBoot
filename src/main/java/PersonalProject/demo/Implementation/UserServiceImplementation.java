package PersonalProject.demo.Implementation;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import PersonalProject.demo.Dto.Response.UserDto;
import PersonalProject.demo.configuration.JwtProvider;
import PersonalProject.demo.exception.ResourceNotFoundException;
import PersonalProject.demo.mapper.storeMapper;
import PersonalProject.demo.models.User;
import PersonalProject.demo.repositories.UserRepository;
import PersonalProject.demo.services.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final storeMapper storeMapper;

    @Override
    public UserDto getUserFromJwtToken(String token) {
        System.out.println("-----------------UserServiceImplementation.getUserFromJwtToken-----------------");
        String email = jwtProvider.GetEmailFromToken(token.substring(7));
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .lastLogin(user.getLastLogin())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }

    @Override
    public UserDto getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .lastLogin(user.getLastLogin())
                .phone(user.getPhone())
                .role(user.getRole())
                .store(storeMapper.convertToDto(user.getStore()))
                .build();
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .lastLogin(user.getLastLogin())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .lastLogin(user.getLastLogin())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }

    @Override
    public List<UserDto> getAllUsers() {
        // Implementation for getting all users
        return userRepository.findAll().stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .fullName(user.getFullName())
                        .lastLogin(user.getLastLogin())
                        .phone(user.getPhone())
                        .role(user.getRole())
                        .build())
                .toList();
    }
}
         
