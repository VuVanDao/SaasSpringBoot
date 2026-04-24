package PersonalProject.demo.mapper;

import org.springframework.stereotype.Component;

import PersonalProject.demo.Dto.Request.CreateCustomerRequest;
import PersonalProject.demo.Dto.Request.UpdateCustomerRequest;
import PersonalProject.demo.Dto.Response.CustomerDto;
import PersonalProject.demo.models.Customer;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerMapper {
    public CustomerDto toCustomerDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .avatar(customer.getAvatar())
                .address(customer.getAddress())
                .tenantId(customer.getTenantId())
                .build();
    }
    
    public Customer toCustomer(CreateCustomerRequest createRequest, Long tenantId) {
        return Customer.builder()
                .fullName(createRequest.getFullName())
                .email(createRequest.getEmail())
                .phone(createRequest.getPhone())
                .avatar(createRequest.getAvatar())
                .address(createRequest.getAddress())
                .tenantId(tenantId)
                .build();
    }

    public void toCustomerFromUpdateRequest(Customer customer, UpdateCustomerRequest updateRequest) {
        customer.setFullName(updateRequest.getFullName());
        customer.setEmail(updateRequest.getEmail());
        customer.setPhone(updateRequest.getPhone());
        customer.setAvatar(updateRequest.getAvatar());
        customer.setAddress(updateRequest.getAddress());
    }
}
