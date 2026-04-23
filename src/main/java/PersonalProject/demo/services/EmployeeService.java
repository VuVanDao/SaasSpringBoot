package PersonalProject.demo.services;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import PersonalProject.demo.Dto.Request.CreateEmployeeRequest;
import PersonalProject.demo.Dto.Request.UpdateEmployeeRequest;
import PersonalProject.demo.Dto.Response.EmployeeDto;
import jakarta.servlet.http.HttpServletRequest;

public interface EmployeeService {
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    EmployeeDto createEmployee(CreateEmployeeRequest createEmployeeRequest, HttpServletRequest request);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    List<EmployeeDto> getAllEmployeesOfAStore(Long store_id, HttpServletRequest request);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    EmployeeDto getEmployeeById(Long id, HttpServletRequest request);
    
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    EmployeeDto getEmployeeByUserId(Long userId, HttpServletRequest request);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    EmployeeDto getEmployeeByBranchId(Long branchId, HttpServletRequest request);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    EmployeeDto UpdateEmployee(Long employeeId, UpdateEmployeeRequest updateEmployeeRequest,
            HttpServletRequest request);
    
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    void deleteEmployee(Long employeeId, HttpServletRequest request);
}
