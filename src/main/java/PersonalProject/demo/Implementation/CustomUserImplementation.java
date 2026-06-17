package PersonalProject.demo.Implementation;

import java.util.Collection;
import java.util.Collections;

import javax.swing.Spring;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import PersonalProject.demo.models.User;
import PersonalProject.demo.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserImplementation implements UserDetailsService{
    // Spring không biết user của bạn nằm ở đâu → bạn phải implement UserDetailsService này để chỉ cho nó cách lấy user.
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ServletRequestAttributes attributes = 
            (ServletRequestAttributes) 
            RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new UsernameNotFoundException("Request context not available");
        }
        HttpServletRequest request = attributes.getRequest();
        String tenantIdStr = request.getHeader("X-tenant-Id");
        if (tenantIdStr == null || tenantIdStr.isEmpty()) {
            throw new UsernameNotFoundException("Missing tenant header");
        }
        Long tenantId = Long.parseLong(tenantIdStr);
        User user = userRepository.findByEmailAndTenantId(username, tenantId);
        if (user == null) {
            throw new UsernameNotFoundException(username + " not found under tenant " + tenantId);
        }
        return user;
    }
    
}
