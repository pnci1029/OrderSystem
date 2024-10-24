package com.example.orderSystem.api.order.dto;

import lombok.Getter;

@Getter
public class OrderResponseDto {
    private Long id;
    private String name;
    private int price;
}
