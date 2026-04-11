package PersonalProject.demo.exception;

import PersonalProject.demo.domain.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
    private ErrorCode errorCode;
    public ResourceNotFoundException( ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
