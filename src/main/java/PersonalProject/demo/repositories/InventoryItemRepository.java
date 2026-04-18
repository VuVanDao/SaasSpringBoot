package PersonalProject.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PersonalProject.demo.models.InventoryItem;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem,Long>{
    InventoryItem findByInventoryIdAndProductId(Long inventoryId, Long productId);
}
