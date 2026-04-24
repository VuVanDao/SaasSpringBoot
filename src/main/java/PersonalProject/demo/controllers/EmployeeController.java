package PersonalProject.demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Request.CreateEmployeeRequest;
import PersonalProject.demo.Dto.Request.UpdateEmployeeRequest;
import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.EmployeeDto;
import PersonalProject.demo.services.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public ApiResponse<EmployeeDto> createEmployee(@Valid @RequestBody CreateEmployeeRequest createRequest,
            HttpServletRequest request) {
        return ApiResponse.<EmployeeDto>builder()
                .message("Create Employee complete")
                .result(employeeService.createEmployee(createRequest, request))
                .code(200)
                .build();
    }
    
    @GetMapping("/store/{store_id}")
    public ApiResponse<List<EmployeeDto>> getEmployeesByStoreId(@PathVariable Long store_id, HttpServletRequest request) {
        return ApiResponse.<List<EmployeeDto>>builder()
                .message("Get Employees by Store ID complete")
                .result(employeeService.getAllEmployeesOfAStore(store_id, request))
                .code(200)
                .build();
    }

    @GetMapping("/{employee_id}")
    public ApiResponse<EmployeeDto> getEmployeeById(@PathVariable Long employee_id, HttpServletRequest request) {
        return ApiResponse.<EmployeeDto>builder()
                .message("Get Employee by ID complete")
                .result(employeeService.getEmployeeById(employee_id, request))
                .code(200)
                .build();
    }

    @GetMapping("/user/{user_id}")
    public ApiResponse<EmployeeDto> getEmployeeByUserId(@PathVariable Long user_id, HttpServletRequest request) {
        return ApiResponse.<EmployeeDto>builder()
                .message("Get Employee by User ID complete")
                .result(employeeService.getEmployeeByUserId(user_id, request))
                .code(200)
                .build();
    }

    @GetMapping("/branch/{branch_id}")
    public ApiResponse<List<EmployeeDto>> getEmployeeByBranchId(@PathVariable Long branch_id, HttpServletRequest request) {
        return ApiResponse.<List<EmployeeDto>>builder()
                .message("Get Employee by Branch ID complete")
                .result(employeeService.getEmployeeByBranchId(branch_id, request))
                .code(200)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<EmployeeDto> updateEmployee(@PathVariable Long id, @Valid @RequestBody UpdateEmployeeRequest updateRequest, HttpServletRequest request) {
        return ApiResponse.<EmployeeDto>builder()
                .message("Update Employee complete")
                .result(employeeService.UpdateEmployee(id, updateRequest, request))
                .code(200)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteEmployee(@PathVariable Long id, HttpServletRequest request) {
        employeeService.deleteEmployee(id, request);
        return ApiResponse.<Void>builder()
                .message("Delete Employee complete")
                .code(200)
                .build();
    }

}
