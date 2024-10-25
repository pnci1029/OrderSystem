package com.example.orderSystem.util;

import com.example.orderSystem.domain.order.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class OrderWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Session connected: " + session.getId()); // 세션 연결 로그
        sessions.add(session);
        System.out.println("Total sessions: " + sessions.size()); // 세션의 총 수
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    public void broadcastOrder(List<Order> order) {
        try {
            String orderJson = objectMapper.writeValueAsString(order);
            System.out.println("orderJson = " + orderJson);
            System.out.println("sessions = " + sessions.size());
            TextMessage message = new TextMessage(orderJson);
            for (WebSocketSession session : sessions) {
                System.out.println("session = " + session.getId());
                session.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public void broadcast(String message) {
//        System.out.println("message = " + message);
//        System.out.println("Number of sessions: " + sessions.size());
//        for (WebSocketSession session : sessions) {
//            System.out.println("session = " + session);
//            try {
//                session.sendMessage(new TextMessage(message));
//                System.out.println("Sent message: " + message + " to session: " + session.getId());
//            } catch (Exception e) {
//                System.err.println("Error sending message to session: " + session.getId());
//                e.printStackTrace();
//            }
//        }
//    }
}