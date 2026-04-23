package PersonalProject.demo.Dto.Request;

import java.math.BigDecimal;

import PersonalProject.demo.domain.EmployeeRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeRequest {
    // CHECK: theem casi check cac field khasc null hay ko
    private Long branchId;
    private EmployeeRole employeeRole;
    private String email;
    private String phone;
    private BigDecimal salary;
    private String employeeCode;
    private Long user_id;
    private Long store_id;
}
