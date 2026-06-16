package PersonalProject.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Request.CreateUserRequest;
import PersonalProject.demo.Dto.Request.LoginRequest;
import PersonalProject.demo.Dto.Request.RefreshTokenRequest;
import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.AuthResponse;
import PersonalProject.demo.Implementation.AuthServiceImplementation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthServiceImplementation authServiceImplementation;

    // DES: Đăng ký tài khoản người dùng mới
    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<AuthResponse>> signUp(@Valid @RequestBody CreateUserRequest request) {
        AuthResponse res = this.authServiceImplementation.signUp(request);
        ApiResponse<AuthResponse> response = ApiResponse.<AuthResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("User registered successfully")
                .result(res)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // DES: Đăng nhập vào hệ thống
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse res = this.authServiceImplementation.login(request);
        ApiResponse<AuthResponse> response = ApiResponse.<AuthResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Login successful")
                .result(res)
                .build();
        return ResponseEntity.ok(response);
    }

    // DES: Refresh access token bằng refresh token
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        AuthResponse res = authServiceImplementation.refreshToken(request.getToken());
        ApiResponse<AuthResponse> response = ApiResponse.<AuthResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Token refreshed successfully")
                .result(res)
                .build();
        return ResponseEntity.ok(response);
    }
}
