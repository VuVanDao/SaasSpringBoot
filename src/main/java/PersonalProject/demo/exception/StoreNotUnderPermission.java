package PersonalProject.demo.exception;

import PersonalProject.demo.domain.ErrorCode;

public class StoreNotUnderPermission extends RuntimeException{
    private ErrorCode errorCode;

    public StoreNotUnderPermission(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}