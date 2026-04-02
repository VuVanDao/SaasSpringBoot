package PersonalProject.demo.services;

import java.util.List;

import PersonalProject.demo.Dto.Response.UserDto;


public interface UserService {
    UserDto getUserFromJwtToken(String token);

    UserDto getCurrentUser();

    UserDto getUserByEmail(String email);

    UserDto getUserById(Long id);

    List<UserDto> getAllUsers();
}
