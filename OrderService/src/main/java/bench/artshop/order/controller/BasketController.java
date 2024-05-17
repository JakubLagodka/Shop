package bench.artshop.order.controller;

import bench.artshop.order.dao.Product;
import bench.artshop.order.dto.BasketDto;
import bench.artshop.order.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class BasketController {
    private final BasketService basketService;

    @PostMapping
    @Operation(description = "add product to basket", security = @SecurityRequirement(name = "bearer"))
    public void addProduct(@RequestBody BasketDto basketDto) {
        basketService.addProduct(basketDto.getProductId(), basketDto.getQuantity());
    }

    @GetMapping
    @Operation(description = "get all products in basket", security = @SecurityRequirement(name = "bearer"))
    public List<Product> getBasket() {
        return basketService.getBasket();
    }

    @DeleteMapping
    @Operation(description = "remove basket", security = @SecurityRequirement(name = "bearer"))
    public void clearBasket() {
        basketService.clearBasket();
    }

    @DeleteMapping("/{productId}")
    @Operation(description = "remove specified product in basket", security = @SecurityRequirement(name = "bearer"))
    public void deleteProductByProductId(@PathVariable Long productId) {
        basketService.deleteProductByProductId(productId);
    }
}
