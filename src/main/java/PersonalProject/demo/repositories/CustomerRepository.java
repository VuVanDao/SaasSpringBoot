package PersonalProject.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PersonalProject.demo.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByIdAndTenantId(Long id, Long tenantId);
    List<Customer> findAllByTenantId(Long tenantId);
}
