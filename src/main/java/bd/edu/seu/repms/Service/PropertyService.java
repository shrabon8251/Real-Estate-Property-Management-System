package bd.edu.seu.repms.Service;

import bd.edu.seu.repms.Entity.Property;
import bd.edu.seu.repms.Repository.PropertyRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@AllArgsConstructor
@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;


     public Property saveProperty(Property property){
         return propertyRepository.save(property);
     }

     public List<Property>getAllProperties(){
         return propertyRepository.findAll();
     }

     public Property getPropertyById(Long id){
         return propertyRepository.findById(id).orElse(null);
     }

     public Property updateProperty( Property property) {return propertyRepository.save(property);}
     public void deleteProperty(Long id){
         propertyRepository.deleteById(id);
     }



    public List<Property> searchAndFilterProperties(
            String keyword,
            String propertyType,
            String listingType,
            String status,
            Double minPrice,
            Double maxPrice) {

        // Database থেকে সব property load
        List<Property> properties = propertyRepository.findAll();

        // Search এবং Filter
        return properties.stream()
                .filter(property ->

                        // Search by Title or Location
                        (keyword == null ||
                                keyword.isBlank() ||
                                property.getTitle()
                                        .toLowerCase()
                                        .contains(keyword.toLowerCase()) ||
                                property.getLocation()
                                        .toLowerCase()
                                        .contains(keyword.toLowerCase()))

                                &&

                                // Property Type Filter
                                (propertyType == null ||
                                        propertyType.isBlank() ||
                                        property.getPropertyType()
                                                .equalsIgnoreCase(propertyType))

                                &&

                                // Listing Type Filter
                                (listingType == null ||
                                        listingType.isBlank() ||
                                        property.getListingType()
                                                .equalsIgnoreCase(listingType))

                                &&

                                // Status Filter
                                (status == null ||
                                        status.isBlank() ||
                                        property.getStatus()
                                                .equalsIgnoreCase(status))

                                &&

                                // Minimum Price Filter
                                (minPrice == null ||
                                        property.getPrice() >= minPrice)

                                &&

                                // Maximum Price Filter
                                (maxPrice == null ||
                                        property.getPrice() <= maxPrice)

                )
                .toList();
    }

    public String saveImage(MultipartFile image) throws IOException, IOException {

        String fileName = image.getOriginalFilename();

        Path uploadPath = Paths.get("src/main/resources/static/uploads");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);

        Files.copy(
                image.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        return fileName;
    }
}
