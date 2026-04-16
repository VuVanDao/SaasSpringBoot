package PersonalProject.demo.exception;

import PersonalProject.demo.domain.ErrorCode;

public class InvalidRoleException extends RuntimeException{
    private ErrorCode errorCode;
    
    public InvalidRoleException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
