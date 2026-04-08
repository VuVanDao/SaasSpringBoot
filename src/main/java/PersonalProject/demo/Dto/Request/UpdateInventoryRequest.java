package PersonalProject.demo.Dto.Request;

import lombok.Data;

@Data
public class UpdateInventoryRequest {
    private Long branchId;
    private String inventoryName;
}