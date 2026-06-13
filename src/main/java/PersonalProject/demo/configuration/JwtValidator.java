package PersonalProject.demo.configuration;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import PersonalProject.demo.Enums.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidator extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Nếu KHÔNG có filter này, Thì dù có JWT, Spring Security không biết user là ai
        System.out.println("-------------------JwtValidator.doFilterInternal----------------------");
        String jwt = request.getHeader(JwtConstant.JWT_HEADER);
        if (jwt != null) {
            jwt = jwt.substring(7);// for Bearer
            try {
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());
                // Verify JWT
                // Dùng secret key để check hợp lệ, Nếu hai bên dùng secret khác nhau, token sẽ bị coi là giả.
                Claims claims = Jwts
                // Khởi tạo một bộ phân tích (parser) để đọc chuỗi JWT
                    .parser()
                // Bạn đưa chìa khóa (SecretKey hoặc PublicKey) vào. Parser sẽ dùng khóa này để tính toán lại chữ ký và so sánh với chữ ký có trong Token.
                    .verifyWith(key)
                // Chốt cấu hình cho Parser.
                    .build()
                // Nó thực hiện việc kiểm tra chữ ký ngay tại đây. Nếu chữ ký sai hoặc token hết hạn, nó sẽ "bắn" lỗi ngay lập tức.
                // Nếu đúng, nó sẽ trả về một đối tượng chứa toàn bộ cấu trúc của JWT (Header, Payload, Signature).
                    .parseSignedClaims(jwt)
                // Bạn đang yêu cầu thư viện lấy phần nội dung (Payload) của Token ra.
                    .getPayload();
                // Claims thực chất là một dạng Map<String, Object> chứa các thông tin như sub (subject), iat (issued at), exp (expiration), v.v.
                System.out.println("claims: " + claims);
                System.out.println("claims.get(\"email\"): " + claims.get("email"));
                System.out.println("claims.get(\"authorities\"): " + claims.get("authorities"));
                // Lấy thông tin user từ token
                String email = String.valueOf(claims.get("email"));
                String authorities = String.valueOf(claims.get("authorities"));
                // Convert role → GrantedAuthority
                List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                // ứng dụng của bạn đang chạy ở chế độ STATELESS, Mỗi khi có request mới đến, Spring Security sẽ "quên" người dùng là ai. 
                // JwtValidator giúp "nhắc lại" cho Spring Security biết: "Đây là user X, có quyền Y" cho mỗi request cụ thể đó.
                // Sau bước này: Spring Security hiểu rằng: user đã login
                /*
                    @PreAuthorize
                    .hasRole()
                    .authenticated()
                 */
                /*
                Dòng này dùng để tạo ra một "Thẻ thông hành" (Authentication object) đã được xác thực.
                Tham số 1 (email): Đây là Principal (Chủ thể). Nó định danh người dùng là ai. Sau này ở bất kỳ đâu trong Controller, bạn có thể lấy lại email này để biết ai đang gọi API.
                Tham số 2 (null): Đây là Credentials (Mật khẩu). Vì ở bước này ta xác thực qua JWT (thẻ đã cấp) chứ không phải bằng mật khẩu nữa, nên ta để null.
                Tham số 3 (auths): Đây là Authorities (Danh sách quyền). Spring Security sẽ dựa vào danh sách này để quyết định user có được vào các đường dẫn như /super-admin/** hay không.
                Điểm mấu chốt: Khi bạn dùng Constructor có 3 tham số này, Spring Security tự động đánh dấu đối tượng auth này là authenticated = true (Đã xác thực thành công).
                 */
                Authentication auth = new UsernamePasswordAuthenticationToken(email, null, auths);
                // đây là bước "Ghi danh" vào hệ thống cho request hiện tại.
                /*
                Phạm vi (Scope): Spring Security sử dụng ThreadLocal để lưu trữ thông tin này. Nghĩa là cái "Thẻ thông hành" bạn vừa tạo sẽ gắn liền với luồng (thread) đang xử lý request đó.
                Vòng đời: Nó tồn tại từ lúc bạn gọi lệnh này, đi qua các Filter tiếp theo, vào đến Service, Controller, và cho đến khi phản hồi (Response) được gửi về cho người dùng. 
                Sau khi request kết thúc, thông tin này sẽ được xóa sạch để chuẩn bị cho request tiếp theo (vì chúng ta dùng STATELESS).
                 */
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                // throw new BadCredentialsException("invalid jwt");
                ErrorCode errorCode = ErrorCode.BadCredentialsException;
                response.setStatus(errorCode.getCode());
                response.setContentType("application/json");
                response.getWriter().write("{\"message\":\"" +errorCode.getMessage() +"\",\"code\":401}");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }  
}
