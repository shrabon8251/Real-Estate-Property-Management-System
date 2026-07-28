package bd.edu.seu.repms.Repository;

import bd.edu.seu.repms.Entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property,Long> {

    List<Property> findByTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(
            String title,
            String location
    );

    List<Property> findByPropertyType(String propertyType);

    List<Property> findByListingType(String listingType);

    List<Property> findByStatus(String status);

}
