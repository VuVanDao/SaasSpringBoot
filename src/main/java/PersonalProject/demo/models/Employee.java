package PersonalProject.demo.models;

import java.math.BigDecimal;

import PersonalProject.demo.Enums.EmployeeRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends AbstractTenantModel {
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user; // User account của nhân viên

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    Store store; // Store mà nhân viên thuộc về

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = true)
    Branch branch;  // Branch cụ thể (có thể chưa có)

    @Enumerated(EnumType.STRING)
    EmployeeRole employeeRole;

    @Column(unique = true)
    String employeeCode; // Unique code for each employee
    
    BigDecimal salary;
}
