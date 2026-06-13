package PersonalProject.demo.Dto.Request;

import java.util.List;

import PersonalProject.demo.Dto.Response.UserDto;
import PersonalProject.demo.Enums.StoreContact;
import PersonalProject.demo.Enums.StoreStatus;
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

    List<Long> branchIds;

    List<Long> categoryIds;
}
