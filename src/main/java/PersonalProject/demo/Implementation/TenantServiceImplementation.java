package PersonalProject.demo.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import PersonalProject.demo.Dto.Request.CreateTenantRequest;
import PersonalProject.demo.Dto.Response.TenantDto;
import PersonalProject.demo.domain.ErrorCode;
import PersonalProject.demo.exception.ResourceNotFoundException;
import PersonalProject.demo.mapper.TenantMapper;
import PersonalProject.demo.models.Tenant;
import PersonalProject.demo.repositories.TenantRepository;
import PersonalProject.demo.services.TenantService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TenantServiceImplementation implements TenantService {
    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;
    @Override
    public TenantDto createTenant(CreateTenantRequest request) {
        Tenant tenant = tenantMapper.toModel(request);
        Tenant savedTenant = tenantRepository.save(tenant);
        return tenantMapper.toDto(savedTenant);
    }

    @Override
    public TenantDto getTenantById(Long tenantId) {
        return tenantMapper.toDto(tenantRepository.findById(tenantId).orElseThrow(()-> new ResourceNotFoundException((ErrorCode.Resource_not_found))));
    }

    @Override
    public List<TenantDto> getAllTenants() {
        return tenantRepository.findAll().stream()
                .map(tenantMapper::toDto)
                .toList();
    }

    @Override
    public TenantDto updateTenant(Long tenantId, CreateTenantRequest request) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
        tenant.setDomain(request.getDomain());
        tenant.setName(request.getName());
        tenant.setStatus(request.getStatus());
        tenant.setExpiryDate(request.getExpiryDate());
        tenantRepository.save(tenant);
        return tenantMapper.toDto(tenant);
    }

    @Override
    public void deleteTenant(Long tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
        tenantRepository.delete(tenant);
    }
    
}
