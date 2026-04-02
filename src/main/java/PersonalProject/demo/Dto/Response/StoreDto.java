package PersonalProject.demo.Dto.Response;

import java.util.List;

import PersonalProject.demo.domain.StoreContact;
import PersonalProject.demo.domain.StoreStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreDto {
    Long id;
    
    String brand;

    UserDto storeAdmin;
    // List<UserDto> storeAdmin;

    String description;

    String storeType;
    
    StoreStatus storeStatus;

    StoreContact storeContact;
}
