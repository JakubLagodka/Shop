package bench.artshop.order.repository;


import bench.artshop.order.dao.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, RevisionRepository<Product,Long,Integer> {
}
