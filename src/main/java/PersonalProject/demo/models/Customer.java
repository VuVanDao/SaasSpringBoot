package PersonalProject.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
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
@Table(name = "customer", // Khai báo Entity này sẽ được map với bảng có tên là customer dưới Database.
       uniqueConstraints = {// Thuộc tính này cho phép bạn định nghĩa các ràng buộc "Unique" (không được trùng lặp) cho bảng này.
           @UniqueConstraint(// Khai báo cụ thể một ràng buộc.
                        name = "uk_customer_email_tenant", // Đặt tên cho cái ràng buộc này. 
                            // Khi có lỗi trùng lặp dữ liệu, Database sẽ ném ra lỗi kèm theo cái tên này ("uk" thường là viết tắt của Unique Key).
                            //  Việc đặt tên giúp bạn dễ debug hơn rất nhiều.
                        columnNames = { "email", "tenant_id" }// Đây là phần quan trọng nhất. 
                            // Nó bảo với Database rằng: "Sự kết hợp giá trị của cột email VÀ cột tenant_id phải là duy nhất trên toàn bộ bảng".
           )
        })
       // link: https://aistudio.google.com/prompts/1mEYkRjaOYYltN_FnyUT-5QZnSAX15xZI ( đọc thêm để hiểu cái uniqueConstraints kia)
public class Customer extends AbstractTenantModel{
    String fullName;
    @Column(nullable = false)
    String email;
    String phone;
    String avatar;
    String address;
}
