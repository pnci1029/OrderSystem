package com.example.orderSystem.api.order;

import com.example.orderSystem.api.order.dto.OrderCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api") @RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public void createOrder(@RequestBody OrderCreateRequestDto dto) {
        orderService.createOrder(dto);
    }

}
