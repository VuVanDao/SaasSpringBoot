package PersonalProject.demo.Dto.Request;

import lombok.Data;

@Data
public class CreateInventoryRequest {
    private Long branchId;
    private String inventoryName;
    private Long tenant_id;
}