package PersonalProject.demo.exception;

import PersonalProject.demo.Enums.ErrorCode;

public class InvalidRoleException extends RuntimeException{
    private ErrorCode errorCode;
    
    public InvalidRoleException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
