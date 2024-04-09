package bench.artshop.order.controller;

import bench.artshop.order.dto.OrderDto;
import bench.artshop.order.mapper.OrderMapper;
import bench.artshop.order.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.ThrowableProblem;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.zalando.problem.Status.NOT_FOUND;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    private static ThrowableProblem getProblem(Long orderId) {
        return Problem.builder()
                .withTitle("Order not found")
                .withStatus(NOT_FOUND)
                .withDetail("Order " + orderId + "is no longer available")
                .with("order", orderId)
                .build();
    }

    @GetMapping("/")
    public Collection<OrderDto> findOrders() {
        return orderService.getOrders().stream().map(orderMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Object> findOrder(@PathVariable Long orderId) {
        try {
            return ResponseEntity.ok().body(orderMapper.toDto(orderService.getOrder(orderId)));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<Object>(
                    getProblem(orderId),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<OrderDto> makeOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok().body(orderMapper.toDto(orderService.addOrder(orderDto)));
    }
}
