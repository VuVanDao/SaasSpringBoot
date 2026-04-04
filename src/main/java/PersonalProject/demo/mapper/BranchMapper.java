package PersonalProject.demo.mapper;

import org.springframework.stereotype.Component;

import PersonalProject.demo.Dto.Request.CreateBranchRequest;
import PersonalProject.demo.Dto.Request.UpdateBranchRequest;
import PersonalProject.demo.Dto.Response.BranchDto;
import PersonalProject.demo.models.Branch;
import PersonalProject.demo.models.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BranchMapper {
    private final userMapper userMapper;

    public Branch convertToModel(CreateBranchRequest request, User manager) {
        return Branch.builder()
                .name(request.getName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .email(request.getEmail())
                .workingDay(request.getWorkingDay())
                .openingTime(request.getOpeningTime())
                .closingTime(request.getClosingTime())
                .manager(manager)
                .build();
    }

    public Branch convertToModel(UpdateBranchRequest request, Branch existing, User manager) {
        existing.setName(request.getName());
        existing.setAddress(request.getAddress());
        existing.setPhone(request.getPhone());
        existing.setEmail(request.getEmail());
        existing.setWorkingDay(request.getWorkingDay());
        existing.setOpeningTime(request.getOpeningTime());
        existing.setClosingTime(request.getClosingTime());
        existing.setManager(manager);
        return existing;
    }

    public BranchDto convertToDto(Branch branch) {
        return BranchDto.builder()
                .id(branch.getId())
                .name(branch.getName())
                .address(branch.getAddress())
                .phone(branch.getPhone())
                .email(branch.getEmail())
                .workingDay(branch.getWorkingDay())
                .openingTime(branch.getOpeningTime())
                .closingTime(branch.getClosingTime())
                .manager(branch.getManager() == null ? null : userMapper.convertToDto(branch.getManager()))
                .build();
    }
}
