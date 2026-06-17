package PersonalProject.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PersonalProject.demo.models.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    List<Branch> findAllByIdInAndTenantId(List<Long> ids, Long tenantId);
    Optional<Branch> findByIdAndTenantId(Long id, Long tenantId);
    List<Branch> findAllByTenantId(Long tenantId);
}
