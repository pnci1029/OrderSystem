package com.example.orderSystem.domain.order;

import com.example.orderSystem.api.order.dto.OrderCreateRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("주문 저장 시 key값은 식별자로 사용된다.")
    @Test
    void createOrderRepositoryTest() {
        // given
        Order order1 = Order.of(createOrder());
        Order order2 = Order.of(createOrder());
        Order order3 = Order.of(createOrder());

        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);

        // when
        List<Order> result = orderRepository.findAll();

        // id로 Set 만들어서 중복 여부 체크
        Set<Long> resultSet = result.stream().map(Order::getId)
                .collect(Collectors.toSet());
        List<String> resultList = result.stream().map(Order::getName).toList();

        // then
        assertThat(resultSet.size()).isEqualTo(result.size());
        assertThat(resultList).containsExactlyInAnyOrder(order1.getName(), order2.getName(), order3.getName());
    }

    // 랜덤 메뉴, 이름 수량 생성 함수
    private OrderCreateRequestDto createOrder() {

        List<String> menuList = new ArrayList<>(List.of("김치찌개", "피자", "돈까스", "치킨", "제육볶음", "김밥"));
        List<Integer> quantityList = new ArrayList<>(List.of(1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 11));

        Collections.shuffle(menuList);
        Collections.shuffle(quantityList);

        return OrderCreateRequestDto.builder()
                .name(menuList.get(0))
                .quantity(quantityList.get(0))
                .build();
    }

}