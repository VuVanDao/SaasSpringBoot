package PersonalProject.demo.services;


import java.util.List;

import PersonalProject.demo.Dto.Request.CreateStoreRequest;
import PersonalProject.demo.Dto.Request.CreateUserRequest;
import PersonalProject.demo.Dto.Request.UpdateStoreRequest;
import PersonalProject.demo.Dto.Response.StoreDto;
import PersonalProject.demo.domain.StoreStatus;

public interface StoreService {
    StoreDto createStore(CreateStoreRequest storeDto);

    StoreDto getStoreById(Long id);

    List<StoreDto> getAllStores();

    StoreDto getStoreByAdmin();

    StoreDto updateStore(Long id, UpdateStoreRequest storeDto);

    void deleteStore(Long id);

    StoreDto getStoreByEmployee();
    StoreDto moderateStore(Long id, StoreStatus storeStatus);
}
