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

import java.net.URI;
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
                    Problem.builder()
                            .withType(URI.create("https://example.org/out-of-stock"))
                            .withTitle("Out of Stock")
                            .withStatus(NOT_FOUND)
                            .withDetail("Item " + orderId + "is no longer available")
                            .with("product", orderId)
                            .build(),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<OrderDto> makeOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok().body(orderMapper.toDto(orderService.addOrder(orderDto)));
    }
}
