package PersonalProject.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AbstractTenantModel extends AbstractModel{
    @Column(name = "tenant_id", nullable = false, updatable = false)
    private Long tenantId; // Liên kết với id của Entity Tenant
}
