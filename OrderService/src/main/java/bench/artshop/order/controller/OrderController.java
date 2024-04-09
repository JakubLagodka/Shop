package bench.artshop.order.controller;

import bench.artshop.order.dto.OrderDto;
import bench.artshop.order.mapper.OrderMapper;
import bench.artshop.order.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static bench.artshop.order.util.ProblemUtils.getInternalServerErrorProblem;
import static bench.artshop.order.util.ProblemUtils.getOrderNotFoundProblem;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    @GetMapping("/")
    public ResponseEntity<Object> findOrders() {
        try {
            return ResponseEntity.ok().body(orderService.getOrders().stream().map(orderMapper::toDto).collect(Collectors.toList()));
        } catch (Exception e) {
            return new ResponseEntity<>(getInternalServerErrorProblem(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Object> findOrder(@PathVariable Long orderId) {
        try {
            return ResponseEntity.ok().body(orderMapper.toDto(orderService.getOrder(orderId)));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(
                    getOrderNotFoundProblem(orderId),
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(getInternalServerErrorProblem(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> makeOrder(@RequestBody OrderDto orderDto) {
        try {
            return ResponseEntity.ok().body(orderMapper.toDto(orderService.addOrder(orderDto)));
        } catch (Exception e) {
            return new ResponseEntity<>(getInternalServerErrorProblem(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}