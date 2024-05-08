package bench.artshop.order.repository;

import bench.artshop.order.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByLogin(String login);
}
