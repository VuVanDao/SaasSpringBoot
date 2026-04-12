package PersonalProject.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import PersonalProject.demo.models.Store;

@Repository
public interface StoreRepositories extends JpaRepository<Store, Long> {
    @Query("SELECT s FROM Store s LEFT JOIN FETCH s.categories WHERE s.storeAdmin.id = :adminId")
    Store findIncludeCategory(@Param("adminId") Long adminId);

    Store findByStoreAdminId(Long adminId);

    @Query("SELECT s FROM Store s WHERE s.tenantId = :tenantId")
    List<Store> findAllStore(Long tenantId);

    Optional<Store> findByTenantId(Long tenantId);
}
