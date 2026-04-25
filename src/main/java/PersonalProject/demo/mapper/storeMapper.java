package PersonalProject.demo.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import PersonalProject.demo.Dto.Request.CreateStoreRequest;
import PersonalProject.demo.Dto.Request.CreateUserRequest;
import PersonalProject.demo.Dto.Response.CategoryResponse;
import PersonalProject.demo.Dto.Response.StoreDto;
import PersonalProject.demo.Dto.Response.UserDto;
import PersonalProject.demo.models.Store;
import PersonalProject.demo.models.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class storeMapper {
    private final userMapper userMapper;
    private final BranchMapper branchMapper;

    public StoreDto convertToDto(Store store) {
        StoreDto storeDto = StoreDto.builder()
                .id(store.getId())
                .brand(store.getBrand())
                .description(store.getDescription())
                .storeAdmin(userMapper.convertToDto(store.getStoreAdmin()))
                // .storeAdmin(store.getStoreAdmin().stream().map(userMapper::convertToDto).toList())
                .storeContact(store.getStoreContact())
                .storeStatus(store.getStoreStatus())
                .build();
        if (store.getCategories() != null) {
            storeDto.setCategories(store.getCategories().stream().map(category -> CategoryResponse.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .build()).collect(Collectors.toSet()));
        }
        if (store.getBranches() != null) {
            storeDto.setBranches(store.getBranches().stream().map(branchMapper::convertToDto).collect(Collectors.toList()));
        }
        return storeDto;
    }
    // public Store convertToModel(CreateStoreRequest storeDto, UserDto userDto) {
    public Store convertToModel(CreateStoreRequest storeDto, User user) {
        return Store.builder()
                .brand(storeDto.getBrand())
                .description(storeDto.getDescription())
                // .storeAdmin(userMapper.convertDtoToModel(userDto))
                .storeAdmin(user)
                .storeContact(storeDto.getStoreContact())
                .storeStatus(storeDto.getStoreStatus())
                .tenantId(storeDto.getTenantId())
                .build();
    }
}
