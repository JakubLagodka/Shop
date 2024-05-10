package bench.artshop.order.controller;

import bench.artshop.order.dto.OrderDto;
import bench.artshop.order.mapper.OrderMapper;
import bench.artshop.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    @GetMapping("/")
    public ResponseEntity<Object> findOrders() {
        return ResponseEntity.ok().body(orderService.getOrders().stream().map(orderMapper::toDto).collect(Collectors.toList()));

    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Object> findOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok().body(orderMapper.toDto(orderService.getOrder(orderId)));

    }

    @PostMapping("/")
    public ResponseEntity<Object> makeOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok().body(orderMapper.toDto(orderService.addOrder(orderDto)));
    }
}