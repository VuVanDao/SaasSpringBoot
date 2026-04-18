package PersonalProject.demo.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RefreshTokenRequest {
    @NotBlank(message = "missing token to refresh")
    private String token;
}
