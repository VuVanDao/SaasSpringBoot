package PersonalProject.demo.services;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import PersonalProject.demo.Dto.Request.CreateCustomerRequest;
import PersonalProject.demo.Dto.Request.UpdateCustomerRequest;
import PersonalProject.demo.Dto.Response.CustomerDto;

public interface CustomerService {
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    CustomerDto createCustomer(CreateCustomerRequest createRequest, Long tenantId);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    CustomerDto getCustomerById(Long id, Long tenantId);
    
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    List<CustomerDto> getAllCustomersByTenantId(Long tenantId);

    CustomerDto updateCustomer(Long id, UpdateCustomerRequest updateRequest, Long tenantId);

    void deleteCustomer(Long id, Long tenantId);
}
