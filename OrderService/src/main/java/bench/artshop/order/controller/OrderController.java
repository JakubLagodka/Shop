package bench.artshop.order.controller;

import bench.artshop.order.dto.OrderDto;
import bench.artshop.order.mapper.OrderMapper;
import bench.artshop.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.ThrowableProblem;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    @GetMapping
    @PreAuthorize("isAuthenticated() && hasAuthority('ADMIN')")
    public ResponseEntity<Object> findOrders() {
        return ResponseEntity.ok().body(orderService.getOrders().stream().map(orderMapper::toDto).collect(Collectors.toList()));

    }

    @GetMapping("/{orderId}")
    @PreAuthorize("isAuthenticated() && hasAuthority('ADMIN')")
    public ResponseEntity<Object> findOrder(@PathVariable Long orderId) {
        try {
            return ResponseEntity.ok().body(orderMapper.toDto(orderService.getOrder(orderId)));
        }catch (ThrowableProblem throwableProblem){
            return new ResponseEntity<>(throwableProblem.getMessage(),HttpStatusCode.valueOf(404));
        }
    }
    @GetMapping("/logged")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> findOrderOfCurrentUser(@PathVariable Long orderId) {
        try {
            return ResponseEntity.ok().body(orderMapper.toDto(orderService.getOrder(orderId)));
        }catch (ThrowableProblem throwableProblem){
            return new ResponseEntity<>(throwableProblem.getMessage(),HttpStatusCode.valueOf(404));
        }
    }

    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> makeOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok().body(orderMapper.toDto(orderService.addOrder(orderMapper.toDao(orderDto))));
    }
}