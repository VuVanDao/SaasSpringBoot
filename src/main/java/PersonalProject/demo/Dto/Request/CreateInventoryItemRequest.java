package PersonalProject.demo.Dto.Request;

import java.util.Map;
import java.util.Set;

import lombok.Getter;

@Getter
public class CreateInventoryItemRequest {
    Map<Long, Integer> productIdsAndQuantity;
    Set<Long> inventoryIds;
    Integer quantity;
}
