package com.example.orderSystem.util;

import com.example.orderSystem.api.order.dto.OrderCreateRequestDto;
import com.example.orderSystem.domain.order.Order;
import com.example.orderSystem.domain.order.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class OrderWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Queue<OrderCreateRequestDto> orderQueue = new ConcurrentLinkedQueue<>();
    private final OrderRepository orderRepository;
    private boolean isProcessing = false; // 처리 중인지 확인하는 플래그


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

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 메시지 내용 확인
        String payload = message.getPayload();

        // JSON 형식이 유효한지 확인
        if (isValidJson(payload)) {
            OrderCreateRequestDto dto = objectMapper.readValue(payload, OrderCreateRequestDto.class);

            // 큐에 주문 추가
            orderQueue.add(dto);

            // 주문을 비동기적으로 처리 (새로운 스레드에서 실행)
            processOrderQueue();
        } else {
            System.out.println("Received non-JSON message: " + payload);
        }
    }

    // JSON 유효성 검사 메서드
    private boolean isValidJson(String str) {
        try {
            objectMapper.readTree(str);
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    private void processOrderQueue() {
        // 큐에서 주문을 꺼내서 처리
        if (isProcessing) {
            return;
        }
        isProcessing = true;

        try {
            OrderCreateRequestDto dto;
            while ((dto = orderQueue.poll()) != null) {
                Order newOrder = Order.of(dto);
                orderRepository.save(newOrder);
                broadcastOrder(List.of(newOrder));
            }
        } finally {
            isProcessing = false;
        }
    }
}