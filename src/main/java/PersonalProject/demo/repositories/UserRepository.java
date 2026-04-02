package PersonalProject.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PersonalProject.demo.models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    User findByEmail(String email);
}
