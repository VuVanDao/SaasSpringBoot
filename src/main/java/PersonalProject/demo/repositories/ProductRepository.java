package PersonalProject.demo.repositories;

import PersonalProject.demo.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

    // List<Products> findByStoreId(Long storeId);

    // @Query("SELECT p FROM Products p WHERE p.store.id = :storeId AND " +
    //         "(LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :query, '%'))) OR" + 
    //         "(LOWER(p.sku) LIKE LOWER(CONCAT('%', :query, '%')))" + 
    //         "ORDER by p.createdAt DESC"
    // )
    // List<Products> searchByQuery(@Param("storeId") Long storeId, @Param("query") String query);

    @Query("SELECT p FROM Products p LEFT JOIN FETCH p.categories")
    List<Products> findAllWithCategories();
}