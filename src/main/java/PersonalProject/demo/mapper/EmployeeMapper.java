package PersonalProject.demo.mapper;

import org.springframework.stereotype.Component;

import PersonalProject.demo.Dto.Request.CreateEmployeeRequest;
import PersonalProject.demo.Dto.Request.UpdateEmployeeRequest;
import PersonalProject.demo.Dto.Response.EmployeeDto;
import PersonalProject.demo.models.Branch;
import PersonalProject.demo.models.Employee;
import PersonalProject.demo.models.Store;
import PersonalProject.demo.models.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {
    public EmployeeDto toEmployeeDto(Employee employee) {
        if (employee == null) {
            return null;
        }
        return EmployeeDto.builder()
                .id(employee.getId())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .employeeCode(employee.getEmployeeCode())
                .salary(employee.getSalary())
                .employeeRole(employee.getEmployeeRole())
                .storeId(employee.getStore().getId())
                .branchId(employee.getBranch() != null ? employee.getBranch().getId() : null)
                .branchName(employee.getBranch() != null ? employee.getBranch().getName() : null)
                .userId(employee.getUser().getId())
                .storeBrand(employee.getStore().getBrand())
                .fullName(employee.getUser().getFullName())
                .build();
    }

    public Employee toEmployee(EmployeeDto employeeDto) {
        if (employeeDto == null) {
            return null;
        }
        return Employee.builder()
                .id(employeeDto.getId())
                .email(employeeDto.getEmail())
                .phone(employeeDto.getPhone())
                .employeeCode(employeeDto.getEmployeeCode())
                .salary(employeeDto.getSalary())
                .employeeRole(employeeDto.getEmployeeRole())
                // Store, Branch, User sẽ được set ở service sau khi lấy từ DB
                .build();
    }

    public Employee toModel(CreateEmployeeRequest createEmployeeRequest, Store store, Branch branch, User user,
            Long tenantId) {
        if (createEmployeeRequest == null) {
            return null;
        }
        return Employee.builder()
                .email(createEmployeeRequest.getEmail())
                .phone(createEmployeeRequest.getPhone())
                .employeeCode(createEmployeeRequest.getEmployeeCode())
                .salary(createEmployeeRequest.getSalary())
                .employeeRole(createEmployeeRequest.getEmployeeRole())
                .store(store)
                .branch(branch)
                .user(user)
                .tenantId(tenantId)
                .build();
    }
    
        public Employee toUpdateModel(UpdateEmployeeRequest updateEmployeeRequest, Store store, Branch branch, User user,
            Long tenantId) {
        if (updateEmployeeRequest == null) {
            return null;
        }
        return Employee.builder()
                .email(updateEmployeeRequest.getEmail())
                .phone(updateEmployeeRequest.getPhone())
                .employeeCode(updateEmployeeRequest.getEmployeeCode())
                .salary(updateEmployeeRequest.getSalary())
                .employeeRole(updateEmployeeRequest.getEmployeeRole())
                .branch(branch)
                .tenantId(tenantId)
                .store(store)
                .user(user)
                .build();
    }
}
