package PersonalProject.demo.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import PersonalProject.demo.Dto.Request.CreateBranchRequest;
import PersonalProject.demo.Dto.Request.UpdateBranchRequest;
import PersonalProject.demo.Dto.Response.BranchDto;
import PersonalProject.demo.Dto.Response.StoreDto;
import PersonalProject.demo.domain.ErrorCode;
import PersonalProject.demo.domain.UserRole;
import PersonalProject.demo.exception.InvalidRoleException;
import PersonalProject.demo.exception.ResourceNotFoundException;
import PersonalProject.demo.exception.TenantException;
import PersonalProject.demo.exception.UserNotUnderPermission;
import PersonalProject.demo.mapper.BranchMapper;
import PersonalProject.demo.mapper.storeMapper;
import PersonalProject.demo.mapper.userMapper;
import PersonalProject.demo.models.Branch;
import PersonalProject.demo.models.Store;
import PersonalProject.demo.models.User;
import PersonalProject.demo.repositories.BranchRepository;
import PersonalProject.demo.repositories.StoreRepositories;
import PersonalProject.demo.repositories.UserRepository;
import PersonalProject.demo.services.BranchService;
import PersonalProject.demo.utils.TenantUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BranchServiceImplementation implements BranchService {
    private final BranchRepository branchRepository;
    private final StoreRepositories storeRepositories;
    private final UserRepository userRepository;
    private final storeMapper storeMapper;
    private final BranchMapper branchMapper;
    private final TenantUtil tenantUtil;
    private final userMapper userMapper;

    @Override
    public BranchDto createBranch(CreateBranchRequest request, HttpServletRequest request2) {
        Long tenantId = tenantUtil.validateTenant(request2);
        User manager = userRepository.findById(request.getManagerId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        if (manager.getRole() != UserRole.ROLE_BRANCH_MANAGER) {
            throw new InvalidRoleException(ErrorCode.USER_NOT_VALID_FOR_MANAGER);
        }
        if (manager.getTenantId() != tenantId) {
            throw new UserNotUnderPermission(ErrorCode.User_Not_Under_Your_Permission);
        }
        if (request.getTenant_id() == null || request.getTenant_id() != tenantId) {
            throw new TenantException(ErrorCode.Tenant_Exception);
        }
        if (request.getStore_id() == null) {
            throw new ResourceNotFoundException(ErrorCode.Resource_not_found);
        }
        Store store = storeRepositories.findById(request.getStore_id())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        if (store.getTenantId() != tenantId) {
            throw new UserNotUnderPermission(ErrorCode.Store_Not_Under_Your_Permission);
        }
        Branch branch = branchMapper.convertToModel(request, manager,store);
        branchRepository.save(branch);
        return branchMapper.convertToDto(branch);
    }

    @Override
    public BranchDto getBranchById(Long id, HttpServletRequest request2) {
        Long tenantId = tenantUtil.validateTenant(request2);
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
        return branchMapper.convertToDto(branch);
    }

    @Override
    public List<BranchDto> getAllBranches(HttpServletRequest request2) {
        Long tenantId = tenantUtil.validateTenant(request2);
        return branchRepository.findAll().stream()
                .map(branchMapper::convertToDto)
                .toList();
    }

    @Override
    public BranchDto updateBranch(Long id, UpdateBranchRequest request, HttpServletRequest request2) {
        Long tenantId = tenantUtil.validateTenant(request2);

        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
        if (branch.getTenantId() != tenantId) {
            throw new TenantException(ErrorCode.Tenant_Exception);
        }
        User manager = branch.getManager();
        if (request.getManagerId() != null && request.getManagerId() != manager.getId()) {
            manager = userRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
            if (manager.getBranch() != null) {
                throw new RuntimeException("User already is branch manager");
            }
        }
        if (manager.getRole() != UserRole.ROLE_BRANCH_MANAGER) {
            throw new InvalidRoleException(ErrorCode.USER_NOT_VALID_FOR_MANAGER);
        }
        if (manager.getTenantId() != tenantId) {
            throw new UserNotUnderPermission(ErrorCode.User_Not_Under_Your_Permission);
        }

        Store store = branch.getStore();
        if (request.getStore_id() != null && request.getStore_id() != store.getId()) {
            store = storeRepositories.findById(request.getStore_id())
                    .orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
        }
        if (store.getTenantId() != tenantId) {
            throw new UserNotUnderPermission(ErrorCode.Store_Not_Under_Your_Permission);
        }
        branchMapper.convertToModel(request, branch, manager,store);
        branchRepository.save(branch);
        return branchMapper.convertToDto(branch);
    }

    @Override
    @Transactional
    public void deleteBranch(Long id, HttpServletRequest request2) {
        Long tenantId = tenantUtil.validateTenant(request2);
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
        if (branch.getTenantId() != tenantId) {
            throw new TenantException(ErrorCode.Tenant_Exception);
        }
        if (branch.getManager() != null) {
            branch.getManager().setBranch(null);
            userRepository.save(branch.getManager());
        }
        branch.setManager(null);
        branchRepository.delete(branch);
    }

    // @Override
    // public List<StoreDto> getAllStoresByBranchId(Long branchId) {
    //     Branch branch = branchRepository.findById(branchId)
    //             .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + branchId));

    //     return storeRepositories.findByBranchId(branch.getId()).stream()
    //             .map(storeMapper::convertToDto)
    //             .toList();
    // }
}
