package PersonalProject.demo.mapper;

import org.springframework.stereotype.Component;

import PersonalProject.demo.Dto.Request.CreateTenantRequest;
import PersonalProject.demo.Dto.Response.TenantDto;
import PersonalProject.demo.domain.TenantStatus;
import PersonalProject.demo.domain.UserRole;
import PersonalProject.demo.models.Tenant;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TenantMapper {
    public TenantDto toDto(Tenant tenant) {
        return TenantDto.builder()
                .id(tenant.getId())
                .name(tenant.getName())
                .status(tenant.getStatus())
                .domain(tenant.getDomain())
                .expiryDate(tenant.getExpiryDate())
                .createdAt(tenant.getCreatedAt())
                .updatedAt(tenant.getUpdatedAt())
                .build();
    }

    public Tenant toModel(TenantDto tenantDto) {
        return Tenant.builder()
                .name(tenantDto.getName())
                .domain(tenantDto.getDomain())
                .expiryDate(tenantDto.getExpiryDate())
                .build();
    }

    public Tenant toModel(CreateTenantRequest tenantDto) {
        TenantStatus role = TenantStatus.ACTIVE;
        if (tenantDto.getStatus() != null) {
            role = tenantDto.getStatus();
        }
        return Tenant.builder()
                .name(tenantDto.getName())
                .domain(tenantDto.getDomain())
                .status(role)
                .expiryDate(tenantDto.getExpiryDate())
                .build();
    }


}
