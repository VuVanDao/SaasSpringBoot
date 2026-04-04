package PersonalProject.demo.Dto.Request;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBranchRequest {
    String name;
    String address;
    String phone;
    String email;
    String workingDay;
    LocalTime openingTime;
    LocalTime closingTime;
    Long managerId;
}
