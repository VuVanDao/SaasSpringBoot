package PersonalProject.demo.services;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import PersonalProject.demo.Dto.Request.CreateEmployeeRequest;
import PersonalProject.demo.Dto.Request.UpdateEmployeeRequest;
import PersonalProject.demo.Dto.Response.EmployeeDto;

public interface EmployeeService {
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    EmployeeDto createEmployee(CreateEmployeeRequest createEmployeeRequest, Long tenantId);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    List<EmployeeDto> getAllEmployeesOfAStore(Long store_id, Long tenantId);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    EmployeeDto getEmployeeById(Long id, Long tenantId);
    
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    EmployeeDto getEmployeeByUserId(Long userId, Long tenantId);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    List<EmployeeDto> getEmployeeByBranchId(Long branchId, Long tenantId);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    EmployeeDto UpdateEmployee(Long employeeId, UpdateEmployeeRequest updateEmployeeRequest, Long tenantId);
    
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    void deleteEmployee(Long employeeId, Long tenantId);
}
