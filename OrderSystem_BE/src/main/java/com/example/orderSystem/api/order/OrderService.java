package com.example.orderSystem.api.order;

import com.example.orderSystem.api.order.dto.OrderCreateRequestDto;
import com.example.orderSystem.domain.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void createOrder(OrderCreateRequestDto dto) {
        orderRepository.save(dto);
    }
}
