package PersonalProject.demo.exception;

import PersonalProject.demo.Enums.ErrorCode;

public class UserNotUnderPermission extends RuntimeException{
    private ErrorCode errorCode;

    public UserNotUnderPermission(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
