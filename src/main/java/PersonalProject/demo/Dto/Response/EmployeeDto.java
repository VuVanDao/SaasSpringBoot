package PersonalProject.demo.Dto.Response;

import java.math.BigDecimal;

import PersonalProject.demo.Enums.EmployeeRole;
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
public class EmployeeDto {
    Long id;
    Long userId;
    String fullName;
    String email;
    String phone;
    String employeeCode;
    Long storeId;
    String storeBrand;
    Long branchId;
    String branchName;
    EmployeeRole employeeRole;
    BigDecimal salary;
}
