package PersonalProject.demo.mapper;

import org.springframework.stereotype.Component;

import PersonalProject.demo.Dto.Request.CreateStoreRequest;
import PersonalProject.demo.Dto.Request.CreateUserRequest;
import PersonalProject.demo.Dto.Response.StoreDto;
import PersonalProject.demo.Dto.Response.UserDto;
import PersonalProject.demo.models.Store;
import PersonalProject.demo.models.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class storeMapper {
    private final userMapper userMapper;
    public StoreDto convertToDto(Store store) {
        return StoreDto.builder()
                .id(store.getId())
                .brand(store.getBrand())
                .description(store.getDescription())
                .storeAdmin(userMapper.convertToDto(store.getStoreAdmin()))
                // .storeAdmin(store.getStoreAdmin().stream().map(userMapper::convertToDto).toList())
                .storeContact(store.getStoreContact())
                .storeType(store.getStoreType())
                .storeStatus(store.getStoreStatus())
                .build();
    }
    // public Store convertToModel(CreateStoreRequest storeDto, UserDto userDto) {
    public Store convertToModel(CreateStoreRequest storeDto, User user) {
        return Store.builder()
                .brand(storeDto.getBrand())
                .description(storeDto.getDescription())
                // .storeAdmin(userMapper.convertDtoToModel(userDto))
                .storeAdmin(user)
                .storeContact(storeDto.getStoreContact())
                .storeType(storeDto.getStoreType())
                .storeStatus(storeDto.getStoreStatus())
                .build();
    }
}
