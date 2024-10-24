package com.example.orderSystem.domain.order;

import com.example.orderSystem.api.order.dto.OrderCreateRequestDto;

import java.util.List;

public interface OrderRepository {

    List<Order> findAll();

    void save(OrderCreateRequestDto dto);

    
}
