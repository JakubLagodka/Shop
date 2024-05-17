package bench.artshop.order.service;

import bench.artshop.order.dao.Product;
import bench.artshop.order.repository.ProductRepository;
import bench.artshop.order.util.ProblemUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService{

    private final ProductRepository productRepository;


    @CachePut(cacheNames = "product", key = "#result.id")
    @Transactional
    public Product create(Product product) {
         productRepository.save(product);
        return product;
    }

    @CachePut(cacheNames = "product", key = "#result.id")
    public Product update(Product product, Long id) {
        Product productDb = getById(id);
        productDb.setName(product.getName());
        productDb.setAvailable(product.isAvailable());
        productDb.setPrice(product.getPrice());
        productDb.setQuantity(product.getQuantity());

        return productDb;
    }
    @CacheEvict(cacheNames = "product", key = "#id")
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
    @Cacheable(cacheNames = "product", key = "#id")
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> ProblemUtils.getProductWithGivenIdNotFoundProblem(id));
    }
}
