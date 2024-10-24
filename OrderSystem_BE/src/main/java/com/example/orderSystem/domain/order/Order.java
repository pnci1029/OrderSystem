package com.example.orderSystem.domain.order;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class Order {

    private Long id;
    private String name;
    private int quantity;


}
