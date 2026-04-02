package PersonalProject.demo.models;

import java.util.List;

import PersonalProject.demo.domain.StoreContact;
import PersonalProject.demo.domain.StoreStatus;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Store extends AbstractModel {
    String brand;

    // @OneToMany(mappedBy = "store")
    // List<User> storeAdmin;
    @OneToOne 
    User storeAdmin;

    String description;

    String storeType;
    
    @Enumerated(EnumType.STRING)
    StoreStatus storeStatus;

    @Embedded
    StoreContact storeContact = new StoreContact();

    // mappedBy = "store" nghĩa là gì
    /*
        Nó có nghĩa là: "Cái bảng này không giữ khóa ngoại. Hãy nhìn vào thuộc tính tên là 'store' ở thực thể bên kia để biết cách liên kết."
        store: Đây không phải tên bảng trong Database, mà là tên của biến (field) bạn khai báo trong thực thể Product
     */
    @OneToMany(mappedBy = "store")
    List<Products> products;
}
