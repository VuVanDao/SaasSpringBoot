package PersonalProject.demo.Dto.Request;

import PersonalProject.demo.Dto.Response.UserDto;
import PersonalProject.demo.domain.StoreContact;
import PersonalProject.demo.domain.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStoreRequest {
    String brand;

    UserDto storeAdmin;
    // List<UserDto> storeAdmin;

    String description;

    String storeType;
    
    StoreStatus storeStatus;

    StoreContact storeContact;
}
