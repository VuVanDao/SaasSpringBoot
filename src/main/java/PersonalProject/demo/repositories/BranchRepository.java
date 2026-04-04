package PersonalProject.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PersonalProject.demo.models.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    
}
