package PersonalProject.demo.Dto.Request;

import PersonalProject.demo.Enums.UserRole;
import jakarta.validation.constraints.Email;
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
public class UpdateProfileRequest {
    private String fullName;

    @Email(message = "Email không hợp lệ")
    private String email;

    private String phone;

    private UserRole role;
}