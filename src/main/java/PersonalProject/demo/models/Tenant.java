package PersonalProject.demo.models;

import java.time.LocalDateTime;

import PersonalProject.demo.Enums.TenantStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class Tenant extends AbstractModel {
    String name;         // Tên công ty/chuỗi cửa hàng
    String domain;
    @Enumerated(EnumType.STRING)
    TenantStatus status; // ACTIVE, SUSPENDED, EXPIRED
    LocalDateTime expiryDate; // Ngày hết hạn thuê phần mềm
}
