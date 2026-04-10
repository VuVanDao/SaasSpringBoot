package PersonalProject.demo.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final ApplicationProperties _ApplicationProperties;
    private final String[] Public_Post_Endpoint = { "/auth/sign_up" ,"/auth/login"}; 
    private final String[] Public_Get_Endpoint = { "/products/store/{storeId}" ,"/products/search" ,"/products/{id}","/categories","/stores" }; 
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(request -> request
                    .requestMatchers(HttpMethod.POST, Public_Post_Endpoint).permitAll()
                    .requestMatchers(HttpMethod.GET, Public_Get_Endpoint).permitAll()
                    // .requestMatchers("/stores/**").permitAll()
                        // .requestMatchers("/error").permitAll()
                        /*
                        Khi Spring thực hiện kiểm tra quyền cho dòng .requestMatchers("/super-admin/**").hasRole("ADMIN"), nó thực hiện logic sau:
                        Bước 1: Nó truy cập vào SecurityContextHolder để lấy đối tượng Authentication.
                        đã được JwtValidator nạp vào trước đó. Authentication này chứa thông tin về user và danh sách GrantedAuthority (quyền) của user đó.
                        
                        Bước 2: Nó duyệt qua danh sách GrantedAuthority mà bạn đã nạp vào ở bước trên.
                        
                        Bước 3 (Quan trọng): * Nếu bạn dùng hasRole("ADMIN"): Spring sẽ tìm kiếm một Authority có tên chính xác là ROLE_ADMIN.
                        
                        Nếu bạn dùng hasAuthority("ADMIN"): Spring sẽ tìm kiếm một Authority có tên chính xác là ADMIN.
                         */
                    .requestMatchers("/super-admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated())
                .addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class);// chạy vào JwtValidator để validate token trước khi vào controller
            http.csrf(csrf -> csrf.disable());
            http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(_ApplicationProperties.getAllowedOrigins());
                System.out.println("--------------------Security------------------------");
                System.out.println("_ApplicationProperties.getAllowedOrigins(): "
                        + _ApplicationProperties.getAllowedOrigins());
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
                config.setAllowedHeaders(List.of("*"));
                config.setAllowCredentials(true);
                config.setExposedHeaders(Arrays.asList("Authorization"));
                config.setMaxAge(3600l);
                return config;
            }
        };
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
