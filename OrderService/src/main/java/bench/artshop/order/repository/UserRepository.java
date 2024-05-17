package bench.artshop.order.repository;

import bench.artshop.order.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

import static bench.artshop.order.util.ProblemUtils.getUserWithGivenLoginNotFoundProblem;

public interface UserRepository extends JpaRepository<User, Long>, RevisionRepository<User,Long,Integer> {
    Optional<User> findByLogin(String login);
    default UserDetailsService userDetailsService() {
        return login -> findByLogin(login)
                .orElseThrow(() -> getUserWithGivenLoginNotFoundProblem(login));
    }
}
