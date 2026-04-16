package PersonalProject.demo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Request.CreateBranchRequest;
import PersonalProject.demo.Dto.Request.UpdateBranchRequest;
import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.BranchDto;
import PersonalProject.demo.Dto.Response.StoreDto;
import PersonalProject.demo.services.BranchService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;

    @PostMapping
    public ApiResponse<BranchDto> createBranch(@RequestBody CreateBranchRequest request,HttpServletRequest request2) {
        BranchDto branchDto = branchService.createBranch(request, request2);
        return ApiResponse.<BranchDto>builder()
                .code(HttpStatus.CREATED.value())
                .result(branchDto)
                .message("Branch created successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BranchDto> getBranchById(@PathVariable Long id,HttpServletRequest request2) {
        BranchDto branchDto = branchService.getBranchById(id, request2);
        return ApiResponse.<BranchDto>builder()
                .code(HttpStatus.OK.value())
                .result(branchDto)
                .message("Branch retrieved successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<BranchDto>> getAllBranches(HttpServletRequest request2) {
        List<BranchDto> branches = branchService.getAllBranches(request2);
        return ApiResponse.<List<BranchDto>>builder()
                .code(HttpStatus.OK.value())
                .result(branches)
                .message("Branches retrieved successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<BranchDto> updateBranch(@PathVariable Long id, @RequestBody UpdateBranchRequest request, HttpServletRequest request2) {
        BranchDto updated = branchService.updateBranch(id, request, request2);
        return ApiResponse.<BranchDto>builder()
                .code(HttpStatus.OK.value())
                .result(updated)
                .message("Branch updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteBranch(@PathVariable Long id, HttpServletRequest request2) {
        branchService.deleteBranch(id, request2);
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .result("Branch deleted successfully")
                .build();
    }

    // @GetMapping("/{id}/stores")
    // public ApiResponse<List<StoreDto>> getAllStoresByBranchId(@PathVariable Long id) {
    //     List<StoreDto> stores = branchService.getAllStoresByBranchId(id);
    //     return ApiResponse.<List<StoreDto>>builder()
    //             .code(HttpStatus.OK.value())
    //             .result(stores)
    //             .message("Stores by branch retrieved successfully")
    //             .build();
    // }
}
