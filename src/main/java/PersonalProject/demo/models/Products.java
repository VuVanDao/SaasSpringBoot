package PersonalProject.demo.models;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
// @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Products extends AbstractTenantModel {
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String sku; // Stock Keeping Unit
    String description;
    BigDecimal mrp; // Maximum Retail Price
    BigDecimal sellingPrice; // price at which the product is sold
    String brand;
    String image;

    // @ManyToOne
    // @JoinColumn(name = "store_id", nullable = false)
    // Store store;

    @ManyToMany
    @JoinTable(
        name = "product_category", // Tên bảng trung gian
        joinColumns = @JoinColumn(name = "product_id"), // Khóa ngoại trỏ đến Product
        inverseJoinColumns = @JoinColumn(name = "category_id") // Khóa ngoại trỏ đến Category
    )
    Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "products")
    List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product")
    List<StoreProduct> storeProducts;
}

