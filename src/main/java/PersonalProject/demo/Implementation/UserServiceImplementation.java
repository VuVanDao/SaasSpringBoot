package PersonalProject.demo.Implementation;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PersonalProject.demo.Dto.Request.UpdateProfileRequest;
import PersonalProject.demo.Dto.Response.UserDto;
import PersonalProject.demo.configuration.ApplicationProperties;
import PersonalProject.demo.configuration.JwtProvider;
import PersonalProject.demo.domain.ErrorCode;
import PersonalProject.demo.exception.ResourceNotFoundException;
import PersonalProject.demo.exception.TenantException;
import PersonalProject.demo.mapper.storeMapper;
import PersonalProject.demo.models.User;
import PersonalProject.demo.repositories.TenantRepository;
import PersonalProject.demo.repositories.UserRepository;
import PersonalProject.demo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final storeMapper storeMapper;
    private final ApplicationProperties applicationProperties;
    private final TenantRepository tenantRepository;

    @Override
    public UserDto getUserFromJwtToken(String token) {
        System.out.println("-----------------UserServiceImplementation.getUserFromJwtToken-----------------");
        String email = jwtProvider.GetEmailFromToken(token.substring(7));
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException((ErrorCode.Resource_not_found));
        }
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .lastLogin(user.getLastLogin())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException((ErrorCode.Resource_not_found));
        }
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .lastLogin(user.getLastLogin())
                .phone(user.getPhone())
                .role(user.getRole())
                .tenantId(user.getTenantId())
                .build();
        if (user.getStore() != null) {
            userDto.setStore(storeMapper.convertToDto(user.getStore()));
        }
        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException((ErrorCode.Resource_not_found));
        }
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .lastLogin(user.getLastLogin())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException((ErrorCode.Resource_not_found));
        }
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .lastLogin(user.getLastLogin())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }

    @Override
    public List<UserDto> getAllUsers(HttpServletRequest request) {
        // dung cho admin nen lay tat ca user trong database
        Long tenantId = Long.valueOf(request.getHeader(applicationProperties.getHeaderTenant()));

        if (tenantId == null || tenantId != 1) {
            throw new RuntimeException("Missing tenant or you have not permission to access this resource");
        }
        return userRepository.findAll().stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .fullName(user.getFullName())
                        .lastLogin(user.getLastLogin())
                        .phone(user.getPhone())
                        .role(user.getRole())
                        .tenantId(tenantId)
                        .build())
                .toList();
    }

    @Override
    public UserDto updateUserProfile(Long userId, UpdateProfileRequest request,  HttpServletRequest request2) {
        Long tenantId = Long.valueOf(request2.getHeader(applicationProperties.getHeaderTenant()));
        if (tenantId == null) {
            throw new RuntimeException("Missing tenant");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
        if (user.getTenantId() != tenantId) {
            throw new RuntimeException(
                    "You have not permission to update this user, " + user.getFullName() + " is not your staff");
        }
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());

        userRepository.save(user);
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .lastLogin(user.getLastLogin())
                .phone(user.getPhone())
                .role(user.getRole())
                .tenantId(tenantId)
                .build();
    }

    @Override
    public List<UserDto> getAllUsersByTenantId(HttpServletRequest request) {
        Long tenantId = Long.valueOf(request.getHeader(applicationProperties.getHeaderTenant()));
        // day thuc ra la api endpoint de store manager lay tat ca user trong tenant cua no
        if (tenantId == null) {
            throw new RuntimeException("Missing tenant");
        }
        
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email);
        
        if (currentUser == null) {
            throw new ResourceNotFoundException((ErrorCode.Resource_not_found));
        }
        
        if (currentUser.getTenantId() != tenantId) {
            throw new TenantException(ErrorCode.Tenant_Exception);
        }

        return userRepository.findAllUserByTenantId(tenantId).stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .fullName(user.getFullName())
                        .lastLogin(user.getLastLogin())
                        .phone(user.getPhone())
                        .role(user.getRole())
                        .tenantId(tenantId)
                        .build())
                .toList();
    }
}
         
