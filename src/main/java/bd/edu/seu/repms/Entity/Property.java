package bd.edu.seu.repms.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@Entity
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Title status is required")
    @Size(min = 5, max = 100 ,message = "Title must be between 5 and 100 character")
    private String title;

    @NotBlank(message = "Property Type required")
    private String propertyType;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be gather than 0")
    private Double price;

    @NotBlank(message = "Location is required")
    private String location;

    @Min(value = 0, message = "Bedrooms can't be negative")
    private Integer bedrooms;

    @Min(value = 0, message = "Bathroom can't be negative")
    private Integer bathrooms;

    @NotNull(message = "Area is required")
    @Positive(message = "Area must be greater than zero")
    private Double area;

    @Size(max = 1000, message = "Description can be up to 1000 characters")
    private String description;

    @NotBlank(message = "Status is required")
    private String status;

    @NotBlank(message = "Listing Type is required")
    private String listingType;

    private String imagePath;

    private LocalDate insertDate;

    @NotNull(message = "Expected Date Required")
    @FutureOrPresent(message = "Expected date must be today or in the future")
    private LocalDate expectedDate;

//    @NotBlank(message = "Sold Or Rented Required")
//    @PastOrPresent(message = "Sold or rented date cannot be in the future")
    private LocalDate soldOrRentedDate;
}
