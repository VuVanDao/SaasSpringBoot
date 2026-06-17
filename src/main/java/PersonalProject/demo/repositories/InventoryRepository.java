package PersonalProject.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import PersonalProject.demo.models.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByBranchId(Long branchId);

    // @Query("SELECT i FROM Inventory i JOIN i.products p WHERE p.id = :productId")
    // Inventory findByProductId(@Param("productId") Long productId);

    Optional<Inventory> findByIdAndTenantId(Long id, Long tenant_id);

    List<Inventory> findAllByBranchIdAndTenantId(Long id, Long tenant_id);

    List<Inventory> findAllByTenantId(Long tenantId);
}
