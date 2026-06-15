package PersonalProject.demo.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import PersonalProject.demo.Dto.Request.CreateCustomerRequest;
import PersonalProject.demo.Dto.Request.UpdateCustomerRequest;
import PersonalProject.demo.Dto.Response.CustomerDto;
import PersonalProject.demo.Enums.ErrorCode;
import PersonalProject.demo.exception.ResourceNotFoundException;
import PersonalProject.demo.mapper.CustomerMapper;
import PersonalProject.demo.models.Customer;
import PersonalProject.demo.repositories.CustomerRepository;
import PersonalProject.demo.services.CustomerService;
import PersonalProject.demo.utils.TenantUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImplementation implements CustomerService {
    private final CustomerRepository customerRepository;
    private final TenantUtil tenantUtil;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerDto createCustomer(CreateCustomerRequest createRequest, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        Customer customer = customerMapper.toCustomer(createRequest, tenantId);
        customerRepository.save(customer);
        return customerMapper.toCustomerDto(customer);
    }

    @Override
    public CustomerDto getCustomerById(Long id, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        Customer customer = customerRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        return customerMapper.toCustomerDto(customer);
    }

    @Override
    public List<CustomerDto> getAllCustomersByTenantId(Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        List<Customer> customers = customerRepository.findAllByTenantId(tenantId);
        return customers.stream().map(customerMapper::toCustomerDto).collect(Collectors.toList());
    }

    @Override
    public CustomerDto updateCustomer(Long id, UpdateCustomerRequest updateRequest, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        Customer customer = customerRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        customerMapper.toCustomerFromUpdateRequest(customer, updateRequest);
        customerRepository.save(customer);
        return customerMapper.toCustomerDto(customer);
    }

    @Override
    public void deleteCustomer(Long id, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        Customer customer = customerRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        customerRepository.delete(customer);
    }
}