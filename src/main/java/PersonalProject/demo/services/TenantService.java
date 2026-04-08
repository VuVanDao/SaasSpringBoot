package PersonalProject.demo.services;

import java.util.List;

import PersonalProject.demo.Dto.Request.CreateTenantRequest;
import PersonalProject.demo.Dto.Response.TenantDto;

public interface TenantService {
    TenantDto createTenant(CreateTenantRequest request);

    TenantDto getTenantById(Long tenantId);

    List<TenantDto> getAllTenants();

    TenantDto updateTenant(Long tenantId, CreateTenantRequest request);
    
    void deleteTenant(Long tenantId);
}
