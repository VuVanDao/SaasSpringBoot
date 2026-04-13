package PersonalProject.demo.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import PersonalProject.demo.models.Category;

public interface CategoryRepositories extends JpaRepository<Category, Long> {
    @Query("select c from Category c where c.tenantId = :tenantId")
    List<Category> findAllWithTenantId(@Param("tenantId") Long tenantId);

    @Query("SELECT c FROM Category c WHERE c.tenantId = :tenantId OR c.isSystemDefault = true")
    List<Category> findAllByTenantIdOrIsSystemDefaultTrue(Long tenantId);

    @Query("SELECT DISTINCT c FROM Category c JOIN c.stores s WHERE s.id IN :storeIds")
    List<Category> findAllByStoreIds(@Param("storeIds") Set<Long> storeIds);
    
    /*
        SELECT DISTINCT c.id, c.name, ... 
        FROM category c
        INNER JOIN store_cate sc ON c.id = sc.category_id
        INNER JOIN store s ON s.id = sc.store_id
        WHERE s.id = ?;
    */
    @Query("SELECT DISTINCT c FROM Category c JOIN c.stores s WHERE s.id = :storeId")
    List<Category> findCategoriesByStoreId(@Param("storeId") Long storeId);
}
