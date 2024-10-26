package com.example.orderSystem.api.order.dto;

import com.example.orderSystem.domain.order.Status;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class OrderCreateRequestDto {
    private String name;
    private int quantity;
}
