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
        System.out.println("Property before save = " + property);

        property.setInsertDate(LocalDate.now());


        if (!image.isEmpty()) {

            String fileName = propertyService.saveImage(image);

            property.setImagePath(fileName);
        }


        propertyService.saveProperty(property);

        System.out.println("Property saved successfully");

        propertyService.saveProperty(property);

        return "redirect:/property/list";

    }



    @GetMapping("/list")
    public String propertyList(Model model) {

        model.addAttribute(
                "properties",
                propertyService.getAllProperties()
        );

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

        if (!image.isEmpty()) {

            String fileName = propertyService.saveImage(image);

            property.setImagePath(fileName);
        }

        propertyService.updateProperty(property);

        return "redirect:/property/details/" + property.getId();
    }
}
