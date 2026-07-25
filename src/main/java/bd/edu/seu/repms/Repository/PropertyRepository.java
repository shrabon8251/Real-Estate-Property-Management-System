package bd.edu.seu.repms.Repository;

import bd.edu.seu.repms.Entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property,Long> {

}
