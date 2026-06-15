package PersonalProject.demo.Dto.Response;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponse {
    String accessToken;//accessToken
    String refreshToken;
    String message;
    UserDto userInfo;
    LocalDateTime accessTokenExpiredAt;
    LocalDateTime refreshTokenExpiredAt;
}
