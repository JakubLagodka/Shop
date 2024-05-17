package bench.artshop.order.service;

import bench.artshop.order.dao.Basket;
import bench.artshop.order.dao.Product;
import bench.artshop.order.dao.User;
import bench.artshop.order.exception.NotEnoughProductQuantityException;
import bench.artshop.order.repository.BasketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasketService {
    private final BasketRepository basketRepository;

    private final UserService userService;

    private final ProductService productService;

    public void addProduct(Long productId, double quantity) {
        User currentUser = userService.getCurrentUser();
        if (quantity > productService.getById(productId).getQuantity())
            throw new NotEnoughProductQuantityException("Given quantity " + quantity + " is too high for product" + productId);
        basketRepository.findByProductIdAndUserId(productId, currentUser.getId())
                .ifPresentOrElse(basket -> {
                    if (basket.getQuantity() + quantity > productService.getById(productId).getQuantity())
                        throw new NotEnoughProductQuantityException("Given quantity " + basket.getQuantity() + quantity + " is too high for product" + productId);
                    basket.setQuantity(basket.getQuantity() + quantity);
                    basketRepository.save(basket);
                }, () -> basketRepository.save(Basket.builder()
                        .product(productService.getById(productId))
                        .quantity(quantity)
                        .user(currentUser)
                        .build()));

    }

    public List<Product> getBasket() {
        User currentUser = userService.getCurrentUser();
        List<Product> retBasket;

        retBasket = basketRepository.findByUserId(currentUser.getId()).stream()
                .map(Basket::getProduct)
                .collect(Collectors.toList());
        return retBasket;

    }

    @Transactional
    public void clearBasket() {
        User currentUser = userService.getCurrentUser();
        basketRepository.deleteByUserId(currentUser.getId());
    }

    @Transactional
    public void deleteProductByProductId(Long productId) {
        User currentUser = userService.getCurrentUser();
        basketRepository.deleteByProductIdAndUserId(productId, currentUser.getId());
    }
}
