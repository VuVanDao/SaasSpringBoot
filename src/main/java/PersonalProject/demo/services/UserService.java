package PersonalProject.demo.services;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import PersonalProject.demo.Dto.Request.UpdateProfileRequest;
import PersonalProject.demo.Dto.Response.UserDto;
import jakarta.servlet.http.HttpServletRequest;


public interface UserService {
    UserDto getUserFromJwtToken(String token);

    UserDto getCurrentUser();

    
    UserDto getUserByEmail(String email);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    UserDto getUserById(Long id);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    List<UserDto> getAllUsers(HttpServletRequest request);
    
    UserDto updateUserProfile(Long userId, UpdateProfileRequest request, HttpServletRequest request2);

    @PreAuthorize("hasAnyAuthority('ROLE_STORE_MANAGER')")
    List<UserDto> getAllUsersByTenantId(HttpServletRequest request);
}

