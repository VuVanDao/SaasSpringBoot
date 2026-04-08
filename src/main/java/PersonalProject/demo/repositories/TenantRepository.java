package PersonalProject.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PersonalProject.demo.models.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Tenant findByDomain(String domain);
    Tenant findByName(String name);
}
