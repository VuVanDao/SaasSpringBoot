package PersonalProject.demo.mapper;

import org.springframework.stereotype.Component;

import PersonalProject.demo.Dto.Request.CreateCustomerRequest;
import PersonalProject.demo.Dto.Request.UpdateCustomerRequest;
import PersonalProject.demo.Dto.Response.CustomerDto;
import PersonalProject.demo.models.Customer;
import PersonalProject.demo.models.User;
import PersonalProject.demo.Enums.UserRole;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerMapper {
    
    public CustomerDto toCustomerDto(Customer customer) {
        if (customer == null) {
            return null;
        }
        User user = customer.getUser();
        return CustomerDto.builder()
                .id(customer.getId())
                .fullName(user != null ? user.getFullName() : null)
                .email(user != null ? user.getEmail() : null)
                .phone(user != null ? user.getPhone() : null)
                .avatar(user != null ? user.getAvatar() : null)
                .address(customer.getAddress())
                .tenantId(customer.getTenantId())
                .build();
    }
    
    public User toUser(CreateCustomerRequest createRequest, Long tenantId) {
        if (createRequest == null) {
            return null;
        }
        return User.builder()
                .fullName(createRequest.getFullName())
                .email(createRequest.getEmail())
                .phone(createRequest.getPhone())
                .avatar(createRequest.getAvatar())
                .role(UserRole.ROLE_USER)
                .tenantId(tenantId)
                .build();
    }

    public Customer toCustomer(CreateCustomerRequest createRequest, User user, Long tenantId) {
        if (createRequest == null) {
            return null;
        }
        return Customer.builder()
                .user(user)
                .address(createRequest.getAddress())
                .tenantId(tenantId)
                .build();
    }

    public void toCustomerFromUpdateRequest(Customer customer, UpdateCustomerRequest updateRequest) {
        if (customer == null || updateRequest == null) {
            return;
        }
        customer.setAddress(updateRequest.getAddress());
        User user = customer.getUser();
        if (user != null) {
            user.setFullName(updateRequest.getFullName());
            user.setEmail(updateRequest.getEmail());
            user.setPhone(updateRequest.getPhone());
            user.setAvatar(updateRequest.getAvatar());
        }
    }
}
