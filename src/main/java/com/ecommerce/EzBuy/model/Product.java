package com.ecommerce.EzBuy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long  productId;

    @NotBlank
    @Size(min = 5, message = "Product name must contain at least 5 characters")
    private String productName;

    @NotBlank
    @Size(min = 6, message = "Product description must contain at least 6 characters")
    private String description;
    private Integer quantity;
    private double price;
    private double discount;
    private String image;
    private double specialPrice;

    @ManyToOne
    @JoinColumn(name ="category_id")
    private Category category;

}
