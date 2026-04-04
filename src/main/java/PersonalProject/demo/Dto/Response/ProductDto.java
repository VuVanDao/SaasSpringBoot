package PersonalProject.demo.Dto.Response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ProductDto {

    private Long id;
    private String name;
    private String sku;
    private String description;
    private String brand;
    private String image;
    private BigDecimal mrp;
    private BigDecimal sellingPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private StoreDto store;
    private List<CategoryResponse> categories;
}