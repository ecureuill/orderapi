package com.ecureuill.ada.avanade.orderapi.api;

import java.util.List;

import com.ecureuill.ada.avanade.orderapi.entity.ProductEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductList {
    private List<ProductEntity> products;
    private int total;
    private int skip;
    private int limit;

}
