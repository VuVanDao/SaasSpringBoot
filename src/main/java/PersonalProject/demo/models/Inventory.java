package PersonalProject.demo.models;

import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Inventory extends AbstractTenantModel {

    @ManyToOne
    Branch branch;

    String inventoryName;
}
