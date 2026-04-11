package PersonalProject.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import PersonalProject.demo.models.Category;

public interface CategoryRepositories extends JpaRepository<Category, Long> {
    @Query("select c from Category c where c.tenantId = :tenantId")
    List<Category> findAllWithTenantId(@Param("tenantId") Long tenantId);

    @Query("SELECT c FROM Category c WHERE c.tenantId = :tenantId OR c.isSystemDefault = true")
    List<Category> findAllByTenantIdOrIsSystemDefaultTrue(Long tenantId);
}
