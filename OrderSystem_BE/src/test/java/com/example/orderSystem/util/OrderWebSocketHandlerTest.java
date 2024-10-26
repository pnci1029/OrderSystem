package com.example.orderSystem.util;

import com.example.orderSystem.api.order.dto.OrderCreateRequestDto;
import com.example.orderSystem.domain.order.Order;
import com.example.orderSystem.domain.order.OrderRepository;
import com.example.orderSystem.domain.order.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(OrderWebSocketHandler.class)
class OrderWebSocketHandlerTest {


    @Autowired
    private OrderWebSocketHandler orderWebSocketHandler;

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final int THREAD_COUNT = 10;
    private final int MESSAGES_PER_THREAD = 10;

    @BeforeEach
    void setUp() throws Exception {
        when(orderRepository.save(any(Order.class))).thenReturn(new Order(1L, "피자", Status.RECEIVED,1));
    }

    @DisplayName("동시에 여러 WebSocket 세션에서 주문을 처리한다")
    @Test
    void givenMultipleSessions_whenSendingMessages_thenHandleAllMessages() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        List<WebSocketSession> sessions = new ArrayList<>();
        int sessionCount = 0;

        // 여러 개의 WebSocket 세션 생성 및 연결
        for (int i = 0; i < THREAD_COUNT; i++) {
            WebSocketSession session = mock(WebSocketSession.class);
            when(session.isOpen()).thenReturn(true);
            orderWebSocketHandler.afterConnectionEstablished(session);
            sessions.add(session);
            sessionCount++;
        }

        // 모든 스레드에서 동시에 메시지 전송
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    for (int j = 0; j < MESSAGES_PER_THREAD; j++) {
                        OrderCreateRequestDto requestDto = OrderCreateRequestDto.builder().name("탕수육").quantity(2).build();
                        String payload = objectMapper.writeValueAsString(requestDto);
                        TextMessage message = new TextMessage(payload);

                        // 모든 세션에 메세지 요청
                        for (WebSocketSession session : sessions) {
                            orderWebSocketHandler.handleTextMessage(session, message);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        // 모든 스레드가 완료될 때까지 대기
        latch.await();

        // 스레드 * 메시지 갯수 * 세션 갯수가 일치하는지 확인
        verify(orderRepository, times(THREAD_COUNT * MESSAGES_PER_THREAD * sessionCount)).save(any(Order.class));

        // 세션 정리
        sessions.forEach(session -> {
            try {
                orderWebSocketHandler.afterConnectionClosed(session, CloseStatus.NORMAL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }
}
