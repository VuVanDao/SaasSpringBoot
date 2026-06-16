package PersonalProject.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Request.UpdateProfileRequest;
import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.UserDto;
import PersonalProject.demo.configuration.JwtConstant;
import PersonalProject.demo.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final String HeaderKey = JwtConstant.JWT_HEADER;

    // DES: lấy profile, thông tin của user đang login
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> getProfile(@RequestHeader(HeaderKey) String jwt) {
            UserDto userDto = userService.getUserFromJwtToken(jwt);
            ApiResponse<UserDto> response = ApiResponse.<UserDto>builder()
                            .code(HttpStatus.OK.value())
                            .result(userDto)
                            .build();
            return ResponseEntity.ok(response);
    }

    // (EM) DES: lấy user bằng id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id) {
            UserDto userDto = userService.getUserById(id);
            ApiResponse<UserDto> response = ApiResponse.<UserDto>builder()
                            .code(HttpStatus.OK.value())
                            .result(userDto)
                            .build();
            return ResponseEntity.ok(response);
    }
    
    // (EM) DES: lấy danh sách user
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getUsers(
                    @RequestHeader("${app.header-tenant}") Long tenantId) {
            List<UserDto> userDto = userService.getAllUsers(tenantId);
            ApiResponse<List<UserDto>> response = ApiResponse.<List<UserDto>>builder()
                            .code(HttpStatus.OK.value())
                            .result(userDto)
                            .build();
            return ResponseEntity.ok(response);
    }

    // DES: update profile của user
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> updateUserProfile(@PathVariable Long id,
            @Valid @RequestBody UpdateProfileRequest entity, 
                    @RequestHeader("${app.header-tenant}") Long tenantId) {
            ApiResponse<UserDto> response = ApiResponse.<UserDto>builder()
                            .code(HttpStatus.OK.value())
                            .result(userService.updateUserProfile(id, entity, tenantId))
                            .build();
            return ResponseEntity.ok(response);
    }
    
    // (D) DES: lấy danh sách user của 1 tenant - hơi vô nghĩa, để đấy, tạm thời chưa có công dụng
    @GetMapping("/store")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsersByTenantId(
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        ApiResponse<List<UserDto>> response = ApiResponse.<List<UserDto>>builder()
                .code(HttpStatus.OK.value())
                .result(userService.getAllUsersByTenantId(tenantId))
                .build();
        return ResponseEntity.ok(response);
    }
}
