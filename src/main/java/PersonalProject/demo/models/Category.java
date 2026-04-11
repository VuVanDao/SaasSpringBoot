package PersonalProject.demo.models;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
// @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends AbstractModel {
    @Column(nullable = false)
    String name;
    
    @ManyToMany(mappedBy = "categories")
    Set<Products> products;

    @ManyToMany(mappedBy = "categories")
    List<Store> stores;

    @Column(name = "tenant_id",nullable = true) // Cho phép null
    Long tenantId;

    @Column(name = "is_system_default")
    Boolean isSystemDefault = false;// false: category public, true: cate của riêng từng store, dùng store or tenantId để lấy
}
