package PersonalProject.demo.Dto.Response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryItemProduct {
    private Long id;
    private String name;
    private String sku;
    private String image;
    private BigDecimal mrp;
    private BigDecimal sellingPrice;
    private Integer quantity;
}
