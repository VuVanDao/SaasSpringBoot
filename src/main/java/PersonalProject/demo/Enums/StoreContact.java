package PersonalProject.demo.Enums;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Embeddable
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class StoreContact {
    String address;
    String phone;
    @Email(message = "Invalid email format")
    String email;
}
