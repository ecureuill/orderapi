package com.ecureuill.ada.avanade.orderapi.entity;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "products")
@Entity(name = "Product")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductEntity {
    @Id
    private float id;
    private String title;
    private String description;
    private float price;
    private float discountPercentage;
    private float rating;
    private float stock;
    private String brand;
    private String category;
    private String thumbnail;
    ArrayList<String> images = new ArrayList<String> ();
}
