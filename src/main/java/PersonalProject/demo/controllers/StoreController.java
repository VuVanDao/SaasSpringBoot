package PersonalProject.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Request.CreateStoreRequest;
import PersonalProject.demo.Dto.Request.UpdateStoreRequest;
import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.StoreDto;
import PersonalProject.demo.configuration.JwtConstant;
import PersonalProject.demo.domain.StoreStatus;
import PersonalProject.demo.services.StoreService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final String HeaderKey = JwtConstant.JWT_HEADER;

    @PostMapping
    public ApiResponse<StoreDto> createStore(@RequestBody CreateStoreRequest request) {
        StoreDto storeDto = storeService.createStore(request);
        return ApiResponse.<StoreDto>builder()
                .code(HttpStatus.CREATED.value())
                .result(storeDto)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<StoreDto> getStoreById(@PathVariable Long id) {
        StoreDto storeDto = storeService.getStoreById(id);
        return ApiResponse.<StoreDto>builder()
                .code(HttpStatus.OK.value())
                .result(storeDto)
                .build();
    }

    @GetMapping
    public ApiResponse<List<StoreDto>> getAllStores() {
        List<StoreDto> stores = storeService.getAllStores();
        return ApiResponse.<List<StoreDto>>builder()
                .code(HttpStatus.OK.value())
                .result(stores)
                .build();
    }

    @GetMapping("/admin")
    public ApiResponse<StoreDto> getStoreByAdmin(@RequestHeader(HeaderKey) String jwt) {
        // Assuming the service uses the current user from JWT
        StoreDto storeDto = storeService.getStoreByAdmin();
        return ApiResponse.<StoreDto>builder()
                .code(HttpStatus.OK.value())
                .result(storeDto)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<StoreDto> updateStore(@PathVariable Long id, @RequestBody UpdateStoreRequest storeDto) {
        StoreDto updatedStore = storeService.updateStore(id, storeDto);
        return ApiResponse.<StoreDto>builder()
                .code(HttpStatus.OK.value())
                .result(updatedStore)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteStore(@PathVariable Long id) {
        storeService.deleteStore(id);
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .result("Store deleted successfully")
                .build();
    }

    @GetMapping("/employee")
    public ApiResponse<StoreDto> getStoreByEmployee(@RequestHeader(HeaderKey) String jwt) {
        // Assuming the service uses the current user from JWT
        StoreDto storeDto = storeService.getStoreByEmployee();
        return ApiResponse.<StoreDto>builder()
                .code(HttpStatus.OK.value())
                .result(storeDto)
                .build();
    }

    @PutMapping("/update_status/{id}")
    public ApiResponse<StoreDto> updateStatusStore(@PathVariable Long id, @RequestBody UpdateStoreRequest storeStatus) {
        StoreDto updatedStore = storeService.moderateStore(id, storeStatus.getStoreStatus());
        return ApiResponse.<StoreDto>builder()
                .code(HttpStatus.OK.value())
                .result(updatedStore)
                .build();
    }
}
