package PersonalProject.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import PersonalProject.demo.models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    User findByEmail(String email);

    User findByFullName(String fullName);

    @Query("SELECT u FROM User u  Where u.tenantId = :tenantId")
    List<User> findAllUserByTenantId(@Param("tenantId") Long tenantId);
}
