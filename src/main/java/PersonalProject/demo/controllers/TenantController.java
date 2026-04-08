package PersonalProject.demo.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/tenants")
@RequiredArgsConstructor
public class TenantController {
    private final TenantService tenantService;

    @PostMapping
    public ApiResponse<TenantDto> createTenant(@Valid @RequestBody CreateTenantRequest request) {
        TenantDto tenantDto = tenantService.createTenant(request);
        return ApiResponse.<TenantDto>builder()
                .result(tenantDto)
                .build();
    }
    
    @GetMapping
    public ApiResponse<List<TenantDto>> getAllTenant() {
        return ApiResponse.<List<TenantDto>>builder()
                .result(tenantService.getAllTenants())
                .build();
    }

    @GetMapping("/{tenantId}")
    public ApiResponse<TenantDto> getTenantById(@PathVariable Long tenantId) {
        return ApiResponse.<TenantDto>builder()
                .result(tenantService.getTenantById(tenantId))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<TenantDto> updateTenant(@PathVariable Long id, @Valid @RequestBody CreateTenantRequest entity) {
        return ApiResponse.<TenantDto>builder()
                .result(tenantService.updateTenant(id, entity))
                .build();
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteTenant(@PathVariable Long id) {
        tenantService.deleteTenant(id);
        return ApiResponse.<String>builder()
                .result("delete complete")
                .build();
    }
}
