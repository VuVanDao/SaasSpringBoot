package PersonalProject.demo.Dto.Response;

import java.time.LocalDateTime;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import PersonalProject.demo.domain.UserRole;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;
    String fullName;
    String email;
    String phone;
    String password;
    UserRole role;
    LocalDateTime lastLogin;
    StoreDto store;
    Long tenantId;
}
