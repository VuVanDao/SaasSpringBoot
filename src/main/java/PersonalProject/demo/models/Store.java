package PersonalProject.demo.models;

import java.util.List;
import java.util.Set;

import PersonalProject.demo.domain.StoreContact;
import PersonalProject.demo.domain.StoreStatus;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.JoinColumn;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
// @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Store extends AbstractTenantModel {
    String brand;

    @OneToOne 
    User storeAdmin;

    String description;
    
    @Enumerated(EnumType.STRING)
    StoreStatus storeStatus;

    @Embedded
    StoreContact storeContact = new StoreContact();

    // mappedBy = "store" nghĩa là gì
    /*
        Nó có nghĩa là: "Cái bảng này không giữ khóa ngoại. Hãy nhìn vào thuộc tính tên là 'store' ở thực thể bên kia để biết cách liên kết."
        store: Đây không phải tên bảng trong Database, mà là tên của biến (field) bạn khai báo trong thực thể Product
     */
    // @OneToMany(mappedBy = "store")
    // List<Products> products;

    @OneToMany(mappedBy = "store")
    List<Branch> branches;
    
    @ManyToMany
    @JoinTable(
        name = "store_category", // Tên bảng trung gian
        joinColumns = @JoinColumn(name = "store_id"), // Khóa ngoại trỏ đến Store
        inverseJoinColumns = @JoinColumn(name = "category_id") // Khóa ngoại trỏ đến Category
    )
    Set<Category> categories;

    @OneToMany(mappedBy = "store")
    List<Employee> employees;
}
