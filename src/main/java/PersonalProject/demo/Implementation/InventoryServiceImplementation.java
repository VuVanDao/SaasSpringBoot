package PersonalProject.demo.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import PersonalProject.demo.Dto.Request.CreateInventoryRequest;
import PersonalProject.demo.Dto.Request.UpdateInventoryRequest;
import PersonalProject.demo.Dto.Response.InventoryDto;
import PersonalProject.demo.Enums.ErrorCode;
import PersonalProject.demo.exception.ResourceNotFoundException;
import PersonalProject.demo.exception.TenantException;
import PersonalProject.demo.mapper.BranchMapper;
import PersonalProject.demo.mapper.ProductMapper;
import PersonalProject.demo.models.Branch;
import PersonalProject.demo.models.Inventory;
import PersonalProject.demo.models.Products;
import PersonalProject.demo.repositories.BranchRepository;
import PersonalProject.demo.repositories.InventoryRepository;
import PersonalProject.demo.repositories.ProductRepository;
import PersonalProject.demo.services.InventoryService;
import PersonalProject.demo.utils.TenantUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryServiceImplementation implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;
    private final BranchMapper branchMapper;
    private final ProductMapper productMapper;
    private final TenantUtil tenantUtil;

    @Override
    @Transactional
    public InventoryDto createInventory(CreateInventoryRequest request,  HttpServletRequest request2) {
        Long tenantId = tenantUtil.validateTenant(request2);
        Branch branch = branchRepository.findById(request.getBranchId())
                        .orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
        if (branch.getTenantId() != tenantId) {
                throw new TenantException(ErrorCode.Tenant_Exception);
        }
        Inventory inventory = Inventory.builder()
                .branch(branch)
                .inventoryName(request.getInventoryName())
                .tenantId(tenantId)
                .build();

        Inventory saved = inventoryRepository.save(inventory);
        return InventoryDto.builder()
                .id(saved.getId())
                // .branch(branchMapper.convertToDto(saved.getBranch()))
                .inventoryName(saved.getInventoryName())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .tenant_id(tenantId)
                .build();
    }

    @Override
    @Transactional
    public InventoryDto updateInventory(Long id, UpdateInventoryRequest request,  HttpServletRequest request2) {
        Long tenantId = tenantUtil.validateTenant(request2);
        Inventory inventory = inventoryRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
        if (inventory.getTenantId() != tenantId) {
                throw new TenantException(ErrorCode.Tenant_Exception);
        }
        Branch branch = inventory.getBranch();
        if (request.getBranchId() != null && request.getBranchId() != branch.getId()) {
                branch = branchRepository.findById(request.getBranchId())
                                .orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
                if (branch.getTenantId() != tenantId) {
                        throw new TenantException(ErrorCode.Tenant_Exception);
                }

                inventory.setBranch(branch);
        }
        inventory.setInventoryName(request.getInventoryName());
        Inventory updated = inventoryRepository.save(inventory);
        return InventoryDto.builder()
                .id(updated.getId())
                .branch(branchMapper.convertToDto(updated.getBranch()))
                .inventoryName(updated.getInventoryName())
                .createdAt(updated.getCreatedAt())
                .updatedAt(updated.getUpdatedAt())
                .build();
    }

    @Override
    public void deleteInventory(Long id, HttpServletRequest request2) {
        Long tenantId = tenantUtil.validateTenant(request2);

        Inventory inventory = inventoryRepository.findByIdAndTenantId(id,tenantId)
                .orElseThrow(() ->new ResourceNotFoundException((ErrorCode.Resource_not_found)));
        inventoryRepository.delete(inventory);
    }

    @Override
    @Transactional
    public InventoryDto getInventoryById(Long id, HttpServletRequest request2) {
        Long tenantId = tenantUtil.validateTenant(request2);

        Inventory inventory = inventoryRepository.findByIdAndTenantId(id,tenantId)
                .orElseThrow(() ->new ResourceNotFoundException((ErrorCode.Resource_not_found)));
          return InventoryDto.builder()
                .id(inventory.getId())
                .branch(branchMapper.convertToDto(inventory.getBranch()))
                .inventoryName(inventory.getInventoryName())
                .createdAt(inventory.getCreatedAt())
                .updatedAt(inventory.getUpdatedAt())
                .tenant_id(inventory.getTenantId())
                .build();
    }

    // CHECK: khi nào đó sẽ thiết kế lại api lỏ vcl này
    @Override
    public InventoryDto getProductInInventory(Long productId) {
        // Inventory inventory = inventoryRepository.findByProductId(productId);
        // if (inventory == null) {
        //     throw new ResourceNotFoundException("Inventory for product not found");
        // }
        // return InventoryDto.builder()
        //         .id(inventory.getId())
        //         .branch(branchMapper.convertToDto(inventory.getBranch()))
        //         .inventoryName(inventory.getInventoryName())
        //         .createdAt(inventory.getCreatedAt())
        //         .updatedAt(inventory.getUpdatedAt())
        //         .build();
        return null;
    }

    @Override
    @Transactional
    public List<InventoryDto> getAllInventoryByBranchId(Long branchId, HttpServletRequest request2) {
        Long tenantId = tenantUtil.validateTenant(request2);

        List<Inventory> inventories = inventoryRepository.findAllByBranchIdAndTenantId(branchId,tenantId);
        return inventories.stream()
                .map(inv -> InventoryDto.builder()
                        .id(inv.getId())
                        .branch(branchMapper.convertToDto(inv.getBranch()))
                        .inventoryName(inv.getInventoryName())
                        .createdAt(inv.getCreatedAt())
                        .updatedAt(inv.getUpdatedAt())
                        .build())
                .toList();
    }

    @Override
    @Transactional
    public List<InventoryDto> getAllInventory() {
        return inventoryRepository.findAll().stream()
                .map(inv -> InventoryDto.builder()
                        .id(inv.getId())
                        // .branch(branchMapper.convertToDto(inv.getBranch()))
                        .inventoryName(inv.getInventoryName())
                        .createdAt(inv.getCreatedAt())
                        .updatedAt(inv.getUpdatedAt())
                        .tenant_id(inv.getTenantId())
                        .branch_id(inv.getBranch().getId())
                        .build())
                .toList();
    }


}