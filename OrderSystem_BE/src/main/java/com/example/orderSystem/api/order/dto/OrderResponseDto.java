package com.example.orderSystem.api.order.dto;

import com.example.orderSystem.domain.order.Order;
import com.example.orderSystem.domain.order.Status;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class OrderResponseDto {
    private Long id;
    private String name;
    private Status status;
    private int quantity;


    public static OrderResponseDto of(Order order) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .name(order.getName())
                .quantity(order.getQuantity())
                .status(order.getStatus())
                .build();
    }

}
