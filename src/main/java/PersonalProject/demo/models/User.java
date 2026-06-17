package PersonalProject.demo.models;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import PersonalProject.demo.Enums.UserRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
// @Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractTenantModel implements UserDetails{
    String fullName;

    @Column(nullable = false, unique = false)
    @Email(message = "Email is not valid")
    String email;

    String phone;

    @Column(nullable = true)
    String password;

    String avatar;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    UserRole role;
    
    LocalDateTime lastLogin;

    @OneToOne(mappedBy = "storeAdmin")
    // @ManyToOne
    Store store;

    @OneToOne(mappedBy = "manager")
    Branch branch;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    Employee employee;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    Customer customer;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = new SimpleGrantedAuthority(this.getRole().toString());
        Collection<GrantedAuthority> authorities = Collections.singletonList(authority);
        return authorities;
    }
    @Override
    public String getUsername() {
        return this.email;
    }
}
