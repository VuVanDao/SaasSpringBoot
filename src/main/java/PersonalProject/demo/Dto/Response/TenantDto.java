package PersonalProject.demo.Dto.Response;

import java.time.LocalDateTime;

import PersonalProject.demo.domain.TenantStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TenantDto {
    Long id;
    String name;         
    String domain;       
    TenantStatus status;       
    LocalDateTime expiryDate;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
