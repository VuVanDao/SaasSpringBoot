package PersonalProject.demo.Dto.Request;

import java.util.Set;

import lombok.Data;

@Data
public class CreateCategoryRequest {
    String name;
    Long tenantId;
    Boolean isSystemDefault;
    Set<Long> storeId;
}
