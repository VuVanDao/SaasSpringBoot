package PersonalProject.demo.Dto.Response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryItemInventory {
    private Long id;
    private String inventoryName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long tenant_id;
    private Long branch_id;
}
