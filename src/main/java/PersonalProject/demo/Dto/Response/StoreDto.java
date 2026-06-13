package PersonalProject.demo.Dto.Response;

import java.util.List;
import java.util.Set;

import PersonalProject.demo.Enums.StoreContact;
import PersonalProject.demo.Enums.StoreStatus;
import PersonalProject.demo.models.Category;
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

    Set<CategoryResponse> categories;

    List<BranchDto> branches;
}
