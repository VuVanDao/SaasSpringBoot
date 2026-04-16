package PersonalProject.demo.services;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import PersonalProject.demo.Dto.Request.CreateBranchRequest;
import PersonalProject.demo.Dto.Request.UpdateBranchRequest;
import PersonalProject.demo.Dto.Response.BranchDto;
import PersonalProject.demo.Dto.Response.StoreDto;
import jakarta.servlet.http.HttpServletRequest;

public interface BranchService {

    @PreAuthorize("hasAnyAuthority('ROLE_STORE_MANAGER')")
    BranchDto createBranch(CreateBranchRequest request,HttpServletRequest request2);

    BranchDto getBranchById(Long id, HttpServletRequest request2);

    List<BranchDto> getAllBranches(HttpServletRequest request);

    @PreAuthorize("hasAnyAuthority('ROLE_STORE_MANAGER')")
    BranchDto updateBranch(Long id, UpdateBranchRequest request, HttpServletRequest request2);

    void deleteBranch(Long id, HttpServletRequest request2);

    // List<StoreDto> getAllStoresByBranchId(Long branchId);
}
