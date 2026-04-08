package PersonalProject.demo.models;

import java.math.BigDecimal;

import PersonalProject.demo.domain.EmployeeRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
public class Employee extends AbstractTenantModel {
    @OneToOne
    User user;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    Store store;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    Branch branch;

    @Enumerated(EnumType.STRING)
    EmployeeRole employeeRole;

    String email;
    String phone;
    String employeeCode; // Unique code for each employee
    BigDecimal salary;
}
