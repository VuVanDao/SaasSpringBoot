package PersonalProject.demo.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalException {
    // 1. Bắt lỗi Validation (@Email, @NotBlank, @Size...)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setMessage("some error occur");
        apiResponse.setCode(400);
        apiResponse.setResult(errors);
        return ResponseEntity.badRequest().body(apiResponse);
    }
    // 2. Bắt lỗi khi lien quan đến database (ví dụ: trùng email khi đăng ký)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setMessage(ex.getMessage());
        apiResponse.setCode(HttpStatus.CONFLICT.value());
        apiResponse.setResult(null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    // 3. lỗi chung chung
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRunTimeException(RuntimeException runtimeException) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setMessage(runtimeException.getMessage());
        apiResponse.setCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(apiResponse);
    }
    // 4. error not found
    @ExceptionHandler(value = ResourceNotFoundException.class)
    ResponseEntity<ApiResponse> handlingResourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setMessage(ex.getMessage());
        apiResponse.setCode(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
    // 5. missing servlet request parameter
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    ResponseEntity<ApiResponse> handlingMissingServletRequestParameterException(
            MissingServletRequestParameterException ex) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setMessage(ex.getMessage());
        apiResponse.setCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
    
    @ExceptionHandler(value = BadCredentialsException.class)
    ResponseEntity<ApiResponse> handlingBadCredentialsException(BadCredentialsException ex) {
        ApiResponse apiResponse = new ApiResponse<>();
        ErrorCode errorCode = ErrorCode.BadCredentialsException;
        apiResponse.setMessage(errorCode.getMessage());
        apiResponse.setCode(errorCode.getCode());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }

    @ExceptionHandler(value = TenantException.class)
    ResponseEntity<ApiResponse> handlingTenantException(TenantException ex) {
        ApiResponse apiResponse = new ApiResponse<>();
        ErrorCode errorCode = ErrorCode.Tenant_Exception;
        apiResponse.setMessage(errorCode.getMessage());
        apiResponse.setCode(errorCode.getCode());
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }
}
