package PersonalProject.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Request.CreateCustomerRequest;
import PersonalProject.demo.Dto.Request.UpdateCustomerRequest;
import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.CustomerDto;
import PersonalProject.demo.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    // DES: Tạo khách hàng mới
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerDto>> createCustomer(
            @Valid @RequestBody CreateCustomerRequest entity,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        ApiResponse<CustomerDto> response = ApiResponse.<CustomerDto>builder()
                .message("Customer created successfully")
                .result(customerService.createCustomer(entity, tenantId))
                .code(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    // DES: Lấy thông tin chi tiết khách hàng bằng ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerDto>> getCustomer(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        ApiResponse<CustomerDto> response = ApiResponse.<CustomerDto>builder()
                .message("Customer retrieved successfully")
                .result(customerService.getCustomerById(id, tenantId))
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }
    
    // DES: Lấy danh sách tất cả khách hàng của tenant hiện tại
    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerDto>>> getAllCustomersByTenantId(
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        ApiResponse<List<CustomerDto>> response = ApiResponse.<List<CustomerDto>>builder()
                .message("Customers retrieved successfully")
                .result(customerService.getAllCustomersByTenantId(tenantId))
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }
    
    // DES: Cập nhật thông tin khách hàng
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerDto>> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCustomerRequest updateRequest,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        ApiResponse<CustomerDto> response = ApiResponse.<CustomerDto>builder()
                .message("Customer updated successfully")
                .result(customerService.updateCustomer(id, updateRequest, tenantId))
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }
    
    // DES: Xóa khách hàng
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        customerService.deleteCustomer(id, tenantId);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Customer deleted successfully")
                .result(null)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }
}
