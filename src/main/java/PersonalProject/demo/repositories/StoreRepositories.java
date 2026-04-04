package PersonalProject.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import PersonalProject.demo.models.Store;

@Repository
public interface StoreRepositories extends JpaRepository<Store, Long> {
    // @Query("SELECT s FROM Store s WHERE s.storeAdmin.id = :adminId")
    Store findByStoreAdminId(Long adminId);

    List<Store> findByBranchId(Long branchId);
}
