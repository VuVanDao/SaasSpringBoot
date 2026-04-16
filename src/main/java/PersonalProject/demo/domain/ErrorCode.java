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
    Resource_already_exist(400, "Duplicate value", HttpStatus.BAD_REQUEST),
    USER_NOT_VALID_FOR_MANAGER(400,
            "User không có quyền quản lý chi nhánh. Vui lòng cập nhật role thành BRANCH_MANAGER trước khi gán.",
            HttpStatus.CONFLICT),
    Tenant_Exception(404, "Tenant missing or not match", HttpStatus.NOT_FOUND),
    User_Not_Under_Your_Permission(409,"The user not under your permission",HttpStatus.CONFLICT),
    Store_Not_Under_Your_Permission(409,"The store not under your permission",HttpStatus.CONFLICT),
    ;              

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;
}
