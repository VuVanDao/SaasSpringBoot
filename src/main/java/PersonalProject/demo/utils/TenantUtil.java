package PersonalProject.demo.utils;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import PersonalProject.demo.Dto.Response.UserDto;
import PersonalProject.demo.Enums.ErrorCode;
import PersonalProject.demo.configuration.ApplicationProperties;
import PersonalProject.demo.exception.TenantException;
import PersonalProject.demo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TenantUtil {
    private final UserService userService;
    private final ApplicationProperties applicationProperties;

    public Long validateTenant(HttpServletRequest request) {
        Long tenantId = Long.valueOf(request.getHeader(applicationProperties.getHeaderTenant()));
        UserDto user = userService.getCurrentUser();

        if (user.getTenantId() != tenantId) {
            throw new TenantException(ErrorCode.Tenant_Exception);
        }

        return tenantId;
    }

    public UserDto validateTenantAndGetUser(HttpServletRequest request) {
        Long tenantId = Long.valueOf(request.getHeader(applicationProperties.getHeaderTenant()));
        UserDto user = userService.getCurrentUser();

        if (user.getTenantId() != tenantId) {
            throw new TenantException(ErrorCode.Tenant_Exception);
        }
        
        return user;
    }
}
