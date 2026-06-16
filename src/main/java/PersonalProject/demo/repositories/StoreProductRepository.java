package PersonalProject.demo.repositories;

import PersonalProject.demo.models.StoreProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StoreProductRepository extends JpaRepository<StoreProduct, Long> {
    Optional<StoreProduct> findByStoreIdAndProductId(Long storeId, Long productId);
    boolean existsByStoreIdAndProductId(Long storeId, Long productId);
}
