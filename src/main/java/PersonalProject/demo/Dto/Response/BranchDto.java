package PersonalProject.demo.Dto.Response;

import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BranchDto {
    Long id;
    String name;
    String address;
    String phone;
    String email;
    String workingDay;
    LocalTime openingTime;
    LocalTime closingTime;
    UserDto manager;
    Long tenant_id;
}
