package PersonalProject.demo.models;

import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Branch extends AbstractModel{
    String name;
    String address;
    String phone;
    String email;
    String workingDay;
    LocalTime openingTime;
    LocalTime closingTime;

    @OneToMany(mappedBy = "branch")
    List<Store> stores;

    @OneToOne
    User manager;
}
