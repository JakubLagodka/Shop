package bench.artshop.order.repository;


import bench.artshop.order.dao.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long>, RevisionRepository<Basket, Long, Integer> {


    Optional<Basket> findByProductIdAndUserId(Long productId, Long userId);

    Optional<Basket> findByUserId(Long userId);

    Optional<Basket> deleteByUserId(Long userId);

    Optional<Basket> deleteByProductIdAndUserId(Long productId, Long userId);
}
