package PersonalProject.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PersonalProject.demo.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    // Lấy tất cả employee của một store và tenant
    List<Employee> findAllByStoreIdAndTenantId(Long storeId, Long tenantId);
    
    // Lấy employee theo user
    Optional<Employee> findByUserId(Long userId);
    
    // Kiểm tra user đã là employee của store chưa
    boolean existsByUserIdAndStoreId(Long userId, Long storeId);

    // Lấy nhân viên theo branch
    List<Employee> findAllByBranchIdAndTenantId(Long branchId, Long tenantId);

    Optional<Employee> findByIdAndTenantId(Long id, Long tenantId);

    Optional<Employee> findByUserIdAndTenantId(Long user_id, Long tenantId);

    Optional<Employee> findByBranchIdAndTenantId(Long branch_id, Long tenantId);
}
