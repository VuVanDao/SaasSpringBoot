package PersonalProject.demo.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "SKU is required")
    private String sku;

    private String description;

    @NotBlank(message = "Brand is required")
    private String brand;

    private String image;

    @NotNull(message = "MRP is required")
    private BigDecimal mrp;

    @NotNull(message = "Selling price is required")
    private BigDecimal sellingPrice;

    // @NotNull(message = "Store ID is required")
    // private Long storeId;
}