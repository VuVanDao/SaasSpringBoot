package PersonalProject.demo.Dto.Request;

import java.time.LocalDateTime;

import PersonalProject.demo.domain.UserRole;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    String fullName;

    @Column(nullable = false, unique = true)
    @Email(message = "Email is not valid")
    String email;

    String phone;

    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    String password;

    UserRole role;
    
    LocalDateTime lastLogin;
}
