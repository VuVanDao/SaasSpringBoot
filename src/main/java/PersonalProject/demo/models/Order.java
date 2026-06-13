package PersonalProject.demo.models;

import java.util.List;

import PersonalProject.demo.Enums.OrderStatus;
import PersonalProject.demo.Enums.PaymentType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
// @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends AbstractTenantModel{
    Double totalAmount;

    // 1 don hang thuoc ve 1 chi nhanh, 1 chi nhanh co nhieu don hang
    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    Branch branch;

    // 1 don hang duoc tao boi 1 nhan vien thu ngan, 1 nhan vien thu ngan co the tao nhieu don hang
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User cashier;

    // 1 don hang thuoc ve 1 khach hang, 1 khach hang co the co nhieu don hang
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;

    @Enumerated(EnumType.STRING)
    PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    // OrderItem: 1 don hang co nhieu san pham, 1 san pham co the thuoc ve nhieu don hang
    @OneToMany(mappedBy = "order")
    List<OrderItem> orderItems;
}
