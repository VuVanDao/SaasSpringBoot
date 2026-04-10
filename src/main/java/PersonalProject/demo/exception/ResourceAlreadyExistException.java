package PersonalProject.demo.exception;

import PersonalProject.demo.domain.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceAlreadyExistException extends RuntimeException {
    private ErrorCode errorCode;
    
    public ResourceAlreadyExistException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
