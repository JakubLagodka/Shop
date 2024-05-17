package bench.artshop.order.controller;

import bench.artshop.order.dto.ProductDto;
import bench.artshop.order.dto.UserDto;
import bench.artshop.order.mapper.HistoryMapper;
import bench.artshop.order.repository.ProductRepository;
import bench.artshop.order.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/histories")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class HistoryController {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final HistoryMapper historyMapper;

    @GetMapping("/users/{id}")
    @Operation(description = "get user history", security = @SecurityRequirement(name = "bearer"))
    public Page<UserDto> getUserHistory(@PathVariable Long id, @RequestParam int page, @RequestParam int size) {
        return userRepository.findRevisions(id, PageRequest.of(page, size))
                .map(historyMapper::toUserDto);
    }

    @GetMapping("/products/{id}")
    @Operation(description = "get produ history", security = @SecurityRequirement(name = "bearer"))
    public Page<ProductDto> getProductHistory(@PathVariable Long id, @RequestParam int page, @RequestParam int size) {
        return productRepository.findRevisions(id, PageRequest.of(page, size))
                .map(historyMapper::toProductDto);
    }
}
