package PersonalProject.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Request.CreateStoreRequest;
import PersonalProject.demo.Dto.Request.UpdateStoreRequest;
import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.StoreDto;
import PersonalProject.demo.services.StoreService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // DES: Tạo store (cửa hàng) mới
    @PostMapping
    public ResponseEntity<ApiResponse<StoreDto>> createStore(
            @RequestBody CreateStoreRequest request,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        StoreDto storeDto = storeService.createStore(request, tenantId);
        ApiResponse<StoreDto> response = ApiResponse.<StoreDto>builder()
                .code(HttpStatus.CREATED.value())
                .result(storeDto)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // DES: Lấy thông tin chi tiết của store bằng ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StoreDto>> getStoreById(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        StoreDto storeDto = storeService.getStoreById(id, tenantId);
        ApiResponse<StoreDto> response = ApiResponse.<StoreDto>builder()
                .code(HttpStatus.OK.value())
                .result(storeDto)
                .build();
        return ResponseEntity.ok(response);
    }

    // DES: Lấy danh sách tất cả store
    @GetMapping
    public ResponseEntity<ApiResponse<List<StoreDto>>> getAllStores(
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        List<StoreDto> stores = storeService.getAllStores(tenantId);
        ApiResponse<List<StoreDto>> response = ApiResponse.<List<StoreDto>>builder()
                .code(HttpStatus.OK.value())
                .result(stores)
                .build();
        return ResponseEntity.ok(response);
    }

    // DES: Cập nhật thông tin store
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StoreDto>> updateStore(
            @PathVariable Long id,
            @RequestBody UpdateStoreRequest storeDto,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        StoreDto updatedStore = storeService.updateStore(id, storeDto, tenantId);
        ApiResponse<StoreDto> response = ApiResponse.<StoreDto>builder()
                .code(HttpStatus.OK.value())
                .result(updatedStore)
                .build();
        return ResponseEntity.ok(response);
    }

    // DES: Xóa store
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteStore(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        storeService.deleteStore(id, tenantId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .result("Store deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    // DES: Lấy thông tin store của employee đăng nhập hiện tại
    @GetMapping("/me/employee")
    public ResponseEntity<ApiResponse<StoreDto>> getStoreByEmployee(
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        StoreDto storeDto = storeService.getStoreByEmployee(tenantId);
        ApiResponse<StoreDto> response = ApiResponse.<StoreDto>builder()
                .code(HttpStatus.OK.value())
                .result(storeDto)
                .build();
        return ResponseEntity.ok(response);
    }

    // DES: Cập nhật trạng thái store (moderate store)
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<StoreDto>> updateStatusStore(
            @PathVariable Long id,
            @RequestBody UpdateStoreRequest storeStatus,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        StoreDto updatedStore = storeService.moderateStore(id, storeStatus.getStoreStatus(), tenantId);
        ApiResponse<StoreDto> response = ApiResponse.<StoreDto>builder()
                .code(HttpStatus.OK.value())
                .message("Update store status complete")
                .result(updatedStore)
                .build();
        return ResponseEntity.ok(response);
    }
    
    // DES: Lấy thông tin store của store manager đăng nhập hiện tại
    @GetMapping("/me/store-manager")
    public ResponseEntity<ApiResponse<StoreDto>> getStoreByStoreManager(
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        StoreDto storeDto = storeService.getStoreByStoreManager(tenantId);
        ApiResponse<StoreDto> response = ApiResponse.<StoreDto>builder()
                .code(HttpStatus.OK.value())
                .result(storeDto)
                .build();
        return ResponseEntity.ok(response);
    }
}
