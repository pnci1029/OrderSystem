package com.example.orderSystem.domain.order;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepositoryImpl implements OrderRepository{
    private static Map<Long, Order> ORDERS = new HashMap<>();
    private static Long ID = 0L;

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(ORDERS.values());
    }

    @Override
    public Order save(Order dto) {
        Order order = Order.builder()
                .id(ID++)
                .name(dto.getName())
                .quantity(dto.getQuantity())
                .build();

        ORDERS.put(order.getId(),order);

        return order;
    }

    @Override
    public void deleteAll() {
        ORDERS = new HashMap<>();
    }
}
