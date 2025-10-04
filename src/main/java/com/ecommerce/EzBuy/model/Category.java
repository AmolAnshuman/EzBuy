package com.ecommerce.EzBuy.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "categories")
@Data              // Lombok -Combines @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor.
@NoArgsConstructor // Lombok - Generates constructor with no parameters.
@AllArgsConstructor // Lombok - generates a constructor with 1 parameter for each field in your class
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;

    @NotBlank
    @Size(min = 4, message = "category must contain at least 4 characters")
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;
}
