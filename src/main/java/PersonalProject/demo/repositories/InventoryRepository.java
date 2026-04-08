package PersonalProject.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import PersonalProject.demo.models.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByBranchId(Long branchId);

    // @Query("SELECT i FROM Inventory i JOIN i.products p WHERE p.id = :productId")
    // Inventory findByProductId(@Param("productId") Long productId);
}
