package com.example.orderSystem.api.order;

import com.example.orderSystem.api.order.dto.OrderCreateRequestDto;
import com.example.orderSystem.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api") @RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/order")
    public Response<?> getOrders() {
        return Response.ok(orderService.getOrders());
    }

    @PostMapping("/order")
    public Response<?> createOrder(@RequestBody OrderCreateRequestDto dto) {
        return Response.ok(orderService.createOrder(dto));
    }

    @PatchMapping("/order")
    public Response<?> processOrder(@RequestParam Long orderId) {
        return Response.ok(orderService.updateOrder(orderId));
    }
}
