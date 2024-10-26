package com.example.orderSystem.api.order;

import com.example.orderSystem.api.order.dto.OrderCreateRequestDto;
import com.example.orderSystem.api.order.dto.OrderResponseDto;
import com.example.orderSystem.domain.order.Order;
import com.example.orderSystem.domain.order.OrderRepository;
import com.example.orderSystem.domain.order.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @DisplayName("메뉴와 수량을 요청 받아 DB에 저장한다.")
    @Test
    void createOrderServiceTest() {
        // given
        String name = "탕수육";
        int quantity = 2;
        OrderCreateRequestDto request = OrderCreateRequestDto
                .builder()
                .name(name)
                .quantity(quantity)
                .build();


        // when
        OrderResponseDto response = orderService.createOrder(request);

        // then
        // 응답받은 데이터와 요청받은 데이터가 일치하는지 확인
        assertThat(response)
                .extracting("name","quantity")
                .contains(name, quantity);
    }

    @DisplayName("주문 ID를 받고, 다음 주문 상태로 업데이트 한다.")
    @Test
    void updateOrderServiceTest() {
        // given
        String name = "탕수육";
        int quantity = 2;
        OrderCreateRequestDto request = OrderCreateRequestDto
                .builder()
                .name(name)
                .quantity(quantity)
                .build();

        OrderResponseDto createdOrder = orderService.createOrder(request);
        Order order = orderService.getOrders().stream().findFirst().orElseThrow();

        // when
        OrderResponseDto firstUpdatedOrder = orderService.updateOrder(order.getId());
        OrderResponseDto secondUpdatedOrder = orderService.updateOrder(order.getId());

        // then
        // 상태가 초기 상태와 다른지 확인
        assertThat(createdOrder.getStatus()).isNotEqualTo(firstUpdatedOrder.getStatus());

        // 상태가 RECEIVED에서 PROCESSING으로 변경되었는지 확인
        assertThat(firstUpdatedOrder.getStatus()).isEqualTo(Status.PROCESSING);

        // 상태가 PROCESSING에서 FINISHED로 변경되었는지 확인
        assertThat(secondUpdatedOrder.getStatus()).isEqualTo(Status.FINISHED);
    }
}