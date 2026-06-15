package PersonalProject.demo.Implementation;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import PersonalProject.demo.Dto.Request.CreateUserRequest;
import PersonalProject.demo.Dto.Request.LoginRequest;
import PersonalProject.demo.Dto.Response.AuthResponse;
import PersonalProject.demo.Dto.Response.UserDto;
import PersonalProject.demo.Enums.UserRole;
import PersonalProject.demo.configuration.JwtConstant;
import PersonalProject.demo.configuration.JwtProvider;
import PersonalProject.demo.exception.ResourceNotFoundException;
import PersonalProject.demo.mapper.userMapper;
import PersonalProject.demo.models.RefreshToken;
import PersonalProject.demo.models.Tenant;
import PersonalProject.demo.models.User;
import PersonalProject.demo.repositories.AuthRepositories;
import PersonalProject.demo.repositories.RefreshTokenRepository;
import PersonalProject.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthRepositories {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImplementation customUserImplementation;
    private final userMapper userMapper;
    private final RefreshTokenRepository refreshTokenRepository;
    
    @Override
    public AuthResponse signUp(CreateUserRequest request) {
        System.out.println("-----------------AuthServiceImplementation.signUp-----------------");
        if ((request.getRole() == UserRole.ROLE_ADMIN) || (request.getRole() == UserRole.ROLE_SUPER_ADMIN)) {
            throw new RuntimeException("User with role admin already exist");
        }
        User user = userMapper.convertToModel(request);
        this.userRepository.save(user);

        String jwt = jwtProvider.generateToken(user);// access token
        String refreshToken = jwtProvider.generateRefreshToken();

        // Lưu Refresh Token vào DB
        RefreshToken rt = RefreshToken.builder()
                .user(user)
                .token(refreshToken)
                .expiryDate(Instant.now().plusMillis(JwtConstant.REFRESH_TOKEN_EXPIRATION))
                .build();
        refreshTokenRepository.save(rt);
        return AuthResponse.builder().accessToken(jwt).refreshToken(refreshToken).message("sign up successful").userInfo(userMapper.convertToDto(user)).build();
    }

    @Override
    public AuthResponse login(LoginRequest userDto) {
        System.out.println("-----------------AuthServiceImplementation.login-----------------");

        String email = userDto.getEmail();
        String password = userDto.getPassword();

        authenticate(email, password);
        User user = userRepository.findByEmail(email);
        // Cập nhật thời gian đăng nhập cuối cùng của user
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        // generate accessToken và refreshToken
        String accessToken = jwtProvider.generateToken(user);// access token
        String refreshToken = jwtProvider.generateRefreshToken();// refresh token

        // Lưu Refresh Token vào DB
        RefreshToken rt = RefreshToken.builder()
                .user(user)
                .token(refreshToken)
                .expiryDate(Instant.now().plusMillis(JwtConstant.REFRESH_TOKEN_EXPIRATION))
                .build();
        refreshTokenRepository.save(rt);

        LocalDateTime accessTokenExpiredAt = LocalDateTime.now().plus(JwtConstant.ACCESS_TOKEN_EXPIRATION,
                ChronoUnit.MILLIS);
        LocalDateTime refreshTokenExpiredAt = LocalDateTime.now().plus(JwtConstant.REFRESH_TOKEN_EXPIRATION,
                ChronoUnit.MILLIS);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .message("login successful")
                .userInfo(userMapper.convertToDto(user))
                .accessTokenExpiredAt(accessTokenExpiredAt)
                .refreshTokenExpiredAt(refreshTokenExpiredAt)
                .build();
    }

    private Authentication authenticate(String email, String password) {
        UserDetails user = customUserImplementation.loadUserByUsername(email);
        // User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password not set for this account");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(email, null, user.getAuthorities());
    }

    @Override
    public AuthResponse refreshToken(String requestRefreshToken) {
        return refreshTokenRepository.findByToken(requestRefreshToken)
            .map(token -> {
                // Kiểm tra refresh token hết hạn chưa
                if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
                        refreshTokenRepository.delete(token);
                    // CHECK: theem casi Errorcode o day cho clean
                    throw new RuntimeException("Refresh token was expired. Please make a new signin request");
                }
                return token;
            })
            .map(RefreshToken::getUser)
            .map(user -> {
                // Tạo Access Token MỚI
                String newAccessToken = jwtProvider.generateToken(user);
                
                return AuthResponse.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(requestRefreshToken) // Có thể trả lại RT cũ hoặc xoay vòng (Rotate RT)
                        .message("Token refreshed")
                        .build();
            })
            .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }
}
