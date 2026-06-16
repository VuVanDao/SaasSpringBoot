package PersonalProject.demo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<BranchDto>> createBranch(
            @RequestBody CreateBranchRequest request,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        BranchDto branchDto = branchService.createBranch(request, tenantId);
        ApiResponse<BranchDto> response = ApiResponse.<BranchDto>builder()
                .code(HttpStatus.CREATED.value())
                .result(branchDto)
                .message("Branch created successfully")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BranchDto>> getBranchById(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        BranchDto branchDto = branchService.getBranchById(id, tenantId);
        ApiResponse<BranchDto> response = ApiResponse.<BranchDto>builder()
                .code(HttpStatus.OK.value())
                .result(branchDto)
                .message("Branch retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BranchDto>>> getAllBranches(
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        List<BranchDto> branches = branchService.getAllBranches(tenantId);
        ApiResponse<List<BranchDto>> response = ApiResponse.<List<BranchDto>>builder()
                .code(HttpStatus.OK.value())
                .result(branches)
                .message("Branches retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BranchDto>> updateBranch(
            @PathVariable Long id,
            @RequestBody UpdateBranchRequest request,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        BranchDto updated = branchService.updateBranch(id, request, tenantId);
        ApiResponse<BranchDto> response = ApiResponse.<BranchDto>builder()
                .code(HttpStatus.OK.value())
                .result(updated)
                .message("Branch updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBranch(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        branchService.deleteBranch(id, tenantId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .result("Branch deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}
