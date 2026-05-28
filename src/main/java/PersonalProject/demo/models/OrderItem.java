package PersonalProject.demo.models;

import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
// @Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends AbstractTenantModel{
    Integer quantity;
    BigDecimal price;

    // 1 san pham thuoc ve 1 don hang, 1 don hang co nhieu san pham
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Products products;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    Order order;
}
