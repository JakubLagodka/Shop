package bench.artshop.order.controller;

import bench.artshop.order.mapper.OrderMapper;
import bench.artshop.order.services.OrderService;
import bench.artshop.order.util.OrderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static bench.artshop.order.util.OrderUtils.orderDtos;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private OrderService orderService;
    @Mock
    private OrderMapper orderMapper;
    @InjectMocks
    private OrderController orderController;

    @BeforeAll
    public static void prepareData() {
        OrderUtils.prepareData();
    }

    @Test
    public void shouldFindOrders() {
        var result = orderController.findOrders();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(), result.getBody());
    }

    @Test
    public void shouldFindOrder() {
        Mockito.when(orderMapper.toDto(any())).thenReturn(orderDtos.get(0));
        var result = orderController.findOrder(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(orderDtos.get(0), result.getBody());
    }

    @Test
    public void shouldNotFindOrder() throws Exception {
        mockMvc.perform(get("/order/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldMakeOrder() {
        var result = orderController.makeOrder(orderDtos.get(0));

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}