package PersonalProject.demo.services;

import java.util.List;

import PersonalProject.demo.Dto.Request.CreateBranchRequest;
import PersonalProject.demo.Dto.Request.UpdateBranchRequest;
import PersonalProject.demo.Dto.Response.BranchDto;
import PersonalProject.demo.Dto.Response.StoreDto;

public interface BranchService {
    BranchDto createBranch(CreateBranchRequest request);

    BranchDto getBranchById(Long id);

    List<BranchDto> getAllBranches();

    BranchDto updateBranch(Long id, UpdateBranchRequest request);

    void deleteBranch(Long id);

    List<StoreDto> getAllStoresByBranchId(Long branchId);
}
