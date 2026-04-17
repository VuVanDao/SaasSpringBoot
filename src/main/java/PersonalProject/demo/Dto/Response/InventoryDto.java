package PersonalProject.demo.Dto.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InventoryDto {
    private Long id;
    private BranchDto branch;
    private String inventoryName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long tenant_id;
    private Long branch_id;
}