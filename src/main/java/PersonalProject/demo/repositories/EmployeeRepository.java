package PersonalProject.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import PersonalProject.demo.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
}
