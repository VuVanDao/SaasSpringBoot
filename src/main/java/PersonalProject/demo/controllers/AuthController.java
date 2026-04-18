package PersonalProject.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    @PostMapping("/sign_up")
    public ApiResponse<AuthResponse> postMethodName(@Valid @RequestBody CreateUserRequest request) {
        AuthResponse res = this.authServiceImplementation.signUp(request);
        return ApiResponse.<AuthResponse>builder().code(200).result(res).build();
    }
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse res = this.authServiceImplementation.login(request);
        return ApiResponse.<AuthResponse>builder().code(200).result(res).build();
    }
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        AuthResponse response = authServiceImplementation.refreshToken(request.getToken());
        return ResponseEntity.ok(response);
    }
}
