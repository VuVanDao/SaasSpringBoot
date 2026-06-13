package PersonalProject.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Request.CreateStoreRequest;
import PersonalProject.demo.Dto.Request.UpdateStoreRequest;
import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.StoreDto;
import PersonalProject.demo.Enums.StoreStatus;
import PersonalProject.demo.configuration.JwtConstant;
import PersonalProject.demo.services.StoreService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final String HeaderKey = JwtConstant.JWT_HEADER;

    @PostMapping
    public ApiResponse<StoreDto> createStore(@RequestBody CreateStoreRequest request,HttpServletRequest request2) {
        StoreDto storeDto = storeService.createStore(request, request2);
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
    public ApiResponse<List<StoreDto>> getAllStores(HttpServletRequest request) {
        List<StoreDto> stores = storeService.getAllStores(request);
        return ApiResponse.<List<StoreDto>>builder()
                .code(HttpStatus.OK.value())
                .result(stores)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<StoreDto> updateStore(@PathVariable Long id, @RequestBody UpdateStoreRequest storeDto,  HttpServletRequest request) {
        StoreDto updatedStore = storeService.updateStore(id, storeDto, request);
        return ApiResponse.<StoreDto>builder()
                .code(HttpStatus.OK.value())
                .result(updatedStore)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteStore(@PathVariable Long id, HttpServletRequest request) {
        storeService.deleteStore(id, request);
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .result("Store deleted successfully")
                .build();
    }

    @GetMapping("/employee")
    public ApiResponse<StoreDto> getStoreByEmployee(@RequestHeader(HeaderKey) String jwt, HttpServletRequest request) {
        // Assuming the service uses the current user from JWT
        StoreDto storeDto = storeService.getStoreByEmployee(request);
        return ApiResponse.<StoreDto>builder()
                .code(HttpStatus.OK.value())
                .result(storeDto)
                .build();
    }

    @PatchMapping("/update_status/{id}")
    public ApiResponse<StoreDto> updateStatusStore(@PathVariable Long id, @RequestBody UpdateStoreRequest storeStatus,
            HttpServletRequest request) {
        StoreDto updatedStore = storeService.moderateStore(id, storeStatus.getStoreStatus(), request);
        return ApiResponse.<StoreDto>builder()
                .code(HttpStatus.OK.value())
                .message("Update store status complete")
                .result(updatedStore)
                .build();
    }
    
    @GetMapping("/store-manager")
    public ApiResponse<StoreDto> getStoreByStoreManager(@RequestHeader(HeaderKey) String jwt, HttpServletRequest request) {
        // Assuming the service uses the current user from JWT
        StoreDto storeDto = storeService.getStoreByStoreManager(request);
        return ApiResponse.<StoreDto>builder()
                .code(HttpStatus.OK.value())
                .result(storeDto)
                .build();
    }
}
