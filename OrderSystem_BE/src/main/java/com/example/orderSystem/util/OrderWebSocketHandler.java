package com.example.orderSystem.util;

import com.example.orderSystem.domain.order.Order;
import com.example.orderSystem.domain.order.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class OrderWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OrderRepository orderRepository;

    public OrderWebSocketHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        sendExistingOrdersToClient(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    public void broadcastOrder(List<Order> order) {
        try {
            String orderJson = objectMapper.writeValueAsString(order);
            TextMessage message = new TextMessage(orderJson);

            for (WebSocketSession session : sessions) {
                session.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 새로 연결된 경우 기존 데이터 노출 함수
    private void sendExistingOrdersToClient(WebSocketSession session) {
        try {
            List<Order> result = orderRepository.findAll();
            String orderJson = objectMapper.writeValueAsString(result);

            TextMessage message = new TextMessage(orderJson);
            session.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}