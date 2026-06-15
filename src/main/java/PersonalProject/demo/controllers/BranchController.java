package PersonalProject.demo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Request.CreateBranchRequest;
import PersonalProject.demo.Dto.Request.UpdateBranchRequest;
import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.BranchDto;
import PersonalProject.demo.services.BranchService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;

    @PostMapping
    public ApiResponse<BranchDto> createBranch(
            @RequestBody CreateBranchRequest request,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        BranchDto branchDto = branchService.createBranch(request, tenantId);
        return ApiResponse.<BranchDto>builder()
                .code(HttpStatus.CREATED.value())
                .result(branchDto)
                .message("Branch created successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BranchDto> getBranchById(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        BranchDto branchDto = branchService.getBranchById(id, tenantId);
        return ApiResponse.<BranchDto>builder()
                .code(HttpStatus.OK.value())
                .result(branchDto)
                .message("Branch retrieved successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<BranchDto>> getAllBranches(
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        List<BranchDto> branches = branchService.getAllBranches(tenantId);
        return ApiResponse.<List<BranchDto>>builder()
                .code(HttpStatus.OK.value())
                .result(branches)
                .message("Branches retrieved successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<BranchDto> updateBranch(
            @PathVariable Long id,
            @RequestBody UpdateBranchRequest request,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        BranchDto updated = branchService.updateBranch(id, request, tenantId);
        return ApiResponse.<BranchDto>builder()
                .code(HttpStatus.OK.value())
                .result(updated)
                .message("Branch updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteBranch(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        branchService.deleteBranch(id, tenantId);
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .result("Branch deleted successfully")
                .build();
    }
}
