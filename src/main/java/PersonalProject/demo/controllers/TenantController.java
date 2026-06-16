package PersonalProject.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Request.CreateTenantRequest;
import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.TenantDto;
import PersonalProject.demo.services.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/tenants")
@RequiredArgsConstructor
public class TenantController {
    private final TenantService tenantService;

    // DES: Tạo tenant (đối tượng kinh doanh) mới
    @PostMapping
    public ResponseEntity<ApiResponse<TenantDto>> createTenant(@Valid @RequestBody CreateTenantRequest request) {
        TenantDto tenantDto = tenantService.createTenant(request);
        ApiResponse<TenantDto> response = ApiResponse.<TenantDto>builder()
                .code(HttpStatus.CREATED.value())
                .result(tenantDto)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    // DES: Lấy danh sách tất cả các tenant
    @GetMapping
    public ResponseEntity<ApiResponse<List<TenantDto>>> getAllTenant() {
        ApiResponse<List<TenantDto>> response = ApiResponse.<List<TenantDto>>builder()
                .code(HttpStatus.OK.value())
                .result(tenantService.getAllTenants())
                .build();
        return ResponseEntity.ok(response);
    }

    // DES: Lấy thông tin chi tiết của một tenant bằng ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TenantDto>> getTenantById(@PathVariable Long id) {
        ApiResponse<TenantDto> response = ApiResponse.<TenantDto>builder()
                .code(HttpStatus.OK.value())
                .result(tenantService.getTenantById(id))
                .build();
        return ResponseEntity.ok(response);
    }

    // DES: Cập nhật thông tin tenant
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TenantDto>> updateTenant(@PathVariable Long id, @Valid @RequestBody CreateTenantRequest entity) {
        ApiResponse<TenantDto> response = ApiResponse.<TenantDto>builder()
                .code(HttpStatus.OK.value())
                .result(tenantService.updateTenant(id, entity))
                .build();
        return ResponseEntity.ok(response);
    }
    
    // DES: Xóa tenant
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTenant(@PathVariable Long id) {
        tenantService.deleteTenant(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .result("delete complete")
                .build();
        return ResponseEntity.ok(response);
    }
}
