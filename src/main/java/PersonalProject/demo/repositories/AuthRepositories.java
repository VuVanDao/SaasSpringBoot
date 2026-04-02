package PersonalProject.demo.repositories;

import PersonalProject.demo.Dto.Request.CreateUserRequest;
import PersonalProject.demo.Dto.Request.LoginRequest;
import PersonalProject.demo.Dto.Response.AuthResponse;
import PersonalProject.demo.Dto.Response.UserDto;

public interface AuthRepositories {
    AuthResponse signUp(CreateUserRequest userDto);
    AuthResponse login(LoginRequest userDto);
}
