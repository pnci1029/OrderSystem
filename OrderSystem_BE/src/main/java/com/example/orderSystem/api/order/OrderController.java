package com.example.orderSystem.api.order;

import com.example.orderSystem.api.order.dto.OrderCreateRequestDto;
import com.example.orderSystem.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/api") @RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public Response<?> createOrder(@RequestBody OrderCreateRequestDto dto) {
        return Response.ok(orderService.createOrder(dto));
    }
}
