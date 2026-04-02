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

import PersonalProject.demo.models.User;
import PersonalProject.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserImplementation implements UserDetailsService{
    // Spring không biết user của bạn nằm ở đâu → bạn phải implement UserDetailsService này để chỉ cho nó cách lấy user.
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " not found");
        }
        // đọc thêm 2 dòng này tại :https://gemini.google.com/app/d7ab9b78aab03383?hl=vi
        // GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().toString());
        // Collection<GrantedAuthority> authorities = Collections.singletonList(authority);
        return user;
    }
    
}
