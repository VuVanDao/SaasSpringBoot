package PersonalProject.demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Request.CreateCustomerRequest;
import PersonalProject.demo.Dto.Request.UpdateCustomerRequest;
import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.CustomerDto;
import PersonalProject.demo.services.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ApiResponse<CustomerDto> createCustomer(@Valid @RequestBody CreateCustomerRequest entity,
            HttpServletRequest request) {

        return ApiResponse.<CustomerDto>builder()
                .message("Customer created successfully")
                .result(customerService.createCustomer(entity, request))
                .code(201)
                .build();
    }
    
    @GetMapping("/{customer_id}")
    public ApiResponse<CustomerDto> getCustomer(@PathVariable Long customer_id, HttpServletRequest request) {
        return ApiResponse.<CustomerDto>builder()
                .message("Customer retrieved successfully")
                .result(customerService.getCustomerById(customer_id, request))
                .code(200)
                .build();
    }
    
    @GetMapping
    public ApiResponse<List<CustomerDto>> getAllCustomersByTenantId(HttpServletRequest request) {
        return ApiResponse.<List<CustomerDto>>builder()
                .message("Customers retrieved successfully")
                .result(customerService.getAllCustomersByTenantId(request))
                .code(200)
                .build();
    }
    
    @PutMapping("/{customer_id}")
    public ApiResponse<CustomerDto> updateCustomer(@PathVariable Long customer_id,
            @Valid @RequestBody UpdateCustomerRequest updateRequest, HttpServletRequest request) {
        return ApiResponse.<CustomerDto>builder()
                .message("Customer updated successfully")
                .result(customerService.updateCustomer(customer_id, updateRequest, request))
                .code(200)
                .build();
    }
    
    @DeleteMapping("/{customer_id}")
    public ApiResponse<Void> deleteCustomer(@PathVariable Long customer_id, HttpServletRequest request) {
        customerService.deleteCustomer(customer_id, request);
        return ApiResponse.<Void>builder()
                .message("Customer deleted successfully")
                .result(null)
                .code(200)
                .build();
    }
}
