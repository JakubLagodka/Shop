package bench.artshop.order.controller;

import bench.artshop.order.dao.Order;
import bench.artshop.order.mapper.OrderMapper;
import bench.artshop.order.services.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderControllerTest {
    @Mock
    private OrderService orderService;

    @Mock
    private OrderMapper orderMapper;

    private Order order = new Order();

    @Test
    public void testGetOrders() throws Exception {
        BDDMockito.given(orderService.getOrders()).willReturn(List.of(order));
    }

    @Test
    public void testGetOrder() throws Exception {
        BDDMockito.given(orderService.getOrder(anyLong())).willReturn(order);
    }

    @Test
    public void testAddOrder() throws Exception {
        BDDMockito.given(orderService.addOrder(orderMapper.toDto(order))).willReturn(order);
    }
}