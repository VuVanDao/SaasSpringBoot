package PersonalProject.demo.Dto.Request;

import java.util.List;

import PersonalProject.demo.Dto.Response.StoreDto;
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
public class CreateStoreRequest {
    String brand;

    String description;
    
    StoreStatus storeStatus;

    StoreContact storeContact;

    List<Long> branchIds;

    Long tenantId;
}