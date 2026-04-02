package PersonalProject.demo.configuration;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {
    static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());

    public String generateToken(Authentication authentication) {
        System.out.println("-----------------JwtProvider.generateToken-----------------");
        // GrantedAuthority là một interface đại diện cho quyền (authority/permission) mà một user có trong hệ thống.
        // Collection<? extends GrantedAuthority> Là một Tập hợp (Container) chứa nhiều đối tượng thực thi Interface đó.
        /*
          Ký hiệu ? extends trong Java Generics cho phép danh sách này linh hoạt hơn. 
          Nó có thể chứa bất kỳ lớp nào triển khai (implement) lại Interface GrantedAuthority. 
          Ví dụ: SimpleGrantedAuthority là một lớp phổ biến mà Spring Security dùng để hiện thực hóa Interface này.
        */
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = populateAuthorities(authorities);
        return Jwts.builder()
                // issuedAt (IAT): Đây là thời điểm phát hành token (thời điểm hệ thống tạo ra nó). Nó ghi lại chính xác lúc nào người dùng đăng nhập thành công.
                .issuedAt(new Date())
                // expiration (EXP): Đây mới thực sự là thời điểm hết hạn. Trong code của bạn, nó được đặt là 24 giờ sau thời điểm phát hành. 
                // Điều này có nghĩa là token sẽ chỉ hợp lệ trong vòng 24 giờ kể từ khi nó được tạo ra.
                .expiration(new Date(new Date().getTime() + 86400000))
                // Nhét email vào một cái túi (Claim).
                .claim("email", authentication.getName())
                // Lưu danh sách quyền của user vào token.
                .claim("authorities", roles)
                // Hệ thống sẽ dùng một thuật toán mã hóa (như HMAC) kết hợp với cái key (bí mật) của bạn để băm (hash) toàn bộ nội dung phía trên.
                .signWith(key)
                /* 
                Về mặt kỹ thuật: Hàm này sẽ thực hiện việc mã hóa các phần (Header, Payload) sang định dạng Base64Url, sau đó nối chúng lại với nhau bằng các dấu chấm (.)
                Kết quả: Nó chuyển đổi đối tượng Builder (đang nằm trong bộ nhớ máy tính) thành một String duy nhất có dạng: xxxxx.yyyyy.zzzzz.
                Ví dụ: Nếu không có compact(), bạn chỉ có một "bản thảo" thiết kế. 
                Gọi compact() giống như việc bạn bấm nút "In ấn" để ra được tờ hóa đơn cuối cùng gửi cho khách hàng.
                 */
                .compact();
    }

    public String GetEmailFromToken(String jwt) {
        System.out.println("-----------------JwtProvider.GetEmailFromToken-----------------");
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
        return String.valueOf(claims.get("email"));
    }
    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        System.out.println("-----------------JwtProvider.generateToken-----------------");
        Set<String> auths = new HashSet<>();
        // GrantedAuthority Là một Interface đại diện cho một quyền hạn cụ thể của người dùng.
        for (GrantedAuthority authority : authorities) {
            System.out.println("authority: " + authority.getAuthority());
            auths.add(authority.getAuthority());
        }
        System.out.println("String.join(\",\", auths): " + String.join(",", auths));// kq vi du: ROLE_USER,ROLE_ADMIN
        return String.join(",", auths);
    }
}
