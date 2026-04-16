package PersonalProject.demo.exception;

import PersonalProject.demo.domain.ErrorCode;

public class TenantException extends RuntimeException{
    private ErrorCode errorCode;
    public TenantException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
