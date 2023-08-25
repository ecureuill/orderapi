package com.ecureuill.ada.avanade.orderapi.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.ecureuill.ada.avanade.exceptions.InsufficientStockException;
import com.ecureuill.ada.avanade.exceptions.NotFoundException;
import com.ecureuill.ada.avanade.orderapi.dto.OrderItemRecord;
import com.ecureuill.ada.avanade.orderapi.dto.OrderRecord;
import com.ecureuill.ada.avanade.orderapi.entity.OrderEntity;
import com.ecureuill.ada.avanade.orderapi.entity.OrderItemEntity;
import com.ecureuill.ada.avanade.orderapi.entity.ProductEntity;
import com.ecureuill.ada.avanade.orderapi.repository.OrderRepository;
import com.ecureuill.ada.avanade.orderapi.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Transactional(rollbackOn = {NotFoundException.class, RuntimeException.class, InsufficientStockException.class})
    public Long create(OrderRecord order) throws NotFoundException, InsufficientStockException, RuntimeException{
        
        OrderEntity orderEntity = new OrderEntity(null, new BigDecimal(0), LocalDateTime.now(), new ArrayList<OrderItemEntity>());

        for(OrderItemRecord item: order.items()){
            ProductEntity product = productService.findById(item.productId()).toEntity(item.productId());
            OrderItemEntity orderItem = new OrderItemEntity(item.quantity(), orderEntity,product);
            
            var items = orderEntity.getItens();
            items.add(orderItem);
            orderEntity.setItens(items);

            updateProductStock(product, item.quantity());

            orderEntity.setTotalValue(orderEntity.getTotalValue().add(product.getPrice().multiply(new BigDecimal(item.quantity()))));
        }


        orderRepository.save(orderEntity);
        return orderEntity.getId();
    }
    
    private void updateProductStock(ProductEntity product, int quantity) {
        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock");
        } else {
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
        }
    }
}
