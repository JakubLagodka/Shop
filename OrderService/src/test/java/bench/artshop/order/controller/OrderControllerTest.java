package bench.artshop.order.controller;

import bench.artshop.order.mapper.OrderMapper;
import bench.artshop.order.services.OrderService;
import bench.artshop.order.util.OrderUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static bench.artshop.order.util.OrderUtils.orderDtos;
import static bench.artshop.order.util.ProblemUtils.getOrderNotFoundProblem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Transactional
class OrderControllerTest {
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
    public void shouldNotFindOrder() {
        Mockito.when(orderMapper.toDto(any())).thenThrow(NoSuchElementException.class);
        var result = orderController.findOrder(1L);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(getOrderNotFoundProblem(1L).toString(), Objects.requireNonNull(result.getBody()).toString());
    }

    @Test
    public void shouldMakeOrder() {
        var result = orderController.makeOrder(orderDtos.get(0));

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}