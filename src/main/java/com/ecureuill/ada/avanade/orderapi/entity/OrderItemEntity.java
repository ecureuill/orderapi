package com.ecureuill.ada.avanade.orderapi.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "OrderItem")
@Table(name = "order_items")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEntity {
    private Integer quantity;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    @EmbeddedId
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @EmbeddedId
    private ProductEntity product;
    
}
