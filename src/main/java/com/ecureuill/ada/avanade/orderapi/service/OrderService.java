package com.ecureuill.ada.avanade.orderapi.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.ecureuill.ada.avanade.orderapi.dto.OrderItemRecord;
import com.ecureuill.ada.avanade.orderapi.dto.OrderRecord;
import com.ecureuill.ada.avanade.orderapi.entity.CostumerEntity;
import com.ecureuill.ada.avanade.orderapi.entity.OrderEntity;
import com.ecureuill.ada.avanade.orderapi.entity.OrderItemEntity;
import com.ecureuill.ada.avanade.orderapi.entity.ProductEntity;
import com.ecureuill.ada.avanade.orderapi.exceptions.InsufficientStockException;
import com.ecureuill.ada.avanade.orderapi.exceptions.NotFoundException;
import com.ecureuill.ada.avanade.orderapi.repository.CostumerRepository;
import com.ecureuill.ada.avanade.orderapi.repository.OrderRepository;
import com.ecureuill.ada.avanade.orderapi.repository.ProductRepository;
import com.ecureuill.ada.avanade.orderapi.repository.UserRepository;
import com.ecureuill.ada.avanade.orderapi.utils.AuthenticatedUser;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final CostumerRepository costumerRepository;

    public Long create(OrderRecord order) throws Exception{
        
        var user = userRepository.findByUsername(AuthenticatedUser.getUsername());
        var customer = costumerRepository.findByUserId(user.get().getId());

        if(customer.isEmpty()){
            throw new Exception("You need to register you name and address first (create a customer)");
        }

        var id = saveOrder(order, customer.get());

        emailService.sendEmail("logikasciuro@gmail.com", "Placed Order!", "A new order have been placed.\n\n Order id is: " + id + "\n\nThanks");

        emailService.sendEmail(customer.get().getEmail(), "Order Completed!", "Hello " + customer.get().getName() + "Your order has been completed successfully\n" + "Your order id is: " + id + "\n" + "Thanks!");

        return id;
    }

    @Transactional(rollbackOn = {NotFoundException.class, RuntimeException.class, InsufficientStockException.class})    
    private Long saveOrder(OrderRecord order, CostumerEntity customer) throws NotFoundException, InsufficientStockException, RuntimeException {
        OrderEntity orderEntity = new OrderEntity(null, new BigDecimal(0), LocalDateTime.now(), new ArrayList<OrderItemEntity>(), customer);

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
    };


    private void updateProductStock(ProductEntity product, int quantity) throws InsufficientStockException {
        if (product.getStock() < quantity) {
            throw new InsufficientStockException("Not enough stock");
        } else {
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
        }
    }
}
