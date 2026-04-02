package PersonalProject.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.UserDto;
import PersonalProject.demo.configuration.JwtConstant;
import PersonalProject.demo.services.UserService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final String HeaderKey = JwtConstant.JWT_HEADER;
    @GetMapping("/profile")
    public ApiResponse<UserDto> Profile(@RequestHeader(HeaderKey) String jwt) {
        UserDto userDto = userService.getUserFromJwtToken(jwt);
        return ApiResponse.<UserDto>builder()
                .code(HttpStatus.OK.value())
                .result(userDto)
                .build();
    }

    @GetMapping("/{id}")
        public ApiResponse<UserDto> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);
        return ApiResponse.<UserDto>builder()
                .code(HttpStatus.OK.value())
                .result(userDto)
                .build();
    }
        @GetMapping
        public ApiResponse<List<UserDto>> getUsers(@RequestParam(required = false) String email) {
            if(email != null){
                UserDto userDto = userService.getUserByEmail(email);
                return ApiResponse.<List<UserDto>>builder()
                        .code(HttpStatus.OK.value())
                        .result(List.of(userDto))
                        .build();
            }
        List<UserDto> userDto = userService.getAllUsers();
        return ApiResponse.<List<UserDto>>builder()
                .code(HttpStatus.OK.value())
                .result(userDto)
                .build();
    }
}
