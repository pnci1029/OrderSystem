package com.example.orderSystem.api.order.dto;

import lombok.Getter;

@Getter
public class OrderCreateRequestDto {
    private String name;
    private int price;
}
