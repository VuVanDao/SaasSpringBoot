package PersonalProject.demo.services;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import PersonalProject.demo.Dto.Request.CreateCustomerRequest;
import PersonalProject.demo.Dto.Request.UpdateCustomerRequest;
import PersonalProject.demo.Dto.Response.CustomerDto;
import jakarta.servlet.http.HttpServletRequest;

public interface CustomerService {
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    CustomerDto createCustomer(CreateCustomerRequest createRequest, HttpServletRequest request);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    CustomerDto getCustomerById(Long id, HttpServletRequest request);
    
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    List<CustomerDto> getAllCustomersByTenantId(HttpServletRequest request);// for admin to manage all customers in the tenant

    CustomerDto updateCustomer(Long id, UpdateCustomerRequest updateRequest, HttpServletRequest request);

    void deleteCustomer(Long id, HttpServletRequest request);
}
