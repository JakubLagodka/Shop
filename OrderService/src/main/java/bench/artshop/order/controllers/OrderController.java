package bench.artshop.order.controllers;

import bench.artshop.order.dto.OrderDto;
import bench.artshop.order.mapper.OrderMapper;
import bench.artshop.order.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    @GetMapping("/")
    public Collection<OrderDto> findOrders() {
        return List.of(OrderDto.builder()
                        .orderId(543L)
                        .productCode("sku-kub-glin")
                        .build(),
                OrderDto.builder()
                        .orderId(544L)
                        .productCode("maly-obr-olej")
                        .build());
    }

    @PostMapping("/order")
    public ResponseEntity<OrderDto> makeOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok().body(orderMapper.toDto(orderService.addOrder(orderDto)));
    }
}
