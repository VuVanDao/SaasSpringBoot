package PersonalProject.demo.configuration;

public class JwtConstant {
    public static final String JWT_SECRET = "f5EXGyafvRgPlZG7EDXNXUffRV33ttcq-myW3czkXvYGRetv2DXkvrz2yINuyOSPI";
    public static final String JWT_HEADER = "Authorization";

    // Thời gian sống (Milliseconds)
    public static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 5; // 5 phút
    public static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7 ngày
}
