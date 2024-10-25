package com.example.orderSystem.api.order;

import com.example.orderSystem.api.order.dto.OrderCreateRequestDto;
import com.example.orderSystem.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api") @RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public Response<?> createOrder(@RequestBody OrderCreateRequestDto dto, HttpServletRequest request) {
        orderService.createOrder(dto);
        return Response.ok();
    }

    @GetMapping("/order")
    public Response<?> getOrders() {
        return Response.ok(orderService.getOrders());
    }

}
