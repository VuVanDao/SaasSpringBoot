package PersonalProject.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Request.CreateStoreRequest;
import PersonalProject.demo.Dto.Request.UpdateStoreRequest;
import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.StoreDto;
import PersonalProject.demo.Enums.StoreStatus;
import PersonalProject.demo.services.StoreService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @PostMapping
    public ApiResponse<StoreDto> createStore(
            @RequestBody CreateStoreRequest request,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        StoreDto storeDto = storeService.createStore(request, tenantId);
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

    @GetMapping("/admin")
    public ApiResponse<List<StoreDto>> getAllStores(
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        List<StoreDto> stores = storeService.getAllStores(tenantId);
        return ApiResponse.<List<StoreDto>>builder()
                .code(HttpStatus.OK.value())
                .result(stores)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<StoreDto> updateStore(
            @PathVariable Long id,
            @RequestBody UpdateStoreRequest storeDto,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        StoreDto updatedStore = storeService.updateStore(id, storeDto, tenantId);
        return ApiResponse.<StoreDto>builder()
                .code(HttpStatus.OK.value())
                .result(updatedStore)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteStore(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        storeService.deleteStore(id, tenantId);
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .result("Store deleted successfully")
                .build();
    }

    @GetMapping("/employee")
    public ApiResponse<StoreDto> getStoreByEmployee(
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        StoreDto storeDto = storeService.getStoreByEmployee(tenantId);
        return ApiResponse.<StoreDto>builder()
                .code(HttpStatus.OK.value())
                .result(storeDto)
                .build();
    }

    @PatchMapping("/update_status/{id}")
    public ApiResponse<StoreDto> updateStatusStore(
            @PathVariable Long id,
            @RequestBody UpdateStoreRequest storeStatus,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        StoreDto updatedStore = storeService.moderateStore(id, storeStatus.getStoreStatus(), tenantId);
        return ApiResponse.<StoreDto>builder()
                .code(HttpStatus.OK.value())
                .message("Update store status complete")
                .result(updatedStore)
                .build();
    }
    
    @GetMapping("/store-manager")
    public ApiResponse<StoreDto> getStoreByStoreManager(
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        StoreDto storeDto = storeService.getStoreByStoreManager(tenantId);
        return ApiResponse.<StoreDto>builder()
                .code(HttpStatus.OK.value())
                .result(storeDto)
                .build();
    }
}
