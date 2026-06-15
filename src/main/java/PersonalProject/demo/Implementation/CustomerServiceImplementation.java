package PersonalProject.demo.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PersonalProject.demo.Dto.Request.CreateCustomerRequest;
import PersonalProject.demo.Dto.Request.UpdateCustomerRequest;
import PersonalProject.demo.Dto.Response.CustomerDto;
import PersonalProject.demo.Enums.ErrorCode;
import PersonalProject.demo.exception.ResourceNotFoundException;
import PersonalProject.demo.mapper.CustomerMapper;
import PersonalProject.demo.models.Customer;
import PersonalProject.demo.models.User;
import PersonalProject.demo.repositories.CustomerRepository;
import PersonalProject.demo.repositories.UserRepository;
import PersonalProject.demo.services.CustomerService;
import PersonalProject.demo.utils.TenantUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImplementation implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final TenantUtil tenantUtil;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerDto createCustomer(CreateCustomerRequest createRequest, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        
        // 1. Create and save User first (with no password since it's a customer record)
        User user = customerMapper.toUser(createRequest, tenantId);
        user = userRepository.save(user);
        
        // 2. Create and save Customer linked to User
        Customer customer = customerMapper.toCustomer(createRequest, user, tenantId);
        customer = customerRepository.save(customer);
        
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
        if (customer.getUser() != null) {
            userRepository.save(customer.getUser());
        }
        customerRepository.save(customer);
        return customerMapper.toCustomerDto(customer);
    }

    @Override
    public void deleteCustomer(Long id, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        Customer customer = customerRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        
        User user = customer.getUser();
        customerRepository.delete(customer);
        if (user != null) {
            userRepository.delete(user);
        }
    }
}