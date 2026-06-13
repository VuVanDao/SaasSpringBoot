package PersonalProject.demo.Implementation;

import java.time.Instant;
import java.time.LocalDateTime;
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
        /*
        Dòng code này giống như việc bạn tạo ra một chiếc "Thẻ tạm trú" cho người dùng sau khi họ đã xuất trình đúng giấy tờ.
        - request.getEmail(),      // Principal: Định danh (Tên đăng nhập/Email)
        - request.getPassword(),   // Credentials: Mật khẩu (thường để null sau khi xác thực xong để bảo mật)
        - user.getAuthorities()    // Authorities: Danh sách các quyền (chìa khóa các phòng) mà người dùng này có thể truy cập.
        + Mục đích: Đóng gói tất cả thông tin quan trọng của người dùng vào một đối tượng duy nhất gọi là Authentication.
         */
        Authentication auth = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword(),
                user.getAuthorities());
        System.out.println("auth: " + auth);
        /*
        Nếu dòng trên là tạo thẻ, thì dòng này là "Ghim chiếc thẻ đó lên ngực áo" của người dùng.
        - Khi bạn gọi dòng này, Spring sẽ lưu thông tin vào một nơi gọi là ThreadLocal.
        - Ở bất kỳ đâu trong code (Controller, Service), Spring có thể "liếc mắt" nhìn vào cái thẻ này để biết: "À, ông này có quyền ADMIN, cho phép vào hàm xóa dữ liệu
        - Bạn có thể lấy lại thông tin người dùng đang đăng nhập ở bất cứ đâu mà không cần truyền tham số qua từng hàm, chỉ bằng cách gọi SecurityContextHolder.getContext().getAuthentication()
        - Trong suốt một Request (từ lúc gửi lên đến lúc nhận phản hồi), Spring sẽ nhớ mặt người dùng này.
        */
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = jwtProvider.generateToken(auth);// access token
        String refreshToken = jwtProvider.generateRefreshToken();

        // Lưu Refresh Token vào DB
        RefreshToken rt = RefreshToken.builder()
                .user(user)
                .token(refreshToken)
                .expiryDate(Instant.now().plusMillis(JwtConstant.REFRESH_TOKEN_EXPIRATION))
                .build();
        refreshTokenRepository.save(rt);
        return AuthResponse.builder().jwt(jwt).refreshToken(refreshToken).message("sign up successful").userInfo(userMapper.convertToDto(user)).build();
    }

    @Override
    public AuthResponse login(LoginRequest userDto) {
        System.out.println("-----------------AuthServiceImplementation.login-----------------");

        String email = userDto.getEmail();
        String password = userDto.getPassword();
        System.out.println("email: " + email);
        System.out.println("password: " + password);

        Authentication authentication = authenticate(email, password);
        User user = userRepository.findByEmail(email);
        // Cập nhật thời gian đăng nhập cuối cùng của user
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // generate accessToken và refreshToken
        String accessToken = jwtProvider.generateToken(authentication);// access token
        String refreshToken = jwtProvider.generateRefreshToken();// refresh token

        // Lưu Refresh Token vào DB
        RefreshToken rt = RefreshToken.builder()
                .user(user)
                .token(refreshToken)
                .expiryDate(Instant.now().plusMillis(JwtConstant.REFRESH_TOKEN_EXPIRATION))
                .build();
        refreshTokenRepository.save(rt);
        
        return AuthResponse.builder().jwt(accessToken).refreshToken(refreshToken).message("login successful").userInfo(userMapper.convertToDto(user)).build();
    }

    private Authentication authenticate(String email, String password) {
        UserDetails user = customUserImplementation.loadUserByUsername(email);
        // User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new RuntimeException("User not found");
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
                Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), null, user.getAuthorities());
                String newAccessToken = jwtProvider.generateToken(auth);
                
                return AuthResponse.builder()
                        .jwt(newAccessToken)
                        .refreshToken(requestRefreshToken) // Có thể trả lại RT cũ hoặc xoay vòng (Rotate RT)
                        .message("Token refreshed")
                        .build();
            })
            .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }
}
