package PersonalProject.demo.Dto.Response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDto {
    Long id;
    String fullName;
    String email;
    String phone;
    String avatar;
    String address;
    Long tenantId;
}
