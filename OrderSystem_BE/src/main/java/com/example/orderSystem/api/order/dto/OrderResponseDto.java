package com.example.orderSystem.api.order.dto;

import com.example.orderSystem.domain.order.Order;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class OrderResponseDto {
    private Long id;
    private String name;
    private int quantity;


    public static OrderResponseDto of(Order order) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .name(order.getName())
                .quantity(order.getQuantity())
                .build();
    }

}
