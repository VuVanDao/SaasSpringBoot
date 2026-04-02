package PersonalProject.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import PersonalProject.demo.models.Category;

public interface CategoryRepositories extends JpaRepository<Category, Long> {
    
}
