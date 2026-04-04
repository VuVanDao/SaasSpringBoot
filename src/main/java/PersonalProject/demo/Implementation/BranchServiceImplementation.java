package PersonalProject.demo.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import PersonalProject.demo.Dto.Request.CreateBranchRequest;
import PersonalProject.demo.Dto.Request.UpdateBranchRequest;
import PersonalProject.demo.Dto.Response.BranchDto;
import PersonalProject.demo.Dto.Response.StoreDto;
import PersonalProject.demo.exception.ResourceNotFoundException;
import PersonalProject.demo.mapper.BranchMapper;
import PersonalProject.demo.mapper.storeMapper;
import PersonalProject.demo.models.Branch;
import PersonalProject.demo.models.User;
import PersonalProject.demo.repositories.BranchRepository;
import PersonalProject.demo.repositories.StoreRepositories;
import PersonalProject.demo.repositories.UserRepository;
import PersonalProject.demo.services.BranchService;
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

    @Override
    public BranchDto createBranch(CreateBranchRequest request) {
        User manager = null;
        if (request.getManagerId() != null) {
            manager = userRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + request.getManagerId()));
        }

        Branch branch = branchMapper.convertToModel(request, manager);
        branchRepository.save(branch);
        return branchMapper.convertToDto(branch);
    }

    @Override
    public BranchDto getBranchById(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + id));
        return branchMapper.convertToDto(branch);
    }

    @Override
    public List<BranchDto> getAllBranches() {
        return branchRepository.findAll().stream()
                .map(branchMapper::convertToDto)
                .toList();
    }

    @Override
    public BranchDto updateBranch(Long id, UpdateBranchRequest request) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + id));

        User manager = null;
        if (request.getManagerId() != null) {
            manager = userRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + request.getManagerId()));
        }

        branchMapper.convertToModel(request, branch, manager);
        branchRepository.save(branch);
        return branchMapper.convertToDto(branch);
    }

    @Override
    @Transactional
    public void deleteBranch(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + id));
        if (branch.getManager() != null) {
            branch.getManager().setBranch(null);
            userRepository.save(branch.getManager());
        }
        branch.setManager(null);
        branchRepository.delete(branch);
    }

    @Override
    public List<StoreDto> getAllStoresByBranchId(Long branchId) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + branchId));

        return storeRepositories.findByBranchId(branch.getId()).stream()
                .map(storeMapper::convertToDto)
                .toList();
    }
}
