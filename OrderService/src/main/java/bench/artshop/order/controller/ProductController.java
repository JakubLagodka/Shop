package bench.artshop.order.controller;

import bench.artshop.order.dto.ProductDto;
import bench.artshop.order.mapper.ProductMapper;
import bench.artshop.order.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/product")
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductService productService;

    private final ProductMapper productMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "create new product", security = @SecurityRequirement(name = "bearer"))
    public ProductDto saveProduct(@Valid @RequestBody ProductDto productDto) {
        return productMapper.toDto(productService.create(productMapper.toDao(productDto)));
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "update given product", security = @SecurityRequirement(name = "bearer"))
    public ProductDto updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable Long id) {
        return productMapper.toDto(productService.update(productMapper.toDao(productDto), id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "delete given product", security = @SecurityRequirement(name = "bearer"))
    public void deleteProduct(@PathVariable Long id) {
        productService.delete(id);
    }
}
