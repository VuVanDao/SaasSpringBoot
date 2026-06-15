package PersonalProject.demo.utils;

import org.springframework.stereotype.Component;

import PersonalProject.demo.Dto.Response.UserDto;
import PersonalProject.demo.Enums.ErrorCode;
import PersonalProject.demo.exception.TenantException;
import PersonalProject.demo.services.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TenantUtil {
    private final UserService userService;

    public Long validateTenant(Long tenantId) {
        if (tenantId == null) {
            throw new RuntimeException("Missing tenant");
        }
        UserDto user = userService.getCurrentUser();

        if (!user.getTenantId().equals(tenantId)) {
            throw new TenantException(ErrorCode.Tenant_Exception);
        }

        return tenantId;
    }

    public UserDto validateTenantAndGetUser(Long tenantId) {
        if (tenantId == null) {
            throw new RuntimeException("Missing tenant");
        }
        UserDto user = userService.getCurrentUser();

        if (!user.getTenantId().equals(tenantId)) {
            throw new TenantException(ErrorCode.Tenant_Exception);
        }
        
        return user;
    }
}
