package PersonalProject.demo.services;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import PersonalProject.demo.Dto.Request.CreateBranchRequest;
import PersonalProject.demo.Dto.Request.UpdateBranchRequest;
import PersonalProject.demo.Dto.Response.BranchDto;

public interface BranchService {

    @PreAuthorize("hasAnyAuthority('ROLE_STORE_MANAGER')")
    BranchDto createBranch(CreateBranchRequest request, Long tenantId);

    BranchDto getBranchById(Long id, Long tenantId);

    List<BranchDto> getAllBranches(Long tenantId);

    @PreAuthorize("hasAnyAuthority('ROLE_STORE_MANAGER')")
    BranchDto updateBranch(Long id, UpdateBranchRequest request, Long tenantId);

    void deleteBranch(Long id, Long tenantId);
}
