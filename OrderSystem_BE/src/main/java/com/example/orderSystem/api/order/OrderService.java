package com.example.orderSystem.api.order;

import com.example.orderSystem.api.order.dto.OrderCreateRequestDto;
import com.example.orderSystem.api.order.dto.OrderResponseDto;
import com.example.orderSystem.domain.order.Order;
import com.example.orderSystem.domain.order.OrderRepository;
import com.example.orderSystem.util.OrderWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderWebSocketHandler orderWebSocketHandler;

    @Transactional
    public OrderResponseDto createOrder(OrderCreateRequestDto dto) {
        // 요청 정보 저장
        Order order = orderRepository.save(Order.of(dto));

        // 웹소켓 발행
        responseWebsocket();

        // DTO로 응답
        return OrderResponseDto.of(order);
    }

    @Transactional
    public OrderResponseDto updateOrder(Long orderId) {
        Order order = orderRepository.updateOrder(orderId);
        responseWebsocket();

        return OrderResponseDto.of(order);
    }

    @Deprecated
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    // 전체 주문 조회 후 메시지 전송
    private void responseWebsocket() {
        List<Order> result = orderRepository.findAll();
        orderWebSocketHandler.broadcastOrder(result);
    }
}
