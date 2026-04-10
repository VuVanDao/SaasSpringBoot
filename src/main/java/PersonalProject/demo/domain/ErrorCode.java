package PersonalProject.demo.domain;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    Resource_not_found(400, "Not found", HttpStatus.BAD_REQUEST),
    AuthorizationDeniedException(403, "you haven't permission to access this", HttpStatus.FORBIDDEN),
    BadCredentialsException(401, "Error, there is a user authentication issue", HttpStatus.FORBIDDEN),
    Resource_already_exist(400,"Duplicate value",HttpStatus.BAD_REQUEST)              ;

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;
}
