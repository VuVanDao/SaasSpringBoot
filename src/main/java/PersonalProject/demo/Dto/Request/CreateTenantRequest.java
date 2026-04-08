package PersonalProject.demo.Dto.Request;

import java.time.LocalDateTime;

import PersonalProject.demo.domain.TenantStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTenantRequest {
    @NotBlank(message = "Tên công ty không được để trống")
    String name;
    @NotBlank(message = "Domain không được để trống")       
    String domain;       
    TenantStatus status = TenantStatus.ACTIVE; // Mặc định là ACTIVE  
    LocalDateTime expiryDate; 
}
