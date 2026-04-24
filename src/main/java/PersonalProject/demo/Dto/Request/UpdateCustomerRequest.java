package PersonalProject.demo.Dto.Request;
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
public class UpdateCustomerRequest {
    String fullName;
    String email;
    String phone;
    String avatar;
    String address;
    Long tenantId;
}
