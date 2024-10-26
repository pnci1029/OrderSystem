package com.example.orderSystem.domain.order;

import com.example.orderSystem.api.order.dto.OrderCreateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder @AllArgsConstructor
public class Order {

    private Long id;
    private String name;
    private Status status;
    private int quantity;


    public static Order of(OrderCreateRequestDto dto) {
        return Order.builder()
                .name(dto.getName())
                .quantity(dto.getQuantity())
                .status(Status.RECEIVED)
                .build();
    }
}
