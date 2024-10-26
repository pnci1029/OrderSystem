package com.example.orderSystem.api.order;

import com.example.orderSystem.api.order.dto.OrderCreateRequestDto;
import com.example.orderSystem.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api") @RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public Response<?> createOrder(@RequestBody OrderCreateRequestDto dto) {
        return Response.ok(orderService.createOrder(dto));
    }

    @PatchMapping("/order")
    public Response<?> updateOrder(@RequestParam Long orderId) {
        return Response.ok(orderService.updateOrder(orderId));
    }

    @GetMapping("/order")
    public Response<?> getOrders() {
        return Response.ok(orderService.getOrders());
    }
}
