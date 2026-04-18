package PersonalProject.demo.repositories;

import PersonalProject.demo.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

    // List<Products> findByStoreId(Long storeId);

    // @Query("SELECT p FROM Products p WHERE p.store.id = :storeId AND " +
    //         "(LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :query, '%'))) OR" + 
    //         "(LOWER(p.sku) LIKE LOWER(CONCAT('%', :query, '%')))" + 
    //         "ORDER by p.createdAt DESC"
    // )
    // List<Products> searchByQuery(@Param("storeId") Long storeId, @Param("query") String query);

    @Query("SELECT p FROM Products p LEFT JOIN FETCH p.categories")
    List<Products> findAllWithCategories();

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    @Query("SELECT DISTINCT p FROM Products p " +
       "JOIN p.categories c " +
       "JOIN c.stores s " +
       "WHERE s.id = :storeId " +
       "AND s.tenantId = :tenantId")
    List<Products> findAllWithStoreIdAndTenantId(@Param("storeId") Long storeId, @Param("tenantId") Long tenantId);
    // nó chỉ lấy các product có cate nằm trong bảng store_category, cùng với các điều kiện ở trên
    /*
    SQL thuần của findAllWithStoreIdAndTenantId:
        SELECT DISTINCT p.*
        FROM products p
        INNER JOIN product_categories pc ON p.id = pc.product_id
        INNER JOIN categories c ON pc.category_id = c.id
        INNER JOIN category_stores cs ON c.id = cs.category_id
        INNER JOIN stores s ON cs.store_id = s.id
        WHERE s.id = ? AND s.tenant_id = ?;
     */

    Optional<Products> findAllByIdAndTenantId(Long Id, Long tenantId);

    Optional<Products> findByIdAndTenantId(Long Id, Long tenantId);
}