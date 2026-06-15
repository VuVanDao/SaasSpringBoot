package PersonalProject.demo.services;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import PersonalProject.demo.Dto.Request.UpdateProfileRequest;
import PersonalProject.demo.Dto.Response.UserDto;


public interface UserService {
    UserDto getUserFromJwtToken(String token);

    UserDto getCurrentUser();

    
    UserDto getUserByEmail(String email);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    UserDto getUserById(Long id);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    List<UserDto> getAllUsers(Long tenantId);
    
    UserDto updateUserProfile(Long userId, UpdateProfileRequest request, Long tenantId);

    @PreAuthorize("hasAnyAuthority('ROLE_STORE_MANAGER','ROLE_SUPER_ADMIN')")
    List<UserDto> getAllUsersByTenantId(Long tenantId);
}

