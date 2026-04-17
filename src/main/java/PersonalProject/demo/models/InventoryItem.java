package PersonalProject.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
// @Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItem extends AbstractTenantModel{
    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = true)
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Products product;

    @Column(nullable = false)
    private int quantity;
}
