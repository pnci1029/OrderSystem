package com.example.orderSystem.domain.order;

import java.util.List;

public interface OrderRepository {

    List<Order> findAll();

    Order save(Order dto);

    Order updateOrder(Long orderId);

    void deleteAll();
    
}
