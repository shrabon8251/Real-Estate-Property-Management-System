package bd.edu.seu.repms.Controller;

import bd.edu.seu.repms.Entity.Property;
import bd.edu.seu.repms.Service.PropertyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
@Controller
@RequestMapping("/property")
public class PropertyController {
    private final PropertyService propertyService;

    @GetMapping("/add")
    public String showAddPropertyForm(Model model){
        model.addAttribute("property",new Property());
        return "add-property";

    }
    @PostMapping("/save")
    public  String saveProperty(@Valid @ModelAttribute Property property,
                                BindingResult bindingResult,
                                @RequestParam("image") MultipartFile image) throws IOException {

        if(bindingResult.hasErrors()){
            return "add-property";
        }


        property.setInsertDate(LocalDate.now());


        if (!image.isEmpty()) {

            String fileName = propertyService.saveImage(image);

            property.setImagePath(fileName);
        }


        propertyService.saveProperty(property);

        System.out.println("Property saved successfully");



        return "redirect:/property/list";

    }



    @GetMapping("/list")
    public String propertyList(

            @RequestParam(required = false) String keyword,

            @RequestParam(required = false) String propertyType,

            @RequestParam(required = false) String listingType,

            @RequestParam(required = false) String status,

            @RequestParam(required = false) Double minPrice,

            @RequestParam(required = false) Double maxPrice,

            Model model) {


        List<Property> properties =
                propertyService.searchAndFilterProperties(
                        keyword,
                        propertyType,
                        listingType,
                        status,
                        minPrice,
                        maxPrice
                );


        model.addAttribute("properties", properties);
        model.addAttribute("keyword", keyword);
        model.addAttribute("propertyType", propertyType);
        model.addAttribute("listingType", listingType);
        model.addAttribute("status", status);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);


        return "property-list";
    }

    @GetMapping("/details/{id}")
    public String propertyDetails(
            @PathVariable Long id,
            Model model) {

        Property property = propertyService.getPropertyById(id);

        model.addAttribute("property", property);

        return "property-details";
    }


    @GetMapping("/delete/{id}")
    public String deleteProperty(
            @PathVariable Long id) {

        propertyService.deleteProperty(id);

        return "redirect:/property/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {

        Property property = propertyService.getPropertyById(id);

        model.addAttribute("property", property);

        return "edit-property";
    }

    @PostMapping("/update")
    public String updateProperty(
            @Valid @ModelAttribute("property") Property property,
            BindingResult bindingResult,
            @RequestParam("image") MultipartFile image) throws IOException {

        if (bindingResult.hasErrors()) {
            return "edit-property";
        }

        Property existingProperty=propertyService.getPropertyById(property.getId());

        if (!image.isEmpty()) {

            String fileName = propertyService.saveImage(image);

            property.setImagePath(fileName);
        }else {
            property.setImagePath(existingProperty.getImagePath());
        }

        propertyService.updateProperty(property);

        return "redirect:/property/details/" + property.getId();
    }
}
