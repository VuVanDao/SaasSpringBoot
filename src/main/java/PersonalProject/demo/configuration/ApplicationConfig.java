package PersonalProject.demo.configuration;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import PersonalProject.demo.Enums.TenantStatus;
import PersonalProject.demo.Enums.UserRole;
import PersonalProject.demo.models.Tenant;
import PersonalProject.demo.models.User;
import PersonalProject.demo.repositories.TenantRepository;
import PersonalProject.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationConfig {
    private final TenantRepository tenantRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository,TenantRepository tenantRepository) {
        return args -> {
            System.out.println("--------------------- ApplicationConfig ---------------------");
            Boolean isAdminTenantExist = tenantRepository.findByName("AdminTenant") != null;
            if (!isAdminTenantExist) {
                System.out.println("AdminTenant not found, creating AdminTenant...");
               Tenant tenant =Tenant.builder()
                        .name("AdminTenant")
                        .domain("admin.tenant.com")
                        .status(TenantStatus.ACTIVE)
                        .build();
                tenantRepository.save(tenant);
                log.info("AdminTenant have created");
                
            }
            if (userRepository.findByFullName("admin") == null) {
                System.out.println("Admin not found, creating admin user...");
                User users = User.builder()
                        .fullName("admin")
                        .password(passwordEncoder.encode("admin"))
                        .role(UserRole.ROLE_SUPER_ADMIN)
                        .email("admin@gmail.com")
                        .tenantId(isAdminTenantExist != null ? tenantRepository.findByName("AdminTenant").getId() : Long.valueOf(1))
                        .build();
                userRepository.save(users);
                log.info("Admin have created");
            }
        };
    }
}
