package com.example.orderSystem.domain.order;

import com.example.orderSystem.api.order.dto.OrderCreateRequestDto;
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
    public void save(OrderCreateRequestDto dto) {
        Order order = Order.builder()
                .id(ID)
                .name(dto.getName())
                .quantity(dto.getQuantity())
                .build();

        ORDERS.put(order.getId(),order);
        ID++;
    }
}
