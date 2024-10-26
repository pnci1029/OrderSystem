package com.example.orderSystem.api.order;

import com.example.orderSystem.api.order.dto.OrderCreateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper om;

    @MockBean
    protected OrderService orderService;

    @DisplayName("주문을 생성합니다.")
    @Test
    void createOrderControllerTest() throws Exception {
        // given
        OrderCreateRequestDto request = OrderCreateRequestDto.builder()
                .name("탕수육")
                .quantity(2)
                .build();

        // when then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
                        .content(om.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatus").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
        ;
    }

    @DisplayName("요청받은 식별자에 대한 주문 상태를 업데이트합니다.")
    @Test
    void updateOrderControllerTest() throws Exception {
        // given
        int orderId = 0;

        OrderCreateRequestDto request = OrderCreateRequestDto.builder()
                .name("탕수육")
                .quantity(2)
                .build();

        orderService.createOrder(request);

        // when then
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/order")
                        .param("orderId", String.valueOf(orderId))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatus").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }
}