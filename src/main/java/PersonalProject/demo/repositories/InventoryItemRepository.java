package PersonalProject.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PersonalProject.demo.models.InventoryItem;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem,Long>{
    InventoryItem findByInventoryIdAndProductId(Long inventoryId, Long productId);

    List<InventoryItem> findAllByInventoryIdAndTenantId(Long inventoryItemId, Long tenantId);
    List<InventoryItem> findAllByProductIdAndTenantId(Long productId, Long tenantId);
}
