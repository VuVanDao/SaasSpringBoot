package PersonalProject.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Request.CreateEmployeeRequest;
import PersonalProject.demo.Dto.Request.UpdateEmployeeRequest;
import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.EmployeeDto;
import PersonalProject.demo.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/employees")
    public ResponseEntity<ApiResponse<EmployeeDto>> createEmployee(
            @Valid @RequestBody CreateEmployeeRequest createRequest,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        ApiResponse<EmployeeDto> response = ApiResponse.<EmployeeDto>builder()
                .message("Create Employee complete")
                .result(employeeService.createEmployee(createRequest, tenantId))
                .code(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/stores/{storeId}/employees")
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> getEmployeesByStoreId(
            @PathVariable Long storeId,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        ApiResponse<List<EmployeeDto>> response = ApiResponse.<List<EmployeeDto>>builder()
                .message("Get Employees by Store ID complete")
                .result(employeeService.getAllEmployeesOfAStore(storeId, tenantId))
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> getEmployeeById(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        ApiResponse<EmployeeDto> response = ApiResponse.<EmployeeDto>builder()
                .message("Get Employee by ID complete")
                .result(employeeService.getEmployeeById(id, tenantId))
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}/employees")
    public ResponseEntity<ApiResponse<EmployeeDto>> getEmployeeByUserId(
            @PathVariable Long userId,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        ApiResponse<EmployeeDto> response = ApiResponse.<EmployeeDto>builder()
                .message("Get Employee by User ID complete")
                .result(employeeService.getEmployeeByUserId(userId, tenantId))
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/branches/{branchId}/employees")
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> getEmployeeByBranchId(
            @PathVariable Long branchId,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        ApiResponse<List<EmployeeDto>> response = ApiResponse.<List<EmployeeDto>>builder()
                .message("Get Employee by Branch ID complete")
                .result(employeeService.getEmployeeByBranchId(branchId, tenantId))
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEmployeeRequest updateRequest,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        ApiResponse<EmployeeDto> response = ApiResponse.<EmployeeDto>builder()
                .message("Update Employee complete")
                .result(employeeService.UpdateEmployee(id, updateRequest, tenantId))
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        employeeService.deleteEmployee(id, tenantId);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Delete Employee complete")
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }
}
