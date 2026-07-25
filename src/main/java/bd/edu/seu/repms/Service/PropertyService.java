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


//     public Property updatePropert(Property property){
//         return propertyRepository.save(property);
//
//     }
    public Property updateProperty(Property property) {
        return propertyRepository.save(property);
    }
     public void deleteProperty(Long id){
         propertyRepository.deleteById(id);
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
