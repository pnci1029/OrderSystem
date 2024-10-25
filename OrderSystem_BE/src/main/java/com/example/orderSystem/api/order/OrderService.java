package com.example.orderSystem.api.order;

import com.example.orderSystem.api.order.dto.OrderCreateRequestDto;
import com.example.orderSystem.domain.order.Order;
import com.example.orderSystem.domain.order.OrderRepository;
import com.example.orderSystem.util.OrderWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.util.List;

@Service @RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderWebSocketHandler orderWebSocketHandler;

    public void createOrder(OrderCreateRequestDto dto) {
        orderRepository.save(dto);

        List<Order> result = orderRepository.findAll();
        orderWebSocketHandler.broadcastOrder(result);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }


}
