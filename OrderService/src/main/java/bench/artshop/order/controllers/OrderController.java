package bench.artshop.order.controllers;

import bench.artshop.order.dto.OrderDto;
import bench.artshop.order.mapper.OrderMapper;
import bench.artshop.order.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    @GetMapping("/")
    public Collection<OrderDto> findOrders() {
        return orderService.getOrders().stream().map(orderMapper::toDto).collect(Collectors.toList());
    }
    @GetMapping("/{orderId}")
    public OrderDto findOrder(@PathVariable Long orderId) {

        return orderMapper.toDto(orderService.getOrder(orderId));
    }
    @PostMapping("/")
    public ResponseEntity<OrderDto> makeOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok().body(orderMapper.toDto(orderService.addOrder(orderDto)));
    }
}
