package PersonalProject.demo.Dto.Request;

import java.math.BigDecimal;

import PersonalProject.demo.Enums.EmployeeRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeRequest {
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotNull(message = "Store ID is required")
    private Long storeId;
    
    private Long branchId;  // Có thể null nếu chưa phân ca
    
    @NotNull(message = "Employee Role is required")
    private EmployeeRole employeeRole;
    
    private String email;
    private String phone;
    private BigDecimal salary;// per month
    private String employeeCode;
}
