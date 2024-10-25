package com.example.orderSystem.api.order;

import com.example.orderSystem.api.order.dto.OrderCreateRequestDto;
import com.example.orderSystem.api.order.dto.OrderResponseDto;
import com.example.orderSystem.domain.order.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        assertThat(response)
                .extracting("name","quantity")
                .contains(name, quantity);
    }
}